package com.hisense.hiask.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.MainActivity;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hitools.utils.RegularUtils;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class LoginActivity extends BaseActivity<ILogin, LoginPresenter> implements ILogin {

    @BindView(R.id.login_account_input)
    EditText loginAccountInput;
    @BindView(R.id.login_cypher_input)
    EditText loginCypherInput;
    @BindView(R.id.login_memory_check_box)
    CheckBox loginMemoryCheckBox;
    @BindView(R.id.login_login_btn)
    Button loginLoginBtn;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginLoginBtn.setEnabled(checkLoginBtnState());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*********************base override methods******************/
    @Override
    protected void initViewDisplay() {
        loginAccountInput.addTextChangedListener(textWatcher);
        loginCypherInput.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    /*********************mvp override methods********************/

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    @Override
    public void onLoginSuccessful() {
        finish();
        startActivitySafely(MainActivity.class);
    }

    /*******************events and listeners*******************/

    @OnClick({R.id.login_forget_cypher, R.id.login_login_btn, R.id.login_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forget_cypher:
                break;
            case R.id.login_login_btn:
                if (beforeLogin())
                    presenter.login(loginAccountInput.getText().toString().trim(), loginCypherInput.getText().toString().trim());
                break;
            case R.id.login_register_btn:
                break;
        }
    }

    /*******************private methods*********************/

    private boolean checkLoginBtnState() {
        return loginCypherInput.getText().length() > 0 && loginAccountInput.getText().length() > 0;
    }

    private boolean beforeLogin() {

        if (!RegularUtils.isMobile(loginAccountInput.getText().toString().trim())) {
            Toasty.info(this, getString(R.string.login_wrong_phone_format)).show();
            return false;
        }

        if (loginCypherInput.getText().toString().trim().length() < 6) {
            Toasty.info(this, getString(R.string.login_wrong_password_format)).show();
            return false;
        }
        return true;
    }



}
