package com.uplan.miyao.ui.financial.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.uplan.miyao.R;
import com.uplan.miyao.ui.financial.view.activity.TopBannerHomeWebActivity_1;
import com.uplan.miyao.ui.financial.view.activity.TopBannerHomeWebActivity_2;
import com.uplan.miyao.ui.financial.view.activity.TopBannerHomeWebActivity_3;
import com.uplan.miyao.ui.vip.view.activity.TopBannerDiscoverWebActivity_1;
import com.uplan.miyao.ui.vip.view.activity.TopBannerDiscoverWebActivity_2;
import com.uplan.miyao.ui.vip.view.activity.TopBannerDiscoverWebActivity_3;
import com.uplan.miyao.util.PreferencesUtils;

/**
 * Created by luomin on 16/7/12.
 */
public class LocalImgAdapter implements LBaseAdapter<Bitmap> {
    private Context mContext;
    public static final String TYPE_HOME="type_home";
    public static final String TYPE_SPLASH="type_splash";
    public static final String TYPE_DISCOVER="type_discover";

    private String type;

    public LocalImgAdapter(Context context,String type) {
        mContext=context;
        this.type=type;
    }

    @Override
    public View getView(final LMBanners lBanners, final Context context, int position, Bitmap data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        imageView.setImageBitmap(data);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TYPE_HOME.equals(type)){
                    switch (position){
                        case 0:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_1))){
                                TopBannerHomeWebActivity_1.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_1));
                            }
                            break;
                        case 1:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_2))){
                                TopBannerHomeWebActivity_2.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_2));
                            }
                            break;
                        case 2:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_3))){
                                TopBannerHomeWebActivity_3.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_HOME_3));
                            }
                            break;
                    }
                }else if(TYPE_DISCOVER.equals(type)){
                    switch (position){
                        case 0:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_1))){
                                TopBannerDiscoverWebActivity_1.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_1));
                            }
                            break;
                        case 1:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_2))){
                                TopBannerDiscoverWebActivity_2.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_2));
                            }
                            break;
                        case 2:
                            if(!TextUtils.isEmpty(PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_3))){
                                TopBannerDiscoverWebActivity_3.start(mContext,PreferencesUtils.getString(mContext,PreferencesUtils.URL_BANNER_DISCOVER_3));
                            }
                            break;
                    }
                }
            }
        });
        return view;
    }


}
