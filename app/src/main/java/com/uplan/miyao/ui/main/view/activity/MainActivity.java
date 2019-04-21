package com.uplan.miyao.ui.main.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.app.ActivityManager;
import com.uplan.miyao.app.UPLANApplication;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.ui.account.view.fragment.AccountFragment;
import com.uplan.miyao.ui.financial.view.fragment.HomeFragment;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.survey.view.fragment.SurveyFragment;
import com.uplan.miyao.ui.vip.view.fragment.VipFragment;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author：Created by Wbin on 2018/4/09
 *
 * Description：首页
 */

public class MainActivity extends AppBaseActivity {

    @BindView(R.id.tv_financial)
    TextView tvFinancial;
    @BindView(R.id.tv_survey)
    TextView tvSurvey;
    @BindView(R.id.tv_discover)
    TextView tvDiscover;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    private long exitTime = 0;
    //三个按钮布局
    @BindView(R.id.financial_layout)
    public RelativeLayout financialLayout;
    @BindView(R.id.survey_layout)
    public RelativeLayout surveyLayout;
    @BindView(R.id.discover_layout)
    public RelativeLayout discoverLayout;
    @BindView(R.id.account_layout)
    public RelativeLayout accountLayout;

    //三个按钮图片及文本
    @BindView(R.id.financial_image)
    public ImageView financialImage;
    @BindView(R.id.survey_image)
    public ImageView surveyImage;
    @BindView(R.id.discover_image)
    public ImageView discoverImage;
    @BindView(R.id.account_image)
    public ImageView accountImage;


    private HomeFragment homeFragment;
    private SurveyFragment surveyFragment;
    private VipFragment discoverFragment;
    private AccountFragment accountFragment;
    FragmentManager fManager;

    /** 选择index: 投资 */
    private static final int SELECT_INDEX_FINANCIAL = 0;

    /** 选择index: 规划宝 */
    private static final int SELECT_INDEX_SURVEY = 1;

    private static final int SELECT_INDEX_DISCOVER = 2;

    /** 选择index: 账户中心页 */
    private static final int SELECT_INDEX_ACCOUNT = 3;

    /** 当前选择index */
    private int mSelectIndex = SELECT_INDEX_FINANCIAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        setSelectItem(financialLayout);
        setTranslucent();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        switch (mSelectIndex) {
            case SELECT_INDEX_FINANCIAL:
                if (homeFragment != null) {
                    //     homeFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_SURVEY:
                if (surveyFragment != null) {
                         surveyFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_DISCOVER:
                if (discoverFragment != null) {
                    //   discoverFragment.updateWebData();
                }
                break;
            case SELECT_INDEX_ACCOUNT:
                if (accountFragment != null) {
                    //   accountFragment.updateWebData();
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.financial_layout, R.id.survey_layout, R.id.discover_layout, R.id.account_layout})
    public void setSelectItem(View view) {

        FragmentTransaction transaction = fManager.beginTransaction();
        switch (view.getId()) {

            case R.id.financial_layout:
                mSelectIndex = SELECT_INDEX_FINANCIAL;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_FINANCIAL);
                financialImage.setImageResource(R.drawable.shouye_select);
                tvFinancial.setTextColor(Color.parseColor("#FF31BCE9"));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment, "HomeWebViewFragment");

                } else {
                    transaction.show(homeFragment);
                }
                break;
            case R.id.survey_layout:

                if(!isLogined()){
                    LoginActivity.start(this);
                    return;
                }

                mSelectIndex = SELECT_INDEX_SURVEY;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_SURVEY);
                surveyImage.setImageResource(R.drawable.guihua_select);
                tvSurvey.setTextColor(Color.parseColor("#FF31BCE9"));
                if (surveyFragment == null) {

                    surveyFragment = new SurveyFragment();
                    transaction.add(R.id.content, surveyFragment, "FinancialWebViewFragment");

                } else {
                    transaction.show(surveyFragment);
                }
                break;
            case R.id.discover_layout:
                mSelectIndex = SELECT_INDEX_DISCOVER;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_DISCOVER);
                discoverImage.setImageResource(R.drawable.faxian_select);
                tvDiscover.setTextColor(Color.parseColor("#FF31BCE9"));
                if (discoverFragment == null) {
                    discoverFragment = new VipFragment();
                    transaction.add(R.id.content, discoverFragment, "discoverFragment");
                } else {
                    transaction.show(discoverFragment);
                }
                break;
            case R.id.account_layout:
                mSelectIndex = SELECT_INDEX_ACCOUNT;
                clearSelect();
                hideFragments(transaction, SELECT_INDEX_ACCOUNT);
                accountImage.setImageResource(R.drawable.wode_select);
                tvAccount.setTextColor(Color.parseColor("#FF31BCE9"));
                if (accountFragment == null) {

                    accountFragment = new AccountFragment();
                    transaction.add(R.id.content, accountFragment, "AccountWebViewFragment");
                } else {
                    transaction.show(accountFragment);
                }
                break;

        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * 清空选择
     */
    public void clearSelect() {
        int bar_item_bg = 0xFFFFFFFF;
        financialImage.setImageResource(R.drawable.shouye_normal);
        financialLayout.setBackgroundColor(bar_item_bg);
        tvFinancial.setTextColor(Color.parseColor("#666666"));
        surveyImage.setImageResource(R.drawable.guihua_normal);
        surveyLayout.setBackgroundColor(bar_item_bg);
        tvSurvey.setTextColor(Color.parseColor("#666666"));
        discoverImage.setImageResource(R.drawable.faxian_normal);
        discoverLayout.setBackgroundColor(bar_item_bg);
        tvDiscover.setTextColor(Color.parseColor("#666666"));
        accountImage.setImageResource(R.drawable.wode_normal);
        accountLayout.setBackgroundColor(bar_item_bg);
        tvAccount.setTextColor(Color.parseColor("#666666"));
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction transaction
     */
    private void hideFragments(FragmentTransaction transaction, int selectIndex) {
        //选中理财页
        if (selectIndex == SELECT_INDEX_FINANCIAL) {
            if (surveyFragment != null) {
                transaction.hide(surveyFragment);
            }
            if (discoverFragment != null) {
                transaction.hide(discoverFragment);
            }
            if (accountFragment != null) {
                transaction.hide(accountFragment);
            }
        }

        //选中规划页
        if (selectIndex == SELECT_INDEX_SURVEY) {
            if (homeFragment != null) {
                transaction.hide(homeFragment);
            }
            if (discoverFragment != null) {
                transaction.hide(discoverFragment);
            }
            if (accountFragment != null) {
                transaction.hide(accountFragment);
            }
        }

        //选中发现页
        if (selectIndex == SELECT_INDEX_DISCOVER) {
            if (homeFragment != null) {
                transaction.hide(homeFragment);
            }
            if (surveyFragment != null) {
                transaction.hide(surveyFragment);
            }
            if (accountFragment != null) {
                transaction.hide(accountFragment);
            }

        }

        //选中账户中心页
        if (selectIndex == SELECT_INDEX_ACCOUNT) {
            if (homeFragment != null) {
                transaction.hide(homeFragment);
            }

            if (surveyFragment != null) {
                transaction.hide(surveyFragment);
            }
            if (discoverFragment != null) {
                transaction.hide(discoverFragment);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show(UPLANApplication.getContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getScreenManager().popAllActivity();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
    public boolean isLogined(){
        return PreferencesUtils.getBoolean(this,PreferencesUtils.LOGIN_STATE);
    }
}
