package com.uplan.miyao.base.helper;

/**
 * Author: Created by zs on 2018/4/16.
 *
 * Description: 退出登录帮助类
 */

public class LoginOutHelper {

    private LoginOutHelper(){}

    /**
     * 退出登录
     */
    public static void loginOut(){
     /*   try{

            //清除本地缓存
            PreferencesUtils.clearString(UiUtils.getContext(), PreferenceConstant.COOKIE_SESSION_ID);
            PreferencesUtils.clearString(UiUtils.getContext(), PreferenceConstant.CSRF_TOKEN);

            //获取当前Top Activity
            Activity activity = ActivityManager.getScreenManager().currentActivity();
            if(activity == null  || activity.isFinishing()){
                //清空栈内Activity
                ActivityManager.getScreenManager().popAllActivity();
                return;
            }

            CommonDialog commonDialog = new CommonDialog(activity).builder().setCancelable(false);
            commonDialog.setSubMessage("登录过期，请重新登录").setOnlyButton("知道了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //清空栈内Activity
                    ActivityManager.getScreenManager().popAllActivity();

                    //打开登录页面
                    LoginActivity.start(activity);
                }
            }).show();

        }catch (Exception ex){
            ex.printStackTrace();
        }*/
    }
}
