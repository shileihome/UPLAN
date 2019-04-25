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
import com.uplan.miyao.util.ToastUtils;

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

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE = 300;

    public static void start(Activity context) {
        Intent starter = new Intent(context, ForgetPwdActivity.class);
        context.startActivityForResult(starter, REQUEST_CODE);
    }


    @Override
    protected void init() {
        setContentView(R.layout.activity_forgetpwd);
        ButterKnife.bind(this);
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
        intent.putExtra("username", etPhotoNo.getText().toString());
        intent.putExtra("password", etPwd.getText().toString());
        setResult(RESULT_CODE, intent);
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
                if(TextUtils.isEmpty(etPhotoNo.getText().toString())){
                    ToastUtils.shortShow("请输入手机号！");
                    return;
                }
                mPresenter.registVerificationCode(etPhotoNo.getText().toString());
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
                String verificationCode=etVerificationCode.getText().toString();
                mPresenter.ModifyPwd(tel,verificationCode, pwd);
                break;

        }
    }


}
