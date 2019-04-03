package com.uplan.miyao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uplan.miyao.R;


/**
 * Author: Created by zs on 2018/4/14.
 *
 * Description: 通用可编辑布局
 */

public class EditableRelativeLayout extends LinearLayout{

    /** 左边文案 */
    private TextView mTvLeftText;

    /** EditText */
    private EditText mEtText;

    /** EditText */
    private ImageView mIvClose;

    /** 箭头 */
    private ImageView mIvArrow;

    /** 分割线 */
    private View mDivideLine;

    /** 编辑布局 */
    private RelativeLayout mRlEdit;

    /** TextView布局 */
    private RelativeLayout mRlTv;

    /** TextView内容 */
    private TextView mTvText;

    /** 左边文案属性 */
    private String mLeftText;

    /** 左边文案最大长度属性 */
    private int mLeftLength;

    /** EditText hint内容 */
    private String mHintText;

    /** 显示分割线属性 */
    private boolean mShowDivide;

    /** EditText可编辑 */
    private boolean mEditEnable;

    /** 上下文 */
    private Context mContext;

    /** 默认输入内容 */
    private String mDefaultText;

    /** 输入类型 */
    private int mInputType;

    /** 显示箭头 */
    private boolean mShowArrow;
    private int mInputMaxLength;

    public EditableRelativeLayout(Context context) {
        this(context, null);
    }

    public EditableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditableRelativeLayout, defStyleAttr, 0);

        mLeftText = a.getString(R.styleable.EditableRelativeLayout_editable_left_text);
        mLeftLength = a.getInteger(R.styleable.EditableRelativeLayout_editable_left_length, 0);
        mHintText = a.getString(R.styleable.EditableRelativeLayout_editable_hint_text);
        mDefaultText = a.getString(R.styleable.EditableRelativeLayout_editable_default_text);
        mInputType = a.getInteger(R.styleable.EditableRelativeLayout_android_inputType, InputType.TYPE_CLASS_TEXT);
        mInputMaxLength = a.getInteger(R.styleable.EditableRelativeLayout_android_maxLength, 50);

        mEditEnable = a.getBoolean(R.styleable.EditableRelativeLayout_editable_edit_enable, true);
        mShowDivide = a.getBoolean(R.styleable.EditableRelativeLayout_editable_show_divide, true);
        mShowArrow = a.getBoolean(R.styleable.EditableRelativeLayout_editable_show_arrow, true);
        a.recycle();

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_editable, this, true);

        mTvLeftText = (TextView) view.findViewById(R.id.tv_left_text);
        mEtText = (EditText) view.findViewById(R.id.et_text);
        mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        mDivideLine = view.findViewById(R.id.view_divide);
        mRlEdit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        mRlTv = (RelativeLayout) view.findViewById(R.id.rl_tv);
        mTvText = (TextView) view.findViewById(R.id.tv_text);

        setLeftText(mLeftText, mLeftLength);

        if(mEditEnable){
            mRlTv.setVisibility(View.GONE);
            mRlEdit.setVisibility(View.VISIBLE);
            mEtText.setText(mDefaultText);
            mEtText.setInputType(mInputType);
            mEtText.setHint(mHintText);
            mEtText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mInputMaxLength)});
        }else {
            mRlEdit.setVisibility(View.GONE);
            mRlTv.setVisibility(View.VISIBLE);
            mTvText.setText(mDefaultText);
            mTvText.setHint(mHintText);
            mTvText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mInputMaxLength)});
        }

        showDivide(mShowDivide);
        showArrow(mShowArrow);

    }

    /**
     * 设置左边文案
     *
     * @param text 文案
     * @param maxLength 左边最大字数
     */
    public void setLeftText(CharSequence text, int maxLength){
        if(text == null){
            text = "";
        }
        StringBuilder sb = new StringBuilder(text);
        if(maxLength != 0 && maxLength > text.length()){
            int extraLength = maxLength -  text.length();
            for(int i = 0; i < extraLength; i++){
                sb.append(mContext.getResources().getString(R.string.enter_blank_space));
            }
        }
        mTvLeftText.setText(sb.toString());
    }

    /**
     * 设置文本框提示信息
     *
     * @param text 文案
     */
    public void setHintText(CharSequence text){
        if(mEditEnable){
            mEtText.setHint(text);
        }else {
            mTvText.setHint(text);
        }
    }

    /**
     * 设置文本框内容
     *
     * @param text 文案
     */
    public void setEditTextContent(CharSequence text){
        if(mEditEnable){
            mEtText.setText(text);
            mEtText.setSelection(mEtText.getText().toString().length());
        } else {
            mTvText.setText(text);
        }
    }

    /**
     * 获取文本框内容
     *
     * @return 文本内容
     */
    public String getEditTextContent(){
        if(mEditEnable){
            return TextUtils.isEmpty(mEtText.getText()) ? "" : mEtText.getText().toString();
        }else {
            return TextUtils.isEmpty(mTvText.getText()) ? "" : mTvText.getText().toString();
        }
    }

    /**
     * 获取EditText
     *
     * @return EditText
     */
    public EditText getEditText(){
        return mEtText;
    }

    /**
     * 获取TextView
     *
     * @return TextView
     */
    public TextView getTextView(){
        return mTvText;
    }

    /**
     * 显示分割线
     *
     * @param showDivide true: 显示  false: 隐藏
     */
    public void showDivide(boolean showDivide){
        mDivideLine.setVisibility(showDivide ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示箭头
     *
     * @param showArrow true: 显示  false: 隐藏
     */
    public void showArrow(boolean showArrow){
        mIvArrow.setVisibility(showArrow ? View.VISIBLE : View.INVISIBLE);
    }
}
