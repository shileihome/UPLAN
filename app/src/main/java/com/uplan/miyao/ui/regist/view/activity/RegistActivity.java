package com.uplan.miyao.ui.regist.view.activity;

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
import com.uplan.miyao.ui.regist.contract.RegistContract;
import com.uplan.miyao.ui.regist.presenter.RegistPresenter;
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

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE = 200;

    int recLen;
    public static void start(Activity context) {
        Intent starter = new Intent(context, RegistActivity.class);
        context.startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
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
        setResult(RESULT_CODE, intent);
        finish();
    }

    @Override
    public void delRegistVerificationCodeSucess(ResponseData data) {

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

                if(recLen>0){
                    return;
                }else{
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
                String verificationCode=etVerificationCode.getText().toString();
                mPresenter.regist(tel, pwd,verificationCode );
                break;

        }
    }

    public void setTimeTask(){
        recLen=60;
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recLen--;
                        tvVerificationCode.setText(recLen+"");
                        if(recLen<0){
                            timer.cancel();
                            tvVerificationCode.setText("重新发送");
                            return;

                        }
                    }
                });
            }
        };
       timer.schedule(task ,1000,1000);

    }


}
