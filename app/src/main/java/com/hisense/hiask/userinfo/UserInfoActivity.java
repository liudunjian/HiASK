package com.hisense.hiask.userinfo;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hisense.hiask.constant.SpConstant;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hitools.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class UserInfoActivity extends BaseActivity<IUserInfo, UserInfoPresenter> implements IUserInfo {

    @BindView(R.id.user_info_photo)
    ImageView userInfoPhoto;
    @BindView(R.id.user_info_nick_name)
    TextView userInfoNickName;

    @Override
    protected void initViewDisplay() {
        userInfoNickName.setText(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_NICK_NAME, "过客"));
        Glide.with(this).load(SpUtils.getInstance(SpUtils.SP_ACCOUNT_KEY).getString(SpConstant.SP_USER_PHOTO_URL, "")).into(userInfoPhoto);
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new UserInfoPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_userinfo;
    }

    /**********************mvp override methods******************/
    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /**********************listeners and events******************/
    @OnClick(R.id.userinfo_title_back)
    public void onViewClicked() {
        this.onBackPressed();
    }

}
