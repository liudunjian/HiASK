package com.hisense.hibot.bot.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.hisense.hiask.higreendao.manager.DaoManager;
import com.hisense.hibeans.bot.request.BaseTextReq;
import com.hisense.hibeans.bot.model.BotDeviceInfo;
import com.hisense.hibeans.bot.model.BotMenuItem;
import com.hisense.hibeans.bot.model.BotMessage;
import com.hisense.hibeans.bot.model.BotQuestion;
import com.hisense.hibeans.bot.model.MessageContent;
import com.hisense.hibeans.bot.model.MessageContentImage;
import com.hisense.hibeans.bot.model.MessageContentMenu;
import com.hisense.hibeans.bot.model.MessageContentText;
import com.hisense.hibeans.bot.request.AudioReq;
import com.hisense.hibeans.bot.request.MessageReq;
import com.hisense.hibeans.bot.request.TextReq;
import com.hisense.hibeans.bot.request.VisitorReq;
import com.hisense.hibeans.bot.response.AudioRes;
import com.hisense.hibeans.bot.response.BotAnswerItem;
import com.hisense.hibeans.bot.response.MaterialImage;
import com.hisense.hibeans.bot.response.MessageRes;
import com.hisense.hibeans.bot.response.RobotNameRes;
import com.hisense.hibeans.bot.response.VisitorRes;
import com.hisense.hibot.bot.enumdefine.EBotConnectState;
import com.hisense.hibot.bot.listener.IBotConnectionListener;
import com.hisense.hibot.bot.listener.IBotMessageListener;
import com.hisense.hibot.http.HttpManager;
import com.hisense.hibot.http.factory.BotRequestFactory;
import com.hisense.hitools.utils.AppUtils;
import com.hisense.hitools.utils.LogUtils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static io.reactivex.Observable.create;


/**
 * Created by liudunjian on 2018/4/11.
 */

public class BotClient {

    private Context context;

    //网络访问必要信息
    private String robotName;
    private String visitorId;
    private String userName = "hiask";
    private String phone;

    //本地必要信息
    private String deviceId;
    private String tenantId;
    private String robotId;
    private String appKey;
    private String appSecret;

    private WebSocket webSocket;
    private Gson gson = new Gson();
    private EBotConnectState connectState = EBotConnectState.BOT_DISCONNECT_STATE;
    private IBotConnectionListener connectionListener;
    private IBotMessageListener messageListener;

    private static BotClient botClient;

    public static BotClient getInstance(Context context) {
        if (botClient == null) {
            synchronized (BotClient.class) {
                if (botClient == null)
                    botClient = new BotClient(context);
            }
        }

        return botClient;
    }

    private WebSocketListener webSocketListener = new WebSocketListener() {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            LogUtils.d("onOpen-------------------------");
            setConnectionState(EBotConnectState.BOT_CONNECTED_STATE);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            LogUtils.d("onMessage in text-------------------------" + text);
            try {
                MessageRes e = BotClient.this.gson.fromJson(text, MessageRes.class);
                if (e != null) {
                    BotClient.this.processMessage(e);
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            LogUtils.d("onMessage in byteString-------------------------" + bytes.toString());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            LogUtils.d("onClosing------------------------");
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            LogUtils.d("onClosed----------------------");
            BotClient.this.webSocket = null;
            setConnectionState(EBotConnectState.BOT_DISCONNECT_STATE);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            LogUtils.d("onFailure----------------------");
            BotClient.this.webSocket = null;
            setConnectionState(EBotConnectState.BOT_CONNECT_FAILED_STATE);
        }
    };

    private BotClient(Context context) {
        this.context = context.getApplicationContext();
        processBotMetaAccessKey();
        this.deviceId = Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public void connect() {
        disconnect();
        setConnectionState(EBotConnectState.BOT_CONNECTING_STATE);
        if (this.visitorId == null) {
            setupConnectionFromServer();
        } else {
            connectWebSocket();
        }
    }

    public void disconnect() {
        if (this.webSocket != null) {
            this.webSocket.close(1001, null);
        }
    }

    public void setConnectionListener(IBotConnectionListener listener) {
        BotClient.this.connectionListener = listener;
    }

    public void setMessageListener(IBotMessageListener listener) {
        this.messageListener = listener;
    }

    public EBotConnectState getConnectState() {
        return BotClient.this.connectState;
    }

    public void askSuggestion(String text) {
        if (!TextUtils.isEmpty(text)) {
            BotQuestion question = new BotQuestion();
            question.setChannel("APP");
            question.setQuestion(text);
            question.setUserId(this.visitorId);
            MessageReq req = new MessageReq();
            req.setType("SUGGESTION");
            req.setQuestion(question);
            this.sendMessage(req);
        }
    }

    public void askQuestion(BotMenuItem item, int menuType) {
        if (item != null && item.getItemId() != null) {
            BotQuestion question = new BotQuestion();
            question.setChannel("APP");
            question.setQuestion(item.getItemId());
            question.setUserId(this.visitorId);
            MessageReq req = new MessageReq();
            req.setType("ASKBYID");
            req.setContentType(menuType == 0 ? "MENU" : "RECOMMEND");
            req.setQuestion(question);
            this.sendMessage(req);
        }
    }

    public void askQuestion(final String text) {
        /**
         *
         *第四范式 API调用
        if (text != null && !text.isEmpty()) {
            BotQuestion question = new BotQuestion();
            question.setQuestionType("TEXT");
            question.setChannel("APP");
            question.setQuestion(text);
            question.setUserId(this.visitorId);
            MessageReq req = new MessageReq();
            req.setType("ASK");
            req.setQuestion(question);
            this.sendMessage(req);
        }*/

        create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                TextReq textReq = new TextReq(text);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"),BotClient.this.gson.toJson(textReq));
                Request request = BotRequestFactory.createBotRequest("http://27.223.99.143:11181/office/getAnswers",body);
                e.onNext(HttpManager.getInstance().call(request));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).map(new Function<Response, BaseTextReq>() {
            @Override
            public BaseTextReq apply(@NonNull Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody resBody = response.body();
                    if (resBody != null) {
                        StringBuilder sr = new StringBuilder();
                        BufferedReader br = new BufferedReader(new InputStreamReader(resBody.byteStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sr.append(line);
                        }
                        return gson.fromJson(sr.toString(), BaseTextReq.class);
                    }
                }
                return null;
            }
        }).observeOn(Schedulers.io()).subscribe(new Consumer<BaseTextReq>() {
            @Override
            public void accept(BaseTextReq messageRes) throws Exception {
                processMessage((MessageRes) messageRes.getSST());
            }
        });
    }

    public void askQuestionImage(final File file) {
        if (file != null) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody body = (new MultipartBody.Builder()).setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), fileBody).build();
            Request.Builder builder = new Request.Builder();
            makeSignature(builder);
            final Request request = builder.url(String.format("%s://%s/v1/tenants/%s/robots/%s/user_material/APP/image?visitorId=%s", new Object[]{"https", "bot.4paradigm.com", this.tenantId, this.robotId, this.visitorId}))
                    .post(body).build();
            HttpManager.getInstance().call(request, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.d("onFailure----------------------");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final MaterialImage image = gson.fromJson(response.body().string(), MaterialImage.class);

                    create(new ObservableOnSubscribe<Object>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                            //照片加载到聊天界面
                            String workPath = robotId + "_";
                            File newFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), workPath + image.getMaterialId() + ".jpg");
                            if (!newFile.exists())
                                newFile.createNewFile();
                            FileOutputStream fos = new FileOutputStream(newFile);
                            FileInputStream fis = new FileInputStream(file);
                            byte[] bytes = new byte[4096];
                            int len = 0;
                            while ((len = fis.read(bytes)) > 0) {
                                fos.write(bytes, 0, len);
                            }
                            fos.close();
                            fis.close();
                            MessageContentImage contentImage = new MessageContentImage(newFile.getName());
                            appendMessageWithContent(contentImage, 3, 0);
                        }
                    }).subscribeOn(Schedulers.newThread()).subscribe();

                    BotQuestion question = new BotQuestion();
                    question.setQuestionType("IMAGE");
                    question.setChannel("APP");
                    question.setQuestion(image.getMaterialId());
                    question.setUserId(BotClient.this.visitorId);
                    MessageReq req = new MessageReq();
                    req.setType("ASK");
                    req.setQuestion(question);
                    BotClient.this.sendMessage(req);
                }
            });
        }
    }

    public void askQuestionAudio(InputStream inputStream) {
        if (inputStream != null) {
            Observable.fromArray(inputStream)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .map(new Function<InputStream, AudioReq>() {
                        @Override
                        public AudioReq apply(@NonNull InputStream inputStream) throws Exception {
                            AudioReq audioReq = new AudioReq("", "", "", "");
                            audioReq.setSpeech(Base64.encodeToString(IOUtils.toByteArray(inputStream), Base64.NO_WRAP));
                            return audioReq;
                        }
                    })
                    .flatMap(new Function<AudioReq, ObservableSource<Response>>() {
                        @Override
                        public ObservableSource<Response> apply(@NonNull AudioReq audioReq) throws Exception {

                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), BotClient.this.gson.toJson(audioReq));
                            Request request = new Request.Builder()
                                    .url("http://10.18.1.103:8025/speech/recognizer")
                                    .post(body)
                                    .build();
                            Response response = HttpManager.getInstance().call(request);
                            return Observable.fromArray(response);
                        }
                    })
                    .map(new Function<Response, AudioRes>() {
                        @Override
                        public AudioRes apply(@NonNull Response response) throws Exception {
                            return BotClient.this.gson.fromJson(response.body().string(), AudioRes.class);
                        }
                    })
                    .subscribe(new Consumer<AudioRes>() {
                        @Override
                        public void accept(AudioRes audioRes) throws Exception {
                            switch (audioRes.getErr_no()) {
                                case 0:
                                    askQuestion(audioRes.getResult().get(0));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });

        }
    }


    /*************************private methods*********************/

    private void sendMessage(MessageReq req) {
        if (this.webSocket != null) {
            String text = this.gson.toJson(req);
            LogUtils.d("request text--------" + text);
            boolean result = this.webSocket.send(text);
        } else if (BotClient.this.connectState != EBotConnectState.BOT_CONNECTED_STATE) {
            this.connect();
        }
    }

    private void processMessage(MessageRes messageRes) {
        if ("SUGGESTION".equals(messageRes.getAnswerType())) {
            this.processSuggestionMessage(messageRes);
        } else if ("MENU".equals(messageRes.getAnswerType())) {
            this.processMenuMessage(messageRes, 0);
        } else if ("RECOMMEND".equals(messageRes.getAnswerType())) {
            this.processMenuMessage(messageRes, 1);
        } else {
            this.processAnswerMessage(messageRes);
        }
    }

    private void processSuggestionMessage(MessageRes res) {
        final ArrayList suggestions = new ArrayList();
        Iterator var3 = res.getItems().iterator();

        while (var3.hasNext()) {
            BotAnswerItem item = (BotAnswerItem) var3.next();
            BotMenuItem suggestionItem = new BotMenuItem(item.getId(), item.getContent(), BotMessage.contentTypeFromString(item.getType()));
            suggestions.add(suggestionItem);
        }

        if (this.messageListener != null) {
            BotClient.this.messageListener.onReceivedSuggestion(suggestions);
        }
    }

    private void processAnswerMessage(MessageRes res) {
        LogUtils.d("message response--------------" + BotClient.this.gson.toJson(res));
        int type = BotMessage.contentTypeFromString(res.getAnswerContentType());
        if (type == 1) {
            MessageContentText request = new MessageContentText(res.getAnswer());
            this.appendMessageWithContent(request, 1, 1);
        }
    }

    private void processMenuMessage(MessageRes res, int menyType) {
        ArrayList menuItems = new ArrayList();
        Iterator iterator = res.getItems().iterator();

        while (iterator.hasNext()) {
            BotAnswerItem item = (BotAnswerItem) iterator.next();
            BotMenuItem suggestionItem = new BotMenuItem(item.getId(), item.getContent(), BotMessage.contentTypeFromString(item.getType()));
            menuItems.add(suggestionItem);
        }

        MessageContentMenu content = new MessageContentMenu(menyType, menuItems);
        this.appendMessageWithContent(content, 2, 1);
    }

    private void appendMessageWithContent(MessageContent content, int type, int direction) {
        final BotMessage botMessage = new BotMessage(System.currentTimeMillis(), direction, type, gson.toJson(content));
        switch (type) {
            case 2: //menu
                if (content instanceof MessageContentMenu) {
                    MessageContentMenu menu = (MessageContentMenu) content;
                    if (menu.getMenuType() == MessageContentMenu.TypeMenu) {
                        break;
                    }
                }
            default:
                DaoManager.getInstance().insertBotMessage(botMessage);
                break;
        }
        if (this.messageListener != null) {
            messageListener.onAppendMessage(botMessage);
        }
    }

    private void setupConnectionFromServer() {
        create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                String agentVersion = "Android " + Build.VERSION.RELEASE;
                String hardwareVersion = Build.BRAND + " " + Build.MODEL;
                VisitorReq req = new VisitorReq();
                req.setTenantId(BotClient.this.tenantId);
                req.setUsername(BotClient.this.userName);
                req.setPhone(BotClient.this.phone);
                req.setChannel("APP");
                req.setAgentVersion(agentVersion);
                req.setHardwareVersion(hardwareVersion);
                req.setDeviceId(BotClient.this.deviceId);

                LogUtils.d("userName:--------"+BotClient.this.userName);

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), BotClient.this.gson.toJson(req));
                Request request = BotRequestFactory.createBotRequest(String.format("%s://%s/v1/tenants/%s/visitors",
                        new Object[]{"https", "bot.4paradigm.com", BotClient.this.tenantId}), body);
                e.onNext(HttpManager.getInstance().call(request));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).map(new Function<Response, VisitorRes>() {
            @Override
            public VisitorRes apply(@NonNull Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody resBody = response.body();
                    if (resBody != null) {
                        StringBuilder sr = new StringBuilder();
                        BufferedReader br = new BufferedReader(new InputStreamReader(resBody.byteStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sr.append(line);
                        }
                        return gson.fromJson(sr.toString(), VisitorRes.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<VisitorRes>() {
            @Override
            public void accept(VisitorRes visitorRes) throws Exception {
                BotClient.this.visitorId = visitorRes.getVisitorId();
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<VisitorRes, ObservableSource<Response>>() {
            @Override
            public ObservableSource<Response> apply(@NonNull VisitorRes visitorRes) throws Exception {
                return DeviceInfoObservable();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Response>() {
            @Override
            public void accept(Response response) throws Exception {
                connectWebSocket();
                // fetchRobotNameFromServer();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    private void connectWebSocket() {
        create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                Request request = (new okhttp3.Request.Builder())
                        .url(String.format("%s://%s/message?tenantId=%s&robotId=%s&visitorId=%s&channel=APP&deviceId=%s", new Object[]{"wss", "bot.4paradigm.com", BotClient.this.tenantId, BotClient.this.robotId, BotClient.this.visitorId, BotClient.this.deviceId})).build();
                BotClient.this.webSocket = HttpManager.getInstance().webSocket(request, webSocketListener);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private void fetchRobotNameFromServer() {
        create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                Request request = BotRequestFactory.createBotRequest(String.format("%s://%s/v1/tenants/%s/public_robots/%s",
                        new Object[]{"https", "bot.4paradigm.com", BotClient.this.tenantId, BotClient.this.robotId}));
                e.onNext(HttpManager.getInstance().call(request));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).map(new Function<Response, RobotNameRes>() {
            @Override
            public RobotNameRes apply(@NonNull Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody resBody = response.body();
                    if (resBody != null) {
                        StringBuilder sr = new StringBuilder();
                        BufferedReader br = new BufferedReader(new InputStreamReader(resBody.byteStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sr.append(line);
                        }
                        return gson.fromJson(sr.toString(), RobotNameRes.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RobotNameRes>() {
            @Override
            public void accept(RobotNameRes robotNameRes) throws Exception {
                BotClient.this.robotName = robotNameRes.getName();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    @SuppressLint({"MissingPermission", "NewApi"})
    private Observable<Response> DeviceInfoObservable() {

        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        PackageManager packageManager = this.context.getPackageManager();
        PackageInfo packageInfo = null;

        try {
            packageInfo = packageManager.getPackageInfo(this.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var47) {
            var47.printStackTrace();
        }

        final BotDeviceInfo botDeviceInfo = new BotDeviceInfo();
        botDeviceInfo.deviceId = this.deviceId;

        try {
            botDeviceInfo.imei = telephonyManager.getDeviceId();//getImei
        } catch (Throwable var46) {
            ;
        }

        try {
            botDeviceInfo.androidId = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
        } catch (Throwable var45) {
            ;
        }

        try {
            botDeviceInfo.phoneNum = telephonyManager.getLine1Number();
        } catch (Throwable var44) {
            ;
        }

        try {
            botDeviceInfo.subscriberId = telephonyManager.getSubscriberId();
        } catch (Throwable var43) {
            ;
        }

        try {
            botDeviceInfo.simSerialNum = telephonyManager.getSimSerialNumber();
        } catch (Throwable var42) {
            ;
        }

        try {
            botDeviceInfo.simCountryIso = telephonyManager.getSimCountryIso();
        } catch (Throwable var41) {
            ;
        }

        try {
            botDeviceInfo.simOperator = telephonyManager.getSimOperator();
        } catch (Throwable var40) {
            ;
        }

        try {
            botDeviceInfo.simOperatorName = telephonyManager.getSimOperatorName();
        } catch (Throwable var39) {
            ;
        }

        try {
            botDeviceInfo.networkCountryIso = telephonyManager.getNetworkCountryIso();
        } catch (Throwable var38) {
            ;
        }

        try {
            botDeviceInfo.networkOperator = telephonyManager.getNetworkOperator();
        } catch (Throwable var37) {
            ;
        }

        try {
            botDeviceInfo.networkOperatorName = telephonyManager.getNetworkOperatorName();
        } catch (Throwable var36) {
            ;
        }

        try {
            botDeviceInfo.networkType = telephonyManager.getNetworkType();
        } catch (Throwable var35) {
            ;
        }

        try {
            botDeviceInfo.phoneType = telephonyManager.getPhoneType();
        } catch (Throwable var34) {
            ;
        }

        try {
            botDeviceInfo.cpuInfo = AppUtils.getCpuName();
        } catch (Throwable var33) {
            ;
        }

        try {
            botDeviceInfo.buildRelease = Build.VERSION.RELEASE;
        } catch (Throwable var32) {
            ;
        }

        try {
            botDeviceInfo.buildSdk = Build.VERSION.SDK_INT;
        } catch (Throwable var31) {
            ;
        }

        try {
            botDeviceInfo.buildBrand = Build.BRAND;
        } catch (Throwable var30) {
            ;
        }

        try {
            botDeviceInfo.buildModel = Build.MODEL;
        } catch (Throwable var29) {
            ;
        }

        try {
            botDeviceInfo.buildId = Build.ID;
        } catch (Throwable var28) {
            ;
        }

        try {
            botDeviceInfo.buildDisplay = Build.DISPLAY;
        } catch (Throwable var27) {
            ;
        }

        try {
            botDeviceInfo.buildProduct = Build.PRODUCT;
        } catch (Throwable var26) {
            ;
        }

        try {
            botDeviceInfo.buildManufacturer = Build.MANUFACTURER;
        } catch (Throwable var25) {
            ;
        }

        try {
            botDeviceInfo.buildDevice = Build.DEVICE;
        } catch (Throwable var24) {
            ;
        }

        try {
            botDeviceInfo.buildHardware = Build.HARDWARE;
        } catch (Throwable var23) {
            ;
        }

        try {
            botDeviceInfo.buildBoard = Build.BOARD;
        } catch (Throwable var22) {
            ;
        }

        try {
            botDeviceInfo.wifiAddress = wifiManager.getConnectionInfo().getMacAddress();
        } catch (Throwable var21) {
            ;
        }

        try {
            botDeviceInfo.wifiIpAddress = "" + wifiManager.getConnectionInfo().getIpAddress();
        } catch (Throwable var20) {
            ;
        }

        try {
            botDeviceInfo.displayDensity = displayMetrics.density;
        } catch (Throwable var19) {
            ;
        }

        try {
            botDeviceInfo.displayDensityDpi = displayMetrics.densityDpi;
        } catch (Throwable var18) {
            ;
        }

        try {
            botDeviceInfo.displayXDpi = displayMetrics.xdpi;
        } catch (Throwable var17) {
            ;
        }

        try {
            botDeviceInfo.displayYDpi = displayMetrics.ydpi;
        } catch (Throwable var16) {
            ;
        }

        try {
            botDeviceInfo.displayScaledDensity = displayMetrics.scaledDensity;
        } catch (Throwable var15) {
            ;
        }

        try {
            botDeviceInfo.packageName = packageInfo.packageName;
        } catch (Throwable var14) {
            ;
        }

        try {
            botDeviceInfo.versionName = packageInfo.versionName;
        } catch (Throwable var13) {
            ;
        }

        try {
            botDeviceInfo.versionCode = packageInfo.versionCode;
        } catch (Throwable var12) {
            ;
        }

        try {
            botDeviceInfo.applicationName = this.context.getString(packageInfo.applicationInfo.labelRes);
        } catch (Throwable var11) {
            ;
        }

        try {
            botDeviceInfo.targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
        } catch (Throwable var10) {
            ;
        }

        try {
            botDeviceInfo.minSdkVersion = packageInfo.applicationInfo.minSdkVersion;
        } catch (Throwable var9) {
            ;
        }

        return create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), BotClient.this.gson.toJson(botDeviceInfo));
                Request request = BotClient.this.makeSignature((new okhttp3.Request.Builder()).url(String.format("%s://%s/v1/tenants/%s/robots/%s/sdkdevice/%s/android", new Object[]{"https", "bot.4paradigm.com", BotClient.this.tenantId, BotClient.this.robotId, BotClient.this.visitorId})).post(body)).build();
                e.onNext(HttpManager.getInstance().call(request));
                e.onComplete();
            }
        });

    }

    /**
     * get the necessary data for access the server
     * <p>
     * </p>
     */
    private void processBotMetaAccessKey() {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            final String accessKey = appInfo.metaData.getString("accessKey");
            byte[] e = Base64.decode(accessKey, Base64.DEFAULT);
            String[] item = (new String(e)).split("#");
            if (item.length >= 4) {
                this.tenantId = item[0];
                this.robotId = item[1];
                this.appKey = item[2];
                this.appSecret = item[3];
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private okhttp3.Request.Builder makeSignature(okhttp3.Request.Builder requestBuilder) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException var12) {
            var12.printStackTrace();
            return requestBuilder;
        }

        long time = System.currentTimeMillis();
        String timestamp = (Long.valueOf(time / 1000L)).toString();
        String noice = (Long.valueOf(time % 1000L)).toString();
        String[] items = new String[]{"paradigm_token", this.tenantId, this.robotId, timestamp, noice, this.appSecret};
        Arrays.sort(items);
        StringBuilder text = new StringBuilder(128);
        String[] digest = items;
        int signature = items.length;

        int i;
        for (i = 0; i < signature; ++i) {
            String item = digest[i];
            text.append(item);
        }

        byte[] var13 = messageDigest.digest(text.toString().getBytes());
        StringBuilder var14 = new StringBuilder(var13.length * 2);

        for (i = 0; i < var13.length; ++i) {
            var14.append(String.format("%02x", new Object[]{Byte.valueOf(var13[i])}));
        }

        requestBuilder.addHeader("inner_access_appkey", this.appKey);
        requestBuilder.addHeader("inner_access_noice", noice);
        requestBuilder.addHeader("inner_access_timestamp", timestamp);
        requestBuilder.addHeader("inner_access_signature", var14.toString());
        return requestBuilder;
    }

    private void setConnectionState(final EBotConnectState connectionState) {
        BotClient.this.connectState = connectionState;
        if (BotClient.this.connectionListener != null) {
            connectionListener.onBotConnectionStateChanged(connectionState);
        }
    }
}
