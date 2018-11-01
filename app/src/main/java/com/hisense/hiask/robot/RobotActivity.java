package com.hisense.hiask.robot;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.PermissionChecker;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.OnKeyboardListener;
import com.hisense.hiask.entity.robot.RobotGeoPoiBean;
import com.hisense.hiask.entity.robot.RobotRouteWayBean;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.map.DataTransaction;
import com.hisense.hiask.map.MapActivity;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.robot.adapter.RecordViewHolder;
import com.hisense.hiask.robot.adapter.RobotMenuPluginHolder;
import com.hisense.hiask.robot.adapter.SuggestionAdapter;
import com.hisense.hiask.robot.multi.RobotMenuMnuChildViewProvider;
import com.hisense.hiask.robot.multi.RobotMenuMnuViewProvider;
import com.hisense.hiask.robot.multi.RobotMenuRecChildViewProvider;
import com.hisense.hiask.robot.multi.RobotMenuRecViewProvider;
import com.hisense.hiask.robot.multi.RobotMsgAudioViewProvider;
import com.hisense.hiask.robot.multi.RobotMsgImageViewProvider;
import com.hisense.hiask.robot.multi.RobotMsgTextViewProvider;
import com.hisense.hiask.robot.multi.RobotPoiViewProvider;
import com.hisense.hiask.robot.multi.RobotRouteWayViewProvider;
import com.hisense.hiask.robot.widget.RobotPluginMenuLayout;
import com.hisense.hiask.widget.refresh.RxRefreshLayout;
import com.hisense.hibeans.bot.model.BotMenuItem;
import com.hisense.hibeans.robot.RobotAudioMsgBean;
import com.hisense.hibeans.robot.RobotImageMsgBean;
import com.hisense.hibeans.robot.RobotMenuMnuList;
import com.hisense.hibeans.robot.RobotMenuRecBean;
import com.hisense.hibeans.robot.RobotMenuRecList;
import com.hisense.hibeans.robot.RobotTextMsgBean;
import com.hisense.hibot.bot.enumdefine.EBotConnectState;
import com.hisense.himultitype.view.LinearMultiView;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.KeyboardUtils;
import com.hisense.hitools.utils.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/4/25.
 */

public class RobotActivity extends BaseActivity<IRobot, RobotPresenter> implements IRobot,
        RobotMenuRecChildViewProvider.IRobotMenuRecItemListener,
        RobotMenuMnuChildViewProvider.IRobotMenuMnuItemListener,
        RobotMenuPluginHolder.IRobotPluginClickListener,
        RobotMsgAudioViewProvider.IRobotMsgAudioItemListener,
        RobotRouteWayViewProvider.IRouteWayNavListener,
        RobotPoiViewProvider.IGeoPoiListener {

    @BindView(R.id.robot_message_list)
    LinearMultiView robotMessageList;
    @BindView(R.id.robot_suggestion_list)
    ListView robotSuggestionList;
    @BindView(R.id.robot_input_key)
    ImageButton robotInputKey;
    @BindView(R.id.robot_input_audio)
    ImageButton robotInputAudio;
    @BindView(R.id.robot_input_text)
    EditText robotInputText;
    @BindView(R.id.robot_input_record)
    Button robotInputRecord;
    @BindView(R.id.robot_input_plugin)
    ImageButton robotInputPlugin;
    @BindView(R.id.robot_input_send)
    Button robotInputSend;
    @BindView(R.id.robot_plugin_menu)
    RobotPluginMenuLayout robotPluginMenu;
    @BindView(R.id.robot_record_volume)
    ImageView robotRecordVolume;
    @BindView(R.id.robot_record_state)
    TextView robotRecordState;
    @BindView(R.id.robot_record_control)
    RelativeLayout robotRecordControl;
    @BindView(R.id.robot_toolbar_title)
    TextView robotToolbarTitle;
    @BindView(R.id.robot_message_list_refresh_layout)
    RxRefreshLayout robotMessageListRefreshLayout;

    private static final int REQUEST_CODE_GET_PICTURE_FROM_ALBUM = 0x0001;
    private static final int REQUEST_CODE_GET_PICTURE_FROM_CAMERA = 0x0002;

    private SuggestionAdapter suggestionAdapter;
    private RobotMenuPluginHolder pluginHolder;
    private RecordViewHolder recordViewHolder;

    private Uri uriCapture;

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            RobotActivity.this.setSendButton();
            final String text = s.toString().trim();
            if (text.length() > 0) {
                RobotActivity.this.robotSuggestionList.setVisibility(View.VISIBLE);
                presenter.askSuggestion(text);
            } else {
                RobotActivity.this.robotSuggestionList.setVisibility(View.GONE);
            }
        }
    };

    /*******************base override methods***************/
    @Override
    protected void initViewDisplay() {
        initMessageMultiView();
        initRobotPluginMenu();
        initMessageListRefreshLayout();
        recordViewHolder = new RecordViewHolder(this.robotRecordControl);
        suggestionAdapter = new SuggestionAdapter(this);
        robotSuggestionList.setAdapter(suggestionAdapter);
        robotSuggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BotMenuItem item = (BotMenuItem) suggestionAdapter.getItem(position);
                String text = item.getContent().trim();
                if (text.length() > 0) {
                    RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 0, text);
                    presenter.postMessageToMain(textBean);
                    presenter.askQuestion(text);
                    RobotActivity.this.robotInputText.setText("");
                }
            }
        });
    }

    @Override
    protected void initEventsAndListeners() {
        robotInputText.addTextChangedListener(inputTextWatcher);
        robotMessageListRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                LogUtils.d("on load More....................");
            }
        });

        robotInputRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotActivity.this.robotInputRecord.setText(R.string.robot_audio_release_to_send);
                        RobotActivity.this.startRecognize();
                        break;
                    case MotionEvent.ACTION_UP:
                        RobotActivity.this.robotInputRecord.setText(R.string.robot_audio_hold_to_talk);
                        if (RobotActivity.this.recordViewHolder.isCancel()) {
                            RobotActivity.this.cancelRecognize();
                        } else {
                            RobotActivity.this.finishRecognize();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() < 10.0F) {
                            RobotActivity.this.robotInputRecord.setText(R.string.robot_record_release_to_cancel);
                            RobotActivity.this.recordViewHolder.setCancel(true);
                        } else {
                            RobotActivity.this.robotInputRecord.setText(R.string.robot_audio_release_to_send);
                            RobotActivity.this.recordViewHolder.setCancel(false);
                        }
                }
                return true;
            }
        });
    }

    @Override
    protected void initPresenter() {
        presenter = new RobotPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_robot;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        immersionBar.keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup && robotPluginMenu.getVisibility() == View.VISIBLE) {
                    robotPluginMenu.setVisibility(View.GONE);
                }
            }
        }).init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GET_PICTURE_FROM_ALBUM:
                    if (EmptyUtils.isNotEmpty(data.getData()))
                        presenter.askQuestionImage(data.getData(), false);
                    break;
                case REQUEST_CODE_GET_PICTURE_FROM_CAMERA:
                    LogUtils.d("this uriCapture--------------" + this.uriCapture);
                    if (EmptyUtils.isNotEmpty(this.uriCapture))
                        presenter.askQuestionImage(this.uriCapture, true);
                    break;
                default:
                    break;
            }
        }
    }

    /*****************mvp override methods******************/

    @Override
    public void notifyBotConnectState(EBotConnectState state) {
        switch (state) {
            case BOT_DISCONNECT_STATE:
                this.robotToolbarTitle.setText(R.string.robot_toolbar_title_disconnect);
                break;
            case BOT_CONNECTING_STATE:
                this.robotToolbarTitle.setText(R.string.robot_toolbar_title_connecting);
                break;
            case BOT_CONNECT_FAILED_STATE:
                this.robotToolbarTitle.setText(R.string.robot_toolbar_title_connect_failed);
                break;
            case BOT_CONNECTED_STATE:
                this.robotToolbarTitle.setText(R.string.robot_toolbar_title);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBotSuggestionUpdate(ArrayList<BotMenuItem> items) {
        suggestionAdapter.setSuggestionList(items);
        suggestionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBotMessageUpdate(Object item) {
        this.robotMessageList.addItem(item);
        robotMessageList.smoothScrollToBottom();
    }

    @Override
    public void onRecordRmsChanged(final int rms) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recordViewHolder.setVolume(rms);
            }
        });
    }

    @Override
    public void onCacheDataPrepared(List<Object> data) {
        this.robotMessageList.resetItems(data);
    }

    @Override
    public void onDataPreparedStarted() {

    }

    @Override
    public void onDataPreparedSuccess(List<Object> data) {

    }

    @Override
    public void onDataPreparedFailed() {

    }

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /**********************events and listeners**************/

    @OnClick({R.id.robot_input_key, R.id.robot_input_audio, R.id.robot_input_plugin, R.id.robot_input_send, R.id.robot_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.robot_title_back:
                this.onBackPressed();
                break;
            case R.id.robot_input_key:
                switchTextInputMode();
                break;
            case R.id.robot_input_audio:
                switchAudioInputMode();
                break;
            case R.id.robot_input_plugin:
                switchPluginInputMode();
                break;
            case R.id.robot_input_send:
                sendTextToServer();
                break;
        }
    }

    @Override
    public void onRobotMenuRecItemClicked(RobotMenuRecBean item) {
        String text = item.getContent().trim();
        if (text.length() > 0) {
            RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 0, text);
            presenter.postMessageToMain(textBean);
            RobotActivity.this.robotInputText.setText("");
        }
        presenter.askQuestion(item, 1);
    }

    @Override
    public void onRobotMenuMnuItemClicked(RobotMenuMnuList.RobotMenuMnuBean item) {
        String text = item.getContent().trim();
        if (text.length() > 0) {
            RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 0, text);
            presenter.postMessageToMain(textBean);
            RobotActivity.this.robotInputText.setText("");
        }
        presenter.askQuestion(item, 0);
    }

    @Override
    public void onRobotPluginItemClicked(View view) {
        if ((int) view.getTag() == 1001) {
            LogUtils.d("on album plugin clicked----------------------");
            getPictureFromAlbum();
        } else if ((int) view.getTag() == 1002) {
            LogUtils.d("on camera plugin clicked----------------------");
            getPictureFromCamera();
        }
    }

    @Override
    public void onRobotMsgAudioItemClicked(RobotAudioMsgBean item) {
        presenter.playAudioItem(item);
    }

    @Override
    public void onRouteWayItemClicked(RobotRouteWayBean item) {
        Log.d("RobotActivity", "onRouteWayItemClicked");
        DataTransaction.getInstance().clear();
        DataTransaction.getInstance().setInfo(item);
        DataTransaction.getInstance().setType(0);
        startActivitySafely(MapActivity.class);
    }

    @Override
    public void onGeoPoiItemClicked(RobotGeoPoiBean item) {
        DataTransaction.getInstance().clear();
        DataTransaction.getInstance().setInfo(item);
        DataTransaction.getInstance().setType(1);
        startActivitySafely(MapActivity.class);
    }

    /*********************private methods******************/

    private void setSendButton() {
        boolean showSend = this.robotInputText.getText().length() > 0;
        this.robotInputSend.setVisibility(showSend ? View.VISIBLE : View.GONE);
        // this.robotInputPlugin.setVisibility(showSend ? View.GONE : View.VISIBLE);
    }

    private void initMessageMultiView() {
        robotMessageList.getLayoutManager().setStackFromEnd(true);
        robotMessageList.getAdapter().register(RobotTextMsgBean.class, new RobotMsgTextViewProvider());
        robotMessageList.getAdapter().register(RobotMenuRecList.class, new RobotMenuRecViewProvider(this));
        robotMessageList.getAdapter().register(RobotMenuMnuList.class, new RobotMenuMnuViewProvider(this));
        robotMessageList.getAdapter().register(RobotImageMsgBean.class, new RobotMsgImageViewProvider());
        robotMessageList.getAdapter().register(RobotAudioMsgBean.class, new RobotMsgAudioViewProvider(this));
        robotMessageList.getAdapter().register(RobotRouteWayBean.class, new RobotRouteWayViewProvider(this, this));
        robotMessageList.getAdapter().register(RobotGeoPoiBean.class, new RobotPoiViewProvider(this, this));
    }

    private void initMessageListRefreshLayout() {
        robotMessageListRefreshLayout.setEnableLoadmore(true);
        robotMessageListRefreshLayout.setEnableRefresh(false);
    }

    private void initRobotPluginMenu() {
        pluginHolder = new RobotMenuPluginHolder(this);
        pluginHolder.setRobotPluginClickListener(this);
        pluginHolder.initializePluginItem(presenter.generateRobotPlugins());
        robotPluginMenu.addViewToViewPager(pluginHolder.getViewList());
    }

    private void getPictureFromAlbum() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, REQUEST_CODE_GET_PICTURE_FROM_ALBUM);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getPictureFromCamera() {
        if (this.selfPermissionGranted("android.permission.WRITE_EXTERNAL_STORAGE")) {
            String name = "photo" + System.currentTimeMillis();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", name + ".jpeg");
            contentValues.put("_display_name", name);
            contentValues.put("mime_type", "image/jpeg");
            this.uriCapture = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, this.uriCapture);
            startActivityForResult(intent, REQUEST_CODE_GET_PICTURE_FROM_CAMERA);
        }
    }

    private void startRecognize() {
        if (this.selfPermissionGranted("android.permission.RECORD_AUDIO")) {
            this.robotRecordControl.setVisibility(View.VISIBLE);
            this.recordViewHolder.setCancel(false);
            this.recordViewHolder.setVolume(0.0F);
            presenter.startAsstVoiceRecognize();
        }
    }

    private void cancelRecognize() {
        this.robotRecordControl.setVisibility(View.GONE);
        presenter.cancelAsstVoiceRecognize();
    }

    private void finishRecognize() {
        this.robotRecordControl.setVisibility(View.GONE);
        presenter.stopAsstVoiceRecognize();
    }

    private boolean selfPermissionGranted(String permission) {
        boolean result = true;
        int targetSdkVersion = 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 23) {
            if (targetSdkVersion > 23) {
                result = this.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                if (!result) {
                    this.requestPermissions(new String[]{permission}, 0);
                }
            } else {
                result = PermissionChecker.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }

    private void switchTextInputMode() {
        robotInputKey.setVisibility(View.GONE);
        robotInputAudio.setVisibility(View.VISIBLE);
        robotInputText.setVisibility(View.VISIBLE);
        robotInputRecord.setVisibility(View.GONE);
        KeyboardUtils.showSoftInput(this.robotInputText);
    }

    private void switchAudioInputMode() {
        hideSoftKeyBoard();
        robotPluginMenu.setVisibility(View.GONE);
        robotInputAudio.setVisibility(View.GONE);
        robotInputKey.setVisibility(View.VISIBLE);
        robotInputText.setVisibility(View.GONE);
        robotInputRecord.setVisibility(View.VISIBLE);
    }

    private void switchPluginInputMode() {
        robotInputAudio.setVisibility(View.VISIBLE);
        robotInputKey.setVisibility(View.GONE);
        robotInputRecord.setVisibility(View.GONE);
        robotInputText.setVisibility(View.VISIBLE);
        if (robotPluginMenu.getVisibility() != View.VISIBLE)
            robotPluginMenu.postDelayed(new Runnable() {
                @Override
                public void run() {
                    robotPluginMenu.setVisibility(View.VISIBLE);
                }
            }, 50);
        hideSoftKeyBoard();
    }

    private void sendTextToServer() {
        String text = this.robotInputText.getText().toString().trim();
        if (text.length() > 0) {
            RobotTextMsgBean textBean = new RobotTextMsgBean(System.currentTimeMillis(), 0, text);
            presenter.postMessageToMain(textBean);
            presenter.askQuestion(text);
            this.robotInputText.setText("");
        }
    }


}
