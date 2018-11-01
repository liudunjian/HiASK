package com.hisense.hiask.robot;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;
import com.hisense.hiask.entity.robot.RobotGeoPoiBean;
import com.hisense.hiask.entity.robot.RobotRouteWayBean;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.higreendao.manager.DaoManager;
import com.hisense.hiask.mvpbase.BasePresenter;
import com.hisense.hiask.recognizer.AsstRecognizer;
import com.hisense.hiask.recognizer.IAsstRecognizerListener;
import com.hisense.hiask.recognizer.entity.RecogResult;
import com.hisense.hibeans.bot.model.BotMenuItem;
import com.hisense.hibeans.bot.model.BotMessage;
import com.hisense.hibeans.bot.model.MessageContentImage;
import com.hisense.hibeans.bot.model.MessageContentMenu;
import com.hisense.hibeans.bot.model.MessageContentText;
import com.hisense.hibeans.robot.RobotAudioMsgBean;
import com.hisense.hibeans.robot.RobotImageMsgBean;
import com.hisense.hibeans.robot.RobotMenuMnuList;
import com.hisense.hibeans.robot.RobotMenuRecBean;
import com.hisense.hibeans.robot.RobotMenuRecList;
import com.hisense.hibeans.robot.RobotPluginItemBean;
import com.hisense.hibeans.robot.RobotTextMsgBean;
import com.hisense.hibot.bot.client.BotClient;
import com.hisense.hibot.bot.enumdefine.EBotConnectState;
import com.hisense.hibot.bot.listener.IBotConnectionListener;
import com.hisense.hibot.bot.listener.IBotMessageListener;
import com.hisense.hitools.broadcast.NetworkListener;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.ImageUtils;
import com.hisense.hitools.utils.LogUtils;
import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;
import com.hisense.hitran.lisener.ITranFrameworkListener;
import com.hisense.hitran.manager.TranFramework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liudunjian on 2018/4/25.
 */

public class RobotPresenter extends BasePresenter<IRobot> implements IBotConnectionListener,
        IBotMessageListener,
        IAsstRecognizerListener, ITranFrameworkListener {

    private BotClient botClient;
    private NetworkListener networkListener;
    private AsstRecognizer asstRecognizer;
    private Gson gson = new Gson();

    private TranFramework tranFramework;

    private AMapLocationClient locationManager;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NetworkListener.HANDLER_NETWORK_CHANGE_MSG:
                    processNetworkEvent(msg.arg1);
                    break;
            }
        }
    };

    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {

            if (location != null) {
                StringBuilder builder = new StringBuilder(String.valueOf(location.getLatitude()));
                builder.append(",");
                builder.append(String.valueOf(location.getLongitude()));
                tranFramework.updateLocation(builder.toString());
                LogUtils.d("location:" + builder.toString());
            }
        }
    };

    /******************base override methods***************/
    public RobotPresenter(Context context, IRobot iRobot) {
        super(context, iRobot);
        tranFramework = TranFramework.getInstance();
        tranFramework.setTranFrameworkListener(this);
    }

    @Override
    protected void onCreate() {
        botClient = BotClient.getInstance(context);
        botClient.setConnectionListener(this);
        botClient.setMessageListener(this);
        //  loadAllCachedData();
        startLocation();
    }

    @Override
    protected void onStart() {
        registerNetworkListener();
    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onStop() {
        unRegisterNetworkListener();
    }

    @Override
    protected void onPause() {
        if (asstRecognizer != null)
            releaseAsstVoiceRecognize();
    }

    @Override
    protected void onDestroy() {
        botClient.disconnect();
    }

    /******************public methods*******************/

    public void askSuggestion(String text) {
        botClient.askSuggestion(text);
    }

    public void askQuestion(String text) {
       // botClient.askQuestion(text);
        tranFramework.askQuestion(text);
    }

    public void askQuestion(RobotMenuRecBean item, int menuType) {
        BotMenuItem botMenuItem = new BotMenuItem(item.getItemId(), item.getContent(), item.getType());
        botClient.askQuestion(botMenuItem, menuType);
    }

    public void askQuestion(RobotMenuMnuList.RobotMenuMnuBean item, int menuType) {
        BotMenuItem botMenuItem = new BotMenuItem(item.getItemId(), item.getContent(), item.getType());
        botClient.askQuestion(botMenuItem, menuType);
    }

    public void askQuestionImage(final Uri uriCapture, final boolean del) {
        try {
            final InputStream is = context.getContentResolver().openInputStream(uriCapture);
            Observable.fromArray(is)
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<InputStream, ObservableSource<File>>() {
                        @Override
                        public ObservableSource<File> apply(@NonNull InputStream inputStream) throws Exception {
                            File imgUri = File.createTempFile("photo", ".jpg", context.getExternalCacheDir());
                            FileOutputStream imgFile = new FileOutputStream(imgUri);
                            long size = 0L;
                            byte[] buffer = new byte[4096];

                            while (true) {
                                int len = is.read(buffer);
                                if (len <= 0) {
                                    break;
                                }
                                imgFile.write(buffer, 0, len);
                                size += (long) len;
                            }

                            imgFile.close();
                            is.close();

                            if (size > 1048576L) {
                                File tmpFile = File.createTempFile("photo", ".jpg", context.getExternalCacheDir());
                                ImageUtils.compressImage(imgUri, 4000, 4000, Bitmap.CompressFormat.JPEG, 80, tmpFile.getPath());
                                imgUri.delete();
                                imgUri = tmpFile;
                            }
                            if (del)
                                context.getContentResolver().delete(uriCapture, (String) null, (String[]) null);
                            return Observable.fromArray(imgUri);
                        }
                    })
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) throws Exception {
                            LogUtils.d("thread---" + Thread.currentThread().getName());
                            if (file != null) {
                                //botClient.askQuestionImage(file);
                            }
                        }
                    });
        } catch (Exception var) {
            var.printStackTrace();
        }
    }

    public void startAsstVoiceRecognize() {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                LogUtils.d("onAUdioFocusChange ----" + focusChange);
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (asstRecognizer == null) {
            //初始化Recognizer
            asstRecognizer = new AsstRecognizer(context, this);
        }

        // 确定识别参数
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true);
        //启动识别
        asstRecognizer.start(params);

    }

    /**
     * 开始录音后，手动停止录音。SDK会识别在此过程中的录音。点击“停止”按钮后调用。
     */
    public void stopAsstVoiceRecognize() {
        if (asstRecognizer != null) {
            asstRecognizer.stop();
        }
//        final File file = audioManager.stopAsstVoiceRecognize();
//        RobotAudioMsgBean item = new RobotAudioMsgBean(new Date(System.currentTimeMillis()), 0);
//        item.setFilePath(file.getAbsolutePath());
//        postMessageToMain(item);
//        askQuestionAudio(file);

    }


    /**
     * 开始录音后，取消这次录音。SDK会取消本次识别，回到原始状态。点击“取消”按钮后调用。
     */
    public void cancelAsstVoiceRecognize() {
        if (asstRecognizer != null)
            asstRecognizer.cancel();
    }


    public void releaseAsstVoiceRecognize() {
        if (asstRecognizer != null) {
            asstRecognizer.release();
            asstRecognizer = null;
        }
    }


    public List<RobotPluginItemBean> generateRobotPlugins() {
        ArrayList<RobotPluginItemBean> items = new ArrayList<>();
        items.add(new RobotPluginItemBean(System.currentTimeMillis(), 1001, R.drawable.selector_robot_plugin_album, context.getString(R.string.robot_plugin_item_album)));
        items.add(new RobotPluginItemBean(System.currentTimeMillis(), 1002, R.drawable.selector_robot_plugin_camera, context.getString(R.string.robot_plugin_item_camera)));
        for (int i = 3; i < 17; i++) {
            RobotPluginItemBean item = new RobotPluginItemBean(System.currentTimeMillis(), 1000 + i, R.drawable.robot_plugin_camera_no, "测试");
            items.add(item);
        }
        return items;
    }

    public void playAudioItem(RobotAudioMsgBean item) {
        //audioManager.startPlay(item.getFilePath());
    }

    /**********override methods************************/

    private void askQuestionAudio(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            botClient.askQuestionAudio(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void postMessageToMain(final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isAttached())
                    attachedView.get().onBotMessageUpdate(object);
            }
        });
    }

    @Override
    public void onBotConnectionStateChanged(final EBotConnectState state) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (isAttached())
//                    attachedView.get().notifyBotConnectState(state);
//            }
//        });
    }

    @Override
    public void onReceivedSuggestion(final ArrayList<BotMenuItem> var1) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isAttached())
                    attachedView.get().onBotSuggestionUpdate(var1);
            }
        });
    }

    @Override
    public void onAppendMessage(final BotMessage var1) {
        switch (var1.getContentType()) {
            case 1://message
                MessageContentText contentText = gson.fromJson(var1.getContent(), MessageContentText.class);
                final RobotTextMsgBean textBean = new RobotTextMsgBean(var1.getSendTime(), var1.getDirection(), contentText.getText());
                postMessageToMain(textBean);
                break;
            case 2://menu
                MessageContentMenu contentMenu = gson.fromJson(var1.getContent(), MessageContentMenu.class);
//                if (contentMenu.getMenuType() == 0) {//menu
//                    LogUtils.d(new Gson().toJson(contentMenu));
//                    ArrayList<RobotMenuMnuList.RobotMenuMnuBean> mnuBeans = new ArrayList<>();
//                    for (BotMenuItem botMenuItem : contentMenu.getBotMenuItems()) {
//                        mnuBeans.add(new RobotMenuMnuList.RobotMenuMnuBean(botMenuItem.getItemId(), botMenuItem.getContent(), botMenuItem.getType()));
//                    }
//
//                    final RobotMenuMnuList robotMenuMnuList = new RobotMenuMnuList(System.currentTimeMillis(), mnuBeans);
//                    postMessageToMain(robotMenuMnuList);
//
//                } else
                if (contentMenu.getMenuType() == 1) {//recommend
                    ArrayList<RobotMenuRecBean> recBeans = new ArrayList<>();

                    for (BotMenuItem botMenuItem : contentMenu.getBotMenuItems()) {
                        recBeans.add(new RobotMenuRecBean(botMenuItem.getItemId(), botMenuItem.getContent(), botMenuItem.getType()));
                    }

                    final RobotMenuRecList recommendBean = new RobotMenuRecList(System.currentTimeMillis(), recBeans);
                    postMessageToMain(recommendBean);

                }
                break;
            case 3: //image
                MessageContentImage contentImage = gson.fromJson(var1.getContent(), MessageContentImage.class);
                final RobotImageMsgBean imageMsgBean = new RobotImageMsgBean(var1.getSendTime(), var1.getDirection(), contentImage.getDataPath());
                postMessageToMain(imageMsgBean);
                break;
            default:
                break;
        }
    }

    /**************************speech recognize listener*******************/
    @Override
    public void onAsrReady() {

    }

    @Override
    public void onAsrBegin() {

    }

    @Override
    public void onAsrEnd() {

    }

    @Override
    public void onAsrPartialResult(String[] results, RecogResult recogResult) {

    }

    @Override
    public void onAsrFinalResult(String[] results, RecogResult recogResult) {
        LogUtils.d("onAsrFinish----------" + recogResult.getOrigalResult());
        try {
            JSONObject jsonObject = new JSONObject(recogResult.getOrigalResult());
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray texts = result.getJSONArray("word");
            String text = texts.get(0).toString();
            RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 0, text);
            postMessageToMain(textBean);
            askQuestion(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAsrFinish(RecogResult recogResult) {

    }

    @Override
    public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {

    }

    @Override
    public void onAsrLongFinish() {

    }

    @Override
    public void onAsrVolume(int volumePercent, int volume) {
        LogUtils.d("volumePercent-----" + volumePercent + "----vlo-----" + volume);
        if (isAttached())
            attachedView.get().onRecordRmsChanged(volumePercent);
    }

    @Override
    public void onAsrAudio(byte[] data, int offset, int length) {

    }

    @Override
    public void onAsrExit() {

    }

    @Override
    public void onAsrOnlineNluResult(String nluResult) {

    }

    @Override
    public void onOfflineLoaded() {

    }

    @Override
    public void onOfflineUnLoaded() {

    }

    /********************message listener from hitran module**********/

    @Override
    public void onTextMessageContent(String text) {
        Log.d("RobotPresenter", text);
        final RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 1, text);
        postMessageToMain(textBean);
    }

    @Override
    public void onRouteWayMessageContent(TranRouteWayPayload payload) {
        final RobotRouteWayBean routeWayBean = new RobotRouteWayBean(System.currentTimeMillis(), payload);
        postMessageToMain(routeWayBean);
    }

    @Override
    public void onGeographicPoiContent(PoiPayload payload) {
        final RobotGeoPoiBean robotGeoPoiBean = new RobotGeoPoiBean(System.currentTimeMillis(), payload);
        postMessageToMain(robotGeoPoiBean);
    }

    @Override
    public void onTranCannotResponse(String request) {
        if (botClient.getConnectState() == EBotConnectState.BOT_CONNECTED_STATE) {
            botClient.askQuestion(request);
        } else {
            final RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 1, "暂时无法回答你的问题");
            postMessageToMain(textBean);
        }
    }

    /***************private methods*********************/

    private void registerNetworkListener() {
        if (networkListener == null)
            networkListener = new NetworkListener(handler);
        context.registerReceiver(networkListener, NetworkListener.intentFilter());
    }

    private void unRegisterNetworkListener() {
        if (networkListener != null) {
            context.unregisterReceiver(networkListener);
            networkListener = null;
        }
    }

    private void processNetworkEvent(int type) {
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_MOBILE:
                EBotConnectState state = botClient.getConnectState();
                if (state == EBotConnectState.BOT_CONNECT_FAILED_STATE || state == EBotConnectState.BOT_DISCONNECT_STATE)
                    botClient.connect();
            default:
                break;
        }
    }

    private void loadAllCachedData() {
        List<BotMessage> botMessages = DaoManager.getInstance().loadAllBotMessagesByTime(100);
        if (EmptyUtils.isNotEmpty(botMessages)) {
            for (BotMessage botMessage : botMessages) {
                this.onAppendMessage(botMessage);
            }
        }
    }

    private void startLocation() {
        if (locationManager == null) {
            locationManager = new AMapLocationClient(context.getApplicationContext());
        }
        AMapLocationClientOption locationClientSingleOption = new AMapLocationClientOption();
//该方法默认为false。
        locationClientSingleOption.setOnceLocation(true);
//关闭缓存机制
        locationClientSingleOption.setLocationCacheEnable(false);
//给定位客户端对象设置定位参数
        locationManager.setLocationOption(locationClientSingleOption);
        locationManager.setLocationListener(locationSingleListener);
//启动定位
        locationManager.startLocation();
    }


}
