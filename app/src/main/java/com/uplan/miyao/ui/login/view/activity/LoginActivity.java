package com.uplan.miyao.ui.login.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.ui.login.contract.LoginContract;
import com.uplan.miyao.ui.login.model.resp.LoginResp;
import com.uplan.miyao.ui.login.model.resp.VerifyTelResp;
import com.uplan.miyao.ui.login.presenter.LoginPresenter;
import com.uplan.miyao.ui.regist.view.activity.PrivacyActivity;
import com.uplan.miyao.ui.regist.view.activity.RegistActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_photo_no)
    EditText etPhotoNo;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_to_regist)
    TextView tvToRegist;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.rb_privacy)
    RadioButton rbPrivacy;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;

    /** 所登录的账号是否和公众号绑定过 */
    private boolean isBind = false;

    /** 是否选中了阅读隐私按钮 */
    private boolean isPrivacy = true;

    public static final int REGIST_REQUEST_CODE = 100;
    public static final int FORGET_REQUEST_CODE = 200;
    public static final int REGIST_RESULT_CODE = 300;
    public static final int FORGET_RESULT_CODE = 400;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void init() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        etPhotoNo.setText(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL));
        etPhotoNo.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    if (TextUtils.isEmpty(etPhotoNo.getText().toString())) {
                        ToastUtils.shortShow("请输入手机号！");
                        return;
                    }

                    mPresenter.verifyTel(etPhotoNo.getText().toString());
                }
            }
        });
        rbPrivacy.setButtonDrawable(R.drawable.privacy_select);
        rbPrivacy.setOnClickListener(view->{
            if(isPrivacy){
                isPrivacy=false;
                tvLogin.setEnabled(false);
                rbPrivacy.setButtonDrawable(R.drawable.privacy_unselect);
            }else{
                isPrivacy=true;
                tvLogin.setEnabled(true);
                rbPrivacy.setButtonDrawable(R.drawable.privacy_select);
            }
        });

    }


    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
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
    public void dealLoginSuccess(LoginResp data) {
        PreferencesUtils.putBoolean(this, PreferencesUtils.LOGIN_STATE, true);
        PreferencesUtils.putString(this, PreferencesUtils.PLAY_SESSION, data.data.get(1).PLAY_SESSION);
        PreferencesUtils.putString(this, PreferencesUtils.USER_NAME, data.data.get(0).name);
        PreferencesUtils.putString(this, PreferencesUtils.USER_TEL, etPhotoNo.getText().toString());
        PreferencesUtils.putBoolean(this, PreferencesUtils.IS_ACTIVEA, data.data.get(0).is_active);
        PreferencesUtils.putLong(this, PreferencesUtils.EXPIRE_TIME, data.data.get(0).level_end_time);
        LoginActivity.this.finish();
    }

    @Override
    public void dealVerifyTelSucess(VerifyTelResp data) {
        //取消请继续提示
        // ToastUtils.shortShow(data.msg);
    }

    @Override
    public void dealVerifyTelFail(int code, String msg) {
        ToastUtils.shortShow(msg);
        if (code == 1) {
            isBind = true;
            PreferencesUtils.putString(this, PreferencesUtils.USER_TEL, etPhotoNo.getText().toString().trim());
            ForgetPwdActivity.start(this, true);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_delete, R.id.tv_login, R.id.tv_to_regist, R.id.tv_forget_pwd, R.id.tv_privacy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_delete:
                etPhotoNo.setText("");
                etPwd.setText("");
                break;
            case R.id.tv_login:
                if(!isPrivacy){
                    return;
                }
                String tel = etPhotoNo.getText().toString();
                String pwd = etPwd.getText().toString();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.shortShow("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.shortShow("密码不能为空");
                    return;
                }

                mPresenter.login(tel, pwd);
                break;
            case R.id.tv_to_regist:
                RegistActivity.start(this);
                break;
            case R.id.tv_forget_pwd:
                PreferencesUtils.putString(this, PreferencesUtils.USER_TEL, etPhotoNo.getText().toString().trim());
                if (isBind) {
                    ForgetPwdActivity.start(this, true);
                } else {
                    ForgetPwdActivity.start(this, false);
                }
                break;
            case R.id.tv_privacy:
                PrivacyActivity.start(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGIST_REQUEST_CODE) {
            if (resultCode == REGIST_RESULT_CODE) {
                etPhotoNo.setText(data.getStringExtra("username"));
                etPwd.setText(data.getStringExtra("password"));
            }
        } else if (requestCode == FORGET_REQUEST_CODE) {
            if (resultCode == FORGET_RESULT_CODE) {
                etPhotoNo.setText(data.getStringExtra("username"));
                etPwd.setText(data.getStringExtra("password"));
            }
        }

    }

}
