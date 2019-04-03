package com.uplan.miyao.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.list.AbsAdapterItem;
import com.uplan.miyao.base.list.BaseListActivity;
import com.uplan.miyao.base.mvp.IPresenter;

import java.util.ArrayList;

/**
 * Author: Created by zs on 2018/4/17.
 *
 * Description: 通用选择类型Activity
 */

public class SelectActivity extends BaseListActivity {

    /** 页面参数：选择类型 */
    public static final String EXTRA_TYPE = "select_type";

    /** 页面参数：已选择文案 */
    public static final String EXTRA_SELECT_TEXT = "select_text";

    /** 页面参数：必填项 */
    public static final String EXTRA_REQUIRED = "required_field";

    /** 请求code值 */
    public static final int SELECT_REQUEST_CODE = 0x1;

    /** 选择类型：车颜色 */
    public static final String TYPE_CAR_COLOR = "type_car_color";

    /** 选择类型：车辆类型 */
    public static final String TYPE_CAR = "type_car";  

    /** 选择类型：贷款类型 */
    public static final String TYPE_LOAN = "type_loan";

    /** 选择类型：婚姻状况 */
    public static final String TYPE_MARRY = "type_marry";

    /** 选择类型：贷款期限 */
    public static final String TYPE_LOAN_DEADLINE = "loan_deadline";

    /** 选择类型：学历 */
    public static final String TYPE_EDUCATION = "type_education";

    /** 选择类型：与借款人关系 */
    public static final String TYPE_BORROWER_RELATION = "type_borrower_relation";

    /** 当前类型 */
    private String mCurrentType;

    /** 选择文案 */
    private String mSelectText;

    /** 必填项 */
    private boolean mRequired;

    /** 类型填充数据 */
    private ArrayList<String> mTypeList = new ArrayList<>();

    /**
     * 启动Activity
     *
     * @param context 上下文
     * @param type 类型选择
     * @param selectText 已选择的文案
     */
    public static void startActivityForResult(Activity context, String type, String selectText) {
        startActivityForResult(context, type, selectText, true);
    }

    /**
     * 启动Activity
     *
     * @param context 上下文
     * @param type 类型选择
     * @param selectText 已选择的文案
     * @param required true: 必填项 false：选填项, 默认true
     */
    public static void startActivityForResult(Activity context, String type, String selectText, boolean required) {
        startActivityForResult(context, type, selectText, required, SELECT_REQUEST_CODE);
    }

    /**
     * 启动Activity
     *
     * @param context 上下文
     * @param type 类型选择
     * @param selectText 已选择的文案
     * @param requestCode 请求code
     */
    public static void startActivityForResult(Activity context, String type, String selectText, int requestCode) {
        startActivityForResult(context, type, selectText, true, requestCode);
    }

    /**
     * 启动Activity
     *
     * @param context 上下文
     * @param type 类型选择
     * @param selectText 已选择的文案
     * @param required true: 必填项  false：选填 默认true
     * @param requestCode 请求code
     */
    public static void startActivityForResult(Activity context, String type, String selectText, boolean required, int requestCode) {
        Intent starter = new Intent(context, SelectActivity.class);
        starter.putExtra(EXTRA_TYPE, type);
        starter.putExtra(EXTRA_SELECT_TEXT, selectText);
        starter.putExtra(EXTRA_REQUIRED, required);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setRefreshMode(MODE_NONE);
        initData();
        setData(mTypeList);
    }

    @Override
    protected void onRefresh() {}

    @Override
    protected void onLoadMore() {}

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected AbsAdapterItem getAbsAdapterItem() {
        return new SelectItem(mSelectText);
    }

    @Override
    protected String getTitleText() {
        getExtra();

        String title = "";
        switch (mCurrentType) {
        case TYPE_CAR_COLOR:
            title = "车辆颜色";
            break;
        case TYPE_CAR:
            title = "车辆类型";
            break;
        case TYPE_LOAN:
            title = "贷款类型";
            break;
        case TYPE_MARRY:
            title = "婚姻状况";
            break;
        case TYPE_LOAN_DEADLINE:
            title = "贷款期限";
            break;
        case TYPE_EDUCATION:
            title = "学历";
            break;
        case TYPE_BORROWER_RELATION:
            title = "与借款人关系";
            break;
        default:
            break;
        }

        String rightText = mRequired ? "" : "不选择";
        setRightText(rightText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectText = "";
                goBack();
            }
        });

        return title;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int i, long l) {
        mSelectText = (String) getData().get(i);
        goBack();
    }

    @Override
    protected void goBack() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECT_TEXT, mSelectText);
        intent.putExtra(EXTRA_TYPE, mCurrentType);
        setResult(Activity.RESULT_OK, intent);
        super.goBack();
    }

    /**
     * 获取页面参数
     */
    private void getExtra() {
        mCurrentType = getIntent().getStringExtra(EXTRA_TYPE);
        mSelectText = getIntent().getStringExtra(EXTRA_SELECT_TEXT);
        mRequired = getIntent().getBooleanExtra(EXTRA_REQUIRED, true);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        switch (mCurrentType) {
        case TYPE_CAR_COLOR:
            mTypeList.add("米色");
            mTypeList.add("白色");
            mTypeList.add("灰色");
            mTypeList.add("红色");
            mTypeList.add("棕色");
            mTypeList.add("蓝色");
            mTypeList.add("绿色");
            mTypeList.add("黄色");
            mTypeList.add("紫色");
            mTypeList.add("黑色");
            mTypeList.add("橙色");
            mTypeList.add("银色");
            mTypeList.add("金色");
            break;
        case TYPE_CAR:
            mTypeList.add("乘用车-新车");
            mTypeList.add("乘用车-二手车");
            mTypeList.add("商用车-黄牌");
            mTypeList.add("商用车-蓝牌");
            mTypeList.add("商用车-新能源");
            break;
        case TYPE_LOAN:
            mTypeList.add("自有资金");
            mTypeList.add("银行放款");
            break;
        case TYPE_MARRY:
            mTypeList.add("已婚");
            mTypeList.add("未婚");
            mTypeList.add("离异");
            mTypeList.add("丧偶");
            break;
        case TYPE_LOAN_DEADLINE:
            mTypeList.add("24个月");
            mTypeList.add("36个月");
            break;
        case TYPE_EDUCATION:
            mTypeList.add("中专及以下");
            mTypeList.add("高中");
            mTypeList.add("大专");
            mTypeList.add("本科");
            mTypeList.add("硕士");
            mTypeList.add("博士");
            break;
        case TYPE_BORROWER_RELATION:
            mTypeList.add("夫妻");
            mTypeList.add("朋友");
            mTypeList.add("亲属");
            mTypeList.add("父子");
            mTypeList.add("母子");
            mTypeList.add("父女");
            mTypeList.add("母女");
            mTypeList.add("同学");
            mTypeList.add("同事");
            mTypeList.add("其他");
            break;
        default:
            break;
        }
    }

    static class SelectItem extends AbsAdapterItem<String>{

        private TextView mTvType;
        private ImageView mIvSelected;
        private String mSelectText;

        SelectItem(String selectText){
            mSelectText = selectText;
        }

        @Override
        public int getItemLayout() {
            return R.layout.item_select_type;
        }

        @Override
        public void init(View contentView) {
            mTvType = (TextView) contentView.findViewById(R.id.tv_type);
            mIvSelected = (ImageView) contentView.findViewById(R.id.iv_selected);
        }

        @Override
        public void bindData(String s) {
            mTvType.setText(s);

            if(mSelectText != null && mSelectText.equals(s)){
                mIvSelected.setVisibility(View.VISIBLE);
            }else {
                mIvSelected.setVisibility(View.GONE);
            }
        }
    }
}
