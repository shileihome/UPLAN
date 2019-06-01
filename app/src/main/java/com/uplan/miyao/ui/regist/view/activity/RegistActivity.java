package com.uplan.miyao.ui.regist.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.regist.contract.RegistContract;
import com.uplan.miyao.ui.regist.presenter.RegistPresenter;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistActivity extends BaseActivity<RegistPresenter> implements RegistContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_photo_no)
    EditText etPhotoNo;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_again)
    EditText etPwdAgain;
    @BindView(R.id.tv_verification_code)
    TextView tvVerificationCode;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.rb_privacy)
    RadioButton rbPrivacy;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;


    /** 是否选中了阅读隐私按钮 */
    private boolean isPrivacy = true;

    int recLen;

    public static void start(Activity context) {
        Intent starter = new Intent(context, RegistActivity.class);
        context.startActivityForResult(starter, LoginActivity.REGIST_REQUEST_CODE);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        etPhotoNo.setText(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL));
        rbPrivacy.setButtonDrawable(R.drawable.privacy_select);
        rbPrivacy.setOnClickListener(view->{
            if(isPrivacy){
                isPrivacy=false;
                tvRegist.setEnabled(false);
                rbPrivacy.setButtonDrawable(R.drawable.privacy_unselect);
            }else{
                isPrivacy=true;
                tvRegist.setEnabled(true);
                rbPrivacy.setButtonDrawable(R.drawable.privacy_select);
            }
        });
    }

    @Override
    protected RegistPresenter getPresenter() {
        return new RegistPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {
        ToastUtils.shortShow(message);
    }

    @Override
    public void loading() {
        showLoadingDialog();
    }

    @Override
    public void unLoad() {
        hideLoadingDialog();
    }

    @Override
    public void dealRegistSuccess(ResponseData data) {
        Intent intent = new Intent();
        intent.putExtra("username", etPhotoNo.getText().toString());
        intent.putExtra("password", etPwd.getText().toString());
        setResult(LoginActivity.REGIST_RESULT_CODE, intent);
        finish();
    }

    @Override
    public void delRegistVerificationCodeSucess(ResponseData data) {

    }


    @OnClick({R.id.iv_back, R.id.iv_delete, R.id.tv_verification_code, R.id.tv_regist, R.id.tv_privacy,R.id.tv_platform})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_delete:
                etPhotoNo.setText("");
                etPwd.setText("");
                etPwdAgain.setText("");
                break;
            case R.id.tv_verification_code:
                if (TextUtils.isEmpty(etPhotoNo.getText().toString())) {
                    ToastUtils.shortShow("请输入手机号！");
                    return;
                }

                if (recLen > 0) {
                    return;
                } else {
                    setTimeTask();
                    mPresenter.registVerificationCode(etPhotoNo.getText().toString());
                }
                break;
            case R.id.tv_regist:
                if (!isPrivacy) {
                    return;
                }
                if (TextUtils.isEmpty(etPhotoNo.getText().toString())) {
                    ToastUtils.shortShow("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText().toString())) {
                    ToastUtils.shortShow("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(etPwdAgain.getText().toString())) {
                    ToastUtils.shortShow("请再次输入密码");
                    return;
                }
                if (TextUtils.isEmpty(etVerificationCode.getText().toString())) {
                    ToastUtils.shortShow("请输入验证码");
                    return;
                }

                if (!etPwd.getText().toString().equals(etPwdAgain.getText().toString())) {
                    ToastUtils.shortShow("两次密码不一致");
                    return;
                }

                String tel = etPhotoNo.getText().toString();
                String pwd = etPwd.getText().toString();
                String verificationCode = etVerificationCode.getText().toString();
                mPresenter.regist(tel, pwd, verificationCode);
                break;
            case R.id.tv_privacy:
                PrivacyActivity.start(this);
                break;
            case R.id.tv_platform:
                PlatformActivity.start(this);
                break;
        }
    }

    public void setTimeTask() {
        recLen = 60;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recLen--;
                        tvVerificationCode.setText(recLen + "");
                        if (recLen < 0) {
                            timer.cancel();
                            tvVerificationCode.setText("重新发送");
                            return;

                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);

    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
}
