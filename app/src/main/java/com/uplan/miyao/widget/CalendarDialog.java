package com.uplan.miyao.widget;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Author: Created by zs on 2018/4/17.
 *
 * Description: 日期选择对话框
 */

public class CalendarDialog {

    /** 上下文 */
    private Activity mContext;

    public CalendarDialog(Activity context){
        this.mContext = context;
    }

    public CalendarDialog show(TimeSelectListener listener){
        Calendar selectDate = Calendar.getInstance();
        int endYear = selectDate.get(Calendar.YEAR);
        int startYear = endYear - 15;

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, 11, 30);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM", Locale.CHINA);

     /*   TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if(listener != null){
                    listener.onTimeSelect(df.format(date));
                }
            }
        }).setType(new boolean[]{true, true, false, false, false, false}).
                setDate(selectDate).setRangDate(startDate , endDate).build();
        pvTime.show();*/
        return this;
    }

    public interface TimeSelectListener{
        void onTimeSelect(String date);
    }
}
