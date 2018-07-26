package com.interactive.suspend.ad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.interactive.suspend.ad.InteractiveAd;
import com.interactive.suspend.ad.R;
import com.interactive.suspend.ad.activity.TmActivity;
import com.interactive.suspend.ad.controller.SuspendController;
import com.interactive.suspend.ad.controller.SuspendListener;
import com.interactive.suspend.ad.html.AdvancedNativeAd;
import com.interactive.suspend.ad.html.INativeAd;
import com.interactive.suspend.ad.html.INativeAdLoadListener;
import com.interactive.suspend.ad.http.FloatAdParams;
import com.interactive.suspend.ad.http.TmResponse;
import com.interactive.suspend.ad.imageloader.LoadImageCallBack;
import com.interactive.suspend.ad.manager.StringUtils;
import com.interactive.suspend.ad.task.AdServiceUrlRequestAction;
import com.interactive.suspend.ad.task.FloatAdRequestAction;

import java.util.List;

import static com.interactive.suspend.ad.util.LogUtil.TAG;

/**
 * Created by VC on 2018/6/28.
 */

public class FloatView extends RelativeLayout implements View.OnClickListener, SuspendController {
    private WebImageView mWebImageView;
    private GifView mGifView;
    private ImageView mCloseImage;
    private ImageView mAdImage;
    private FloatAdRequestAction mFloatAdManager;
    private AdServiceUrlRequestAction mAdServiceUrlRequestAction;
    private FloatAdParams mAdInterInfo;
    private TmResponse mAdInfo;
    private INativeAd mFloatAd;
    private int mAdViewShapeStyle;
    private Context mContext;
    private SuspendListener mSuspendListener;

    private AdvancedNativeAd mAdvancedNativeAd;
    private static final int LOAD_AD_NUM = 1;

    public FloatView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.mContext = context.getApplicationContext();
        this.initAttribute(context, attributeSet, defStyleAttr);
        this.initView(context);
    }

    private void initAttribute(Context context, AttributeSet attributeSet, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TMAwView, defStyleAttr, 0);
        this.mAdViewShapeStyle = typedArray.getInt(R.styleable.TMAwView_shape, 0);
        typedArray.recycle();
    }

    private void initView(Context context) {
        if (this.getChildCount() == 0) {
            this.mWebImageView = new WebImageView(context);
            this.mCloseImage = new ImageView(context);
            this.mAdImage = new ImageView(context);
            this.mGifView = new GifView(context);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            LayoutParams webLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(this.mWebImageView, webLayoutParams);

            LayoutParams gifLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(this.mGifView, gifLayoutParams);
            this.mGifView.setVisibility(View.GONE);

            LayoutParams closeLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            closeLayoutParams.addRule(10);
            closeLayoutParams.addRule(11, -1);
            this.addView(this.mCloseImage, closeLayoutParams);
            this.mCloseImage.setImageResource(R.drawable.tm_new_close);

            LayoutParams adLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            adLayoutParams.addRule(12);
            adLayoutParams.addRule(11, -1);
            this.addView(this.mAdImage, adLayoutParams);
            this.mAdImage.setImageResource(R.drawable.tm_ad_icon);

            this.mWebImageView.setOnClickListener(this);
            this.mGifView.setOnClickListener(this);
            this.mCloseImage.setOnClickListener(new OnClickListener() {
                public void onClick(View var1) {
                    if (mSuspendListener != null) {
                        mSuspendListener.onCloseClick();
                    }
                    setVisibility(View.GONE);
                }
            });
            this.mWebImageView.setLoadCallback(new LoadImageCallBack() {
                public void loadImageSuccessed() {
                    setVisibility(View.VISIBLE);
                    if (mWebImageView != null) {
                        setVisibility(View.VISIBLE);
                    }

                    if (mSuspendListener != null) {
                        mSuspendListener.onReceiveAd();
                        mSuspendListener.onAdExposure();
                    }

                }

                public void loadImageFailed() {
                    if (mSuspendListener != null) {
                        mSuspendListener.onLoadFailed();
                    }

                }
            });
            this.mGifView.setLoadCallback(new LoadImageCallBack() {
                public void loadImageSuccessed() {
                    if (mGifView != null) {
                        setVisibility(View.VISIBLE);
                    }

                    if (mSuspendListener != null) {
                        mSuspendListener.onReceiveAd();
                        mSuspendListener.onAdExposure();
                    }

                }

                public void loadImageFailed() {
                    if (mSuspendListener != null) {
                        mSuspendListener.onLoadFailed();
                    }

                }
            });
        }

        this.setVisibility(View.GONE);
    }

    public void loadNativeAd(String slotId) {
        mAdvancedNativeAd = new AdvancedNativeAd(getContext(), slotId); // use unitid with large image if you want image data
        mAdvancedNativeAd.setAdListener(new INativeAdLoadListener() {
            @Override
            public void onAdListLoaded(List<INativeAd> ads) {
                Log.e(TAG, "onAdListLoaded: size: " + ads.size()+"  onAdListLoaded: Category: "
                        + ads.get(0).getAppCategory() + " |||||  size:" + ads.get(0).getAppSize());
                if(ads != null&&ads.size()>0){
                    mFloatAd = ads.get(0);
                }
                if (mFloatAd == null) {
                    return;
                }

                String iconUrl = mFloatAd.getIconImageUrl();
                if (!TextUtils.isEmpty(iconUrl)) {
                    if (iconUrl.endsWith(".gif")) {
                        mWebImageView.setVisibility(View.GONE);
                        mGifView.setVisibility(View.VISIBLE);
                        mGifView.setGifUrl(StringUtils.a(iconUrl));
                    } else {
                        mWebImageView.loadSource(StringUtils.a(iconUrl), R.drawable.default_image_background);
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "onError: " + error);
                if (mSuspendListener != null) {
                        mSuspendListener.onFailedToReceiveAd();
                    }
//
            }

            @Override
            public void onAdClicked(INativeAd ad) {
                Log.e(TAG, "onAdlicked: type: " + ad.getAdType() + ", title: " + ad.getTitle());
            }

            @Override
            public void onShowed() { // IMPORTANT: ONLY record show event from APX and Facebook, not include Admob

            }
        });

        mAdvancedNativeAd.load(LOAD_AD_NUM);
    }

    public void loadAd(String slotId) {
        loadNativeAd(slotId);
    }

//    public void loadAd(String slotId) {
//        loadNativeAd(slotId);
//
//        if (this.mAdInterInfo == null) {
////            if(StringUtils.isEmpty(slotId)){
////                return;
////            }
////            String slotKey = getSlotKeyByName(slotId);
//            this.mAdInterInfo = (new FloatAdParams.a(this.mContext)).a(slotId).a();
//        }
//
//        if (!TextUtils.isEmpty(this.mAdInterInfo.b()) && !TextUtils.isEmpty(this.mAdInterInfo.a())) {
//            this.mFloatAdManager = new FloatAdRequestAction(new TmResponse.a(), new RequestInterface() {
//                public void requestSuccess(BaseResponse baseResponse) {
//                    if (baseResponse instanceof TmResponse) {
//                        mAdInfo = (TmResponse) baseResponse;
//                        String var2 = mAdInfo.getImg_url();
//                        if (!TextUtils.isEmpty(var2)) {
//                            if (var2.endsWith(".gif")) {
//                                mWebImageView.setVisibility(View.GONE);
//                                mGifView.setVisibility(View.VISIBLE);
//                                mGifView.setGifUrl(StringUtils.a(var2));
//                            } else {
//                                mWebImageView.loadSource(StringUtils.a(var2), R.drawable.default_image_background);
//                            }
//                        }
//
//                        if (mAdInfo.isAd_close_visible()) {
//                            mCloseImage.setVisibility(View.VISIBLE);
//                        } else {
//                            mCloseImage.setVisibility(View.GONE);
//                        }
//
//                        if (mAdInfo.isAd_icon_visible()) {
//                            mAdImage.setVisibility(View.VISIBLE);
//                        } else {
//                            mAdImage.setVisibility(View.GONE);
//                        }
//                    }
//
//                }
//
//                public void requestFail(String var1) {
//                    ThreadStackLog.a().a(var1);
//                    if (mSuspendListener != null) {
//                        mSuspendListener.onFailedToReceiveAd();
//                    }
//
//                }
//
//                public void preExecute() {
//                    setVisibility(View.GONE);
//                }
//            }, this.mContext);
//            this.mFloatAdManager.requestAdInfo(this.mAdInterInfo);
//        } else {
//            throw new IllegalStateException("app_key or adslot_id not set");
//        }
//    }


    private String getSlotKeyByName(String slotName) {
        String slotKey = InteractiveAd.getInstance().getSourceIdBySlotId(slotName);
        return slotKey;
    }

    public void destroy() {
        if (this.mWebImageView != null) {
            this.mWebImageView.cancelTask(true);
            this.mWebImageView = null;
        }

        if (this.mGifView != null) {
            this.mGifView = null;
        }

        this.removeAllViews();
        if (this.mFloatAdManager != null) {
            this.mFloatAdManager.releseResource();
            this.mFloatAdManager = null;
        }

        if (this.mAdServiceUrlRequestAction != null) {
            this.mAdServiceUrlRequestAction.a();
            this.mAdServiceUrlRequestAction = null;
        }

        this.mAdInterInfo = null;
        this.mAdInfo = null;
    }

    @Override
    public void setAdListener(SuspendListener var1) {
        this.mSuspendListener = var1;
    }


    public void onClick(View var1) {
        if (this.mAdInfo != null && this.getVisibility() == View.VISIBLE) {
            if (this.mSuspendListener != null) {
                this.mSuspendListener.onAdClick();
            }
            TmActivity.jumptoShowActivity(this.getContext(), StringUtils.a(this.mFloatAd.getClickUrl()));
        }
    }
}

