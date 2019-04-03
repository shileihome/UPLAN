package com.uplan.miyao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.util.ScreenUtils;

/**
 * Author: Created by zs on 2018/4/14.
 *
 * Description: 通用导航布局
 */

public class NavigateRelativeLayout extends LinearLayout {

    /** 左边文案 */
    private TextView mTvLeftText;

    /** 右边文案 */
    private TextView mTvRightText;

    /** 右边图标 */
    private ImageView mIvRightIcon;

    /** 箭头 */
    private ImageView mIvArrow;

    /** 状态描述 */
    private TextView mTvStatusDesc;

    /** 分割线 */
    private View mDivideLine;

    /*最外层布局*/
    private RelativeLayout mRLayout;

    /** 左边文案、字体颜色、字体大小 */
    private String mLeftText;
    private int mLeftColor;
    private float mLeftSize;

    /** 右边文案、字体颜色、字体大小或者图标 */
    private String mRightText;
    private int mRightColor;
    private float mRightSize;
    private Drawable mRightIcon;

    /** 自定义背景*/
    private Drawable mBackground;

    /** 显示箭头：true-显示 false-隐藏*/
    private boolean mShowArrow;

    /** 显示分割线：true-显示 false-隐藏*/
    private boolean mShowDivide;


    public NavigateRelativeLayout(Context context) {
        this(context, null);
    }

    public NavigateRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigateRelativeLayout, defStyleAttr, 0);
        int textColor = getResources().getColor(R.color.color_4a4a4a);

        mLeftSize = a.getDimension(R.styleable.NavigateRelativeLayout_navigate_left_size, 15);
        mLeftColor = a.getColor(R.styleable.NavigateRelativeLayout_navigate_left_color, textColor);
        mLeftText = a.getString(R.styleable.NavigateRelativeLayout_navigate_left_text);

        mRightSize = a.getDimension(R.styleable.NavigateRelativeLayout_navigate_right_size, 14);
        mRightColor = a.getColor(R.styleable.NavigateRelativeLayout_navigate_right_color, textColor);
        mRightText = a.getString(R.styleable.NavigateRelativeLayout_navigate_right_text);
        mRightIcon = a.getDrawable(R.styleable.NavigateRelativeLayout_navigate_right_icon);
        mBackground=a.getDrawable(R.styleable.NavigateRelativeLayout_navigate_background);

        mShowArrow = a.getBoolean(R.styleable.NavigateRelativeLayout_navigate_show_arrow, true);
        mShowDivide = a.getBoolean(R.styleable.NavigateRelativeLayout_navigate_show_divide, true);

        a.recycle();

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_navigate, this, true);
        mTvLeftText = (TextView) view.findViewById(R.id.tv_left_text);
        mTvRightText = (TextView) view.findViewById(R.id.tv_right_text);
        mIvRightIcon = (ImageView) view.findViewById(R.id.iv_right_icon);
        mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        mTvStatusDesc = (TextView) view.findViewById(R.id.tv_status_desc);
        mDivideLine = view.findViewById(R.id.view_divide);
        mRLayout= (RelativeLayout) view.findViewById(R.id.rl_relative);

        mTvLeftText.setText(mLeftText);
        mTvLeftText.setTextColor(mLeftColor);
        mTvLeftText.setTextSize(mLeftSize);

        if(!TextUtils.isEmpty(mRightText)){
            mTvRightText.setVisibility(View.VISIBLE);
            mIvRightIcon.setVisibility(View.GONE);
            mTvRightText.setText(mRightText);
            mTvRightText.setTextColor(mRightColor);
            mTvRightText.setTextSize(mRightSize);
        }else if(mRightIcon != null){
            mTvRightText.setVisibility(View.GONE);
            mIvRightIcon.setVisibility(View.VISIBLE);
            mIvRightIcon.setImageDrawable(mRightIcon);
        }

        mTvRightText.setVisibility(mShowArrow ? View.VISIBLE : View.GONE);
        mIvRightIcon.setVisibility(mShowArrow ? View.VISIBLE : View.GONE);

        if(mBackground!=null){
            mRLayout.setBackground(mBackground);
        }else{
            mRLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        mIvArrow.setVisibility(mShowArrow ? View.VISIBLE : View.GONE);
        mDivideLine.setVisibility(mShowDivide ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置左边文案
     *
     * @param text 文案
     */
    public void setLeftText(CharSequence text){
        mTvLeftText.setText(text);
    }

    /**
     * 设置右边文案
     *
     * @param text 文案
     * @param color 字体颜色
     */
    public void setRightText(CharSequence text, int color){
        mIvArrow.setVisibility(View.VISIBLE);
        mTvRightText.setVisibility(View.VISIBLE);
        mIvRightIcon.setVisibility(View.GONE);
        mTvStatusDesc.setVisibility(View.GONE);
        mTvRightText.setText(text);
        mTvRightText.setTextColor(getResources().getColor(color));
    }

    /**
     * 设置右边图标
     *
     * @param iconResource 图片资源
     */
    public void setRightIcon(int iconResource){
        mIvArrow.setVisibility(View.VISIBLE);
        mTvRightText.setVisibility(View.GONE);
        mIvRightIcon.setVisibility(View.VISIBLE);
        mTvStatusDesc.setVisibility(View.GONE);
        mIvRightIcon.setImageResource(iconResource);
    }

    /**
     * 设置状态描述文案
     *
     * @param text 文案
     * @param iconResource 图片资源
     */
    public void setStatusDesc(CharSequence text, int iconResource){
        mIvArrow.setVisibility(View.VISIBLE);
        mTvRightText.setVisibility(View.GONE);
        mIvRightIcon.setVisibility(View.GONE);
        mTvStatusDesc.setVisibility(View.VISIBLE);
        mTvStatusDesc.setText(text);
        Drawable drawable = UiUtils.getDrawable(iconResource);
        if(drawable != null){
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvStatusDesc.setCompoundDrawables(drawable, null, null, null);
            mTvStatusDesc.setCompoundDrawablePadding((int)ScreenUtils.dpToPx(getContext(), 8));
        }
    }

    /**
     * 设置状态描述文案
     *
     * @param text 文案
     */
    public void setStatusDesc(CharSequence text){
        mIvArrow.setVisibility(View.VISIBLE);
        mTvRightText.setVisibility(View.GONE);
        mIvRightIcon.setVisibility(View.GONE);
        mTvStatusDesc.setVisibility(View.VISIBLE);
        mTvStatusDesc.setText(text);
    }

    /**
     * 设置状态描述文案颜色
     */
    public void setStatusDescColor(int color){
        mTvStatusDesc.setTextColor(getResources().getColor(color));
    }

    /**
     * 显示分割线
     *
     * @param showDivide true: 显示  false: 隐藏
     */
    public void showDivide(boolean showDivide){
        mDivideLine.setVisibility(mShowDivide ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取 右侧TextView 显示的文字
     * @return String
     */
    public String getRightText(){
        return TextUtils.isEmpty(mTvRightText.getText()) ? "" : mTvRightText.getText().toString();
    }

    /**
     * 设置右边箭头隐藏
     * @param right textview右侧padding值（px）
     */
    public void setArrowInVisible(int right){
        mIvArrow.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        mTvRightText.setLayoutParams(lp);
        mTvRightText.setPadding(0,0,right,0);
    }


    }
