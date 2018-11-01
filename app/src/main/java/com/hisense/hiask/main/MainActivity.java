package com.hisense.hiask.main;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.fragment.bank.BankFragment;
import com.hisense.hiask.main.fragment.home.HomeFragment;
import com.hisense.hiask.main.fragment.my.MyFragment;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.robot.RobotActivity;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    public static final String MAIN_FORM_DATA_TRANSFER_KEY = "MAIN_FORM_DATA_TRANSFER_KEY";
    private static final long MAIN_ON_BACK_PRESS_TIME_OUT = 3000;

    @BindView(R.id.main_bottombar_bank_button)
    ImageView mainBottombarBankButton;
    @BindView(R.id.main_bottombar_bank_title)
    TextView mainBottombarBankTitle;
    @BindView(R.id.main_bottombar_my_button)
    ImageView mainBottombarMyButton;
    @BindView(R.id.main_bottombar_my_title)
    TextView mainBottombarMyTitle;


    private long onBackPressedTime = 0;
    private BankFragment bankFragment;
    private MyFragment myFragment;
    private HomeFragment homeFragment;


    /***********************base override methods******************/
    @Override
    protected void initViewDisplay() {
        //showBankUI();
        switchFragment(2);
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - onBackPressedTime > MAIN_ON_BACK_PRESS_TIME_OUT) {
            onBackPressedTime = currentTime;
            Toasty.info(this, getString(R.string.main_on_back_press_title)).show();
        } else {
            finish();
        }
    }

    /********************mvp override methods**********************/

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /*********************events and listeners*********************/


    @OnClick({R.id.main_bottombar_bank_layout, R.id.main_bottombar_my_layout,R.id.main_bottombar_robot_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottombar_bank_layout:
                showBankUI();
                break;
            case R.id.main_bottombar_my_layout:
                showMyUI();
                break;
            case R.id.main_bottombar_robot_button:
                startActivitySafely(RobotActivity.class);
                break;
        }
    }

    /*********************private methods*************************/

    private void switchFragment(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (pos) {
            case 0:
                if (bankFragment == null) {
                    bankFragment = BankFragment.newInstance();
                    transaction.add(R.id.main_content, bankFragment);
                    bankFragment.setUserVisibleHint(true);
                } else {
                    transaction.show(bankFragment);
                }
                break;
            case 1:
                if (myFragment == null) {
                    myFragment = MyFragment.newInstance();
                    transaction.add(R.id.main_content, myFragment);
                    myFragment.setUserVisibleHint(true);
                } else {
                    transaction.show(myFragment);
                }
                break;
            case 2:
                if(homeFragment==null) {
                    homeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.main_content,homeFragment);
                    homeFragment.setUserVisibleHint(true);
                }else {
                    transaction.show(homeFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (EmptyUtils.isNotEmpty(bankFragment))
            transaction.hide(bankFragment);

        if (EmptyUtils.isNotEmpty(myFragment))
            transaction.hide(myFragment);

    }

    private void showBankUI() {
        switchFragment(0);
        mainBottombarBankButton.setImageResource(R.drawable.main_lib_selected);
        mainBottombarBankTitle.setTextColor(getResources().getColor(R.color.main_bottom_bar_selected));
        mainBottombarMyButton.setImageResource(R.drawable.main_my_normal);
        mainBottombarMyTitle.setTextColor(getResources().getColor(R.color.main_bottom_bar_normal));
    }

    private void showMyUI() {
        switchFragment(1);
        mainBottombarBankButton.setImageResource(R.drawable.main_lib_normal);
        mainBottombarBankTitle.setTextColor(getResources().getColor(R.color.main_bottom_bar_normal));
        mainBottombarMyButton.setImageResource(R.drawable.main_my_selected);
        mainBottombarMyTitle.setTextColor(getResources().getColor(R.color.main_bottom_bar_selected));
    }
}
