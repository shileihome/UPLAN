package com.uplan.miyao.app;

import android.app.Activity;
import android.util.Log;

import com.uplan.miyao.ui.home.view.activity.MainActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by yabusai on 15/2/10.
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        activityStack = new Stack<Activity>();
    }

    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 退出栈顶Activity
     * @param activity  activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
            if (currentActivity() != null)
                Log.e("TAG", "====" + currentActivity().getClass().getName());
        }
    }


    /**
     * 推出当前activity
     */
    public void popCurrActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * @return 获得当前栈顶Activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    public Activity getFirstActivity(){
        Activity activity = null;
        if (!activityStack.empty())
            activity = activityStack.firstElement();
        return activity;
    }

    public Activity getActivity(Class clazz) {
        Activity activity = null;
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity nextActivity = it.next();
            if(nextActivity != null && nextActivity.getClass().equals(clazz)){
                activity = nextActivity;
            }
        }
        return activity;
    }

    /**
     * 将当前Activity推入栈中
     * @param activity activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 推出所有Activity
     */
    public void popAllActivity() {
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    // 退出栈中所有Activity
    public void popAllActivityExceptOne() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(MainActivity.class)){
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出栈顶的栈
     * @param count　栈顶栈的数量
     */
    public void popActivityWithNumber(int count) {
        for(int i=0;i<count;i++){
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(MainActivity.class)){
                break;
            }

            popActivity(activity);
        }
    }

    /**
     * 使某个activity到栈顶
     *
     * @param className 需要到栈顶的Activity
     */
    public void moveActivity2Top(String className){
        while(true){
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (className.equals(activity.getClass().getName())){
                break;
            }
            popActivity(activity);
        }
    }

}
