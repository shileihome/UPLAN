package com.uplan.miyao.ui.login.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.login.contract.ForgetPwdContract;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;
import com.uplan.miyao.ui.login.presenter.ForgetPwdPresenter;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/4/23-11:00
 * Description:
 */
public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.View {

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


    /** 是否绑定过公众号 */
    private boolean isBinding = false;

    /** 是否保存密码 */
    private boolean isSave = false;
    int recLen;

    public static void start(Activity context, boolean isBind, boolean isSave) {
        Intent starter = new Intent(context, ForgetPwdActivity.class);
        starter.putExtra("isBind", isBind);
        starter.putExtra("isSave", isSave);
        context.startActivityForResult(starter, LoginActivity.FORGET_REQUEST_CODE);
    }


    @Override
    protected void init() {
        setContentView(R.layout.activity_forgetpwd);
        ButterKnife.bind(this);
        isBinding = getIntent().getBooleanExtra("isBind", false);
        isSave =getIntent().getBooleanExtra("isSave",true);
        initView();
    }

    private void initView() {
        etPhotoNo.setText(PreferencesUtils.getString(this, PreferencesUtils.USER_TEL));
        if (isBinding) {
            etPwd.setHint("请设置密码");
            etPwdAgain.setHint("请确认密码");
            tvRegist.setText("确定");
        } else {
            etPwd.setHint("请设置新密码");
            etPwdAgain.setHint("请确认新密码");
            tvRegist.setText("确认修改");
        }
    }

    @Override
    protected ForgetPwdPresenter getPresenter() {
        return new ForgetPwdPresenter(this);
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
    public void dealModifySuccess(ForgetPwdResp data) {
        Intent intent = new Intent();
        if(isSave){
            PreferencesUtils.putString(this,PreferencesUtils.USER_PWD,etPwd.getText().toString());
        }
        intent.putExtra("username", etPhotoNo.getText().toString());
        intent.putExtra("password", etPwd.getText().toString());
        setResult(LoginActivity.FORGET_RESULT_CODE, intent);
        finish();
    }

    @Override
    public void delRegistVerificationCodeSucess(ResponseData responseData) {

    }

    @Override
    public void dealFailure(int code, String message) {
        ToastUtils.shortShow(message);
    }


    @OnClick({R.id.iv_back, R.id.iv_delete, R.id.tv_verification_code, R.id.tv_regist})
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
                if (!etPwd.getText().toString().equals(etPwdAgain.getText().toString())) {
                    ToastUtils.shortShow("两次密码不一致，请重新输入!");
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
                mPresenter.ModifyPwd(tel, verificationCode, pwd);
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
                        tvVerificationCode.setText(recLen + "s");
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

}
