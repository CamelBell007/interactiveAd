package com.interactive.suspend.ad.html;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.interactive.suspend.ad.R;
import com.interactive.suspend.ad.constant.Constants;
import com.interactive.suspend.ad.util.ApplicationUtil;
import com.interactive.suspend.ad.util.LogUtil;
import com.interactive.suspend.ad.util.RecordUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hongwu on 8/2/17.
 */

public class ApxNativeAdapter extends NativeAd implements IAdvancedNativeAd{

    private final static String TAG = "ApxNativeAdapter: ";
    private Context mContext;
    private INativeAdLoadListener mListener;
    private AdInfo mRawAd;
    private SubscribeAdInfo mSubscribeAdInfo;
    private long mJumpStartTime = 0;
    private long mJumpEndTime = 0;
    private boolean mIsPbshowing = false;
    private boolean mJumpShareGP = false;
    private AdInfo mCurJumpAd;
    private View mAdContent;
    private FetchAppConfigResult.NativeUnit mNativeUnit;
    private List<AdInfo> mShowedAds = new ArrayList<>();
    private List<SubscribeAdInfo> mShowedSubscribeAds = new ArrayList<>();
    private int mAdNum = 1; //default value
    private boolean mIsEnableTransitionView = true;
    private boolean mIsLoadSubscsribeAd = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private long mLastReportImpTime = 0;
    private String mLastReportCampaignid = "";

    public ApxNativeAdapter(Context context, String key, FetchAppConfigResult.NativeUnit nativeUnit) {
        mContext = context;
        mKey = key;
        mNativeUnit = nativeUnit;
    }

    @Override
    public void setAdListener(INativeAdLoadListener listener) {
        mListener = listener;
    }

    @Override
    public void load(final int adNum) {
        if (adNum < 1 || adNum > Constants.MAX_ADVANCED_NATIVE_ADS) {
            throw new IllegalArgumentException("Wrong ad num: " + adNum + ", should be in [1, " + Constants.MAX_ADVANCED_NATIVE_ADS + "]");
        }
        mAdNum = adNum;
        if (ApplicationUtil.shouldShowSubscribeAd(mContext, mNativeUnit.adType, mNativeUnit.optinRate)) {
            mIsLoadSubscsribeAd = true;
            loadSubscribeAd();
        } else {
            mIsLoadSubscsribeAd = false;
            loadNativeAd();
        }
    }

    private void loadNativeAd() {
        Log.e(LogUtil.TAG,"loadNativeAd");
        NativeLoaderInterface nativeLoader = NativeLoader.newInstance(mContext, mNativeUnit.style, mNativeUnit.videoAllow);
        if (nativeLoader != null) {
            nativeLoader.load(new IApxNativeAdListener<AdInfo>() {
                @Override
                public void onError(String msg) {
                    if (mListener != null) {
                        mListener.onError(msg);
                    }
                }

                @Override
                public void onAdLoaded(List<AdInfo> list) {
                    List<AdInfo> filterAds = tryGetNotShowedAds(getFilterAds(list), mShowedAds);
                    if (filterAds == null || filterAds.size() == 0) {
                        if (mListener != null) {
                            mListener.onError("No valid data");
                        }
                    } else {
                        // init cur adapter
                        mRawAd = filterAds.get(0);
                        mLoadedTime = System.currentTimeMillis();
                        if (mListener != null) {
                            List<INativeAd> ads = new ArrayList<>();
                            for (AdInfo info : filterAds) {
                                ApxNativeAdapter adapter = new ApxNativeAdapter(mContext, mKey, mNativeUnit);
                                adapter.mRawAd = info;
                                adapter.mLoadedTime = mLoadedTime;
                                adapter.mListener = mListener;
                                adapter.mIsLoadSubscsribeAd = false;
                                ads.add(adapter);
                                mShowedAds.add(info);
                            }
                            mListener.onAdListLoaded(ads);
                        }
                    }
                }

                @Override
                public void onAdClicked(AdInfo info) {

                }

                @Override
                public void onAdShowed() {

                }

                @Override
                public void onAdClosed() {

                }
            }, true, "", true, mAdNum);
        }
    }

    private void loadSubscribeAd() {
        Log.e(LogUtil.TAG,"loadSubscribeAd");
        SubscribeLoaderInterface subscribeLoader = SubscribeLoader.newInstance(mContext);
        if (subscribeLoader != null) {
            subscribeLoader.load(new IApxNativeAdListener<SubscribeAdInfo>() {
                @Override
                public void onError(String msg) {
                    if (mListener != null) {
                        mListener.onError(msg);
                    }
                }

                @Override
                public void onAdLoaded(List<SubscribeAdInfo> list) {
                    List<SubscribeAdInfo> filterAds = tryGetNotShowedAds(list, mShowedSubscribeAds);
                    if (filterAds == null || filterAds.size() == 0) {
                        if (mListener != null) {
                            mListener.onError("No valid data");
                        }
                    } else {
                        // init cur adapter
                        mSubscribeAdInfo = filterAds.get(0);
                        mLoadedTime = System.currentTimeMillis();
                        if (mListener != null) {
                            List<INativeAd> ads = new ArrayList<>();
                            for (SubscribeAdInfo info : filterAds) {
                                ApxNativeAdapter adapter = new ApxNativeAdapter(mContext, mKey, mNativeUnit);
                                adapter.mSubscribeAdInfo = info;
                                adapter.mLoadedTime = mLoadedTime;
                                adapter.mListener = mListener;
                                adapter.mIsLoadSubscsribeAd = true;
                                ads.add(adapter);
                                mShowedSubscribeAds.add(info);
                            }
                            mListener.onAdListLoaded(ads);
                        }
                    }
                }

                @Override
                public void onAdClicked(SubscribeAdInfo info) {

                }

                @Override
                public void onAdShowed() {

                }

                @Override
                public void onAdClosed() {

                }
            }, true, true, mAdNum);
        }
    }


    private List<AdInfo> getFilterAds(List<AdInfo> ads) {
        List<AdInfo> list = new ArrayList<>();
        if (ads == null || ads.size() == 0) {
            return list;
        }
        if (mNativeUnit.style == Constants.NativeAdStyle.LARGE) {
            for (AdInfo info : ads) {
                if (TextUtils.isEmpty(info.imageUrl)) {
                    continue;
                }
                if (!mNativeUnit.videoAllow) {
                    AdInfo clone = new AdInfo(info);
                    clone.videoUrl = "";
                    list.add(clone);
                } else {
                    list.add(info);
                }
            }
        } else {
            list = ads;
        }
        return list;
    }

    private <T> List<T> tryGetNotShowedAds(List<T> adInfos, List<T> showedAds) {
        List<T> list = new ArrayList<>();
        if (adInfos == null) {
            return list;
        }
        for (T ad : adInfos) { // add not showed ads
            if (list.size() < mAdNum) {
                if (!ApplicationUtil.contains(showedAds, ad) && !ApplicationUtil.contains(list, ad)) {
                    list.add(ad);
                }
            }
        }
        if (list.size() < mAdNum) { // num is not enough, add some already showed ads
            showedAds.clear();
            for (T ad : adInfos) {
                if (list.size() < mAdNum && !ApplicationUtil.contains(list, ad)) {
                    list.add(ad);
                }
            }
        }
        return list;
    }

    @Override
    public void registerPrivacyIconView(View view) {
    }

    @Override
    public String getClickUrl() {
        String clickUrl = "";
        if (mIsLoadSubscsribeAd) {
            if (mSubscribeAdInfo != null) {
                clickUrl = mSubscribeAdInfo.clkurl;
            }
        }
        return clickUrl;
    }

    @Override
    public void registerViewForInteraction(final View view) {
        String campaignid = "";
        if (mIsLoadSubscsribeAd) {
            if (mSubscribeAdInfo != null) {
                campaignid = mSubscribeAdInfo.campaignid;
            }
        } else {
            if (mRawAd != null) {
                campaignid = mRawAd.campaignid;
            }
        }
        if (campaignid.equals(mLastReportCampaignid) && (System.currentTimeMillis() - mLastReportImpTime <= 2000)) {
            Log.e(TAG, "Report imp twice in short time");
            return;
        }

        super.registerViewForInteraction(view);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mIsLoadSubscsribeAd) {
                    if (mSubscribeAdInfo != null) {
                        mLastReportCampaignid = mSubscribeAdInfo.campaignid;
                        mLastReportImpTime = System.currentTimeMillis();
                        List<SubscribeAdInfo> list = new ArrayList<>();
                        list.add(mSubscribeAdInfo);
                        if (mListener != null) {
                            mListener.onShowed();
                        }
                        RecordUtil.uploadAdImpressionEvent(mContext, list, ApplicationUtil.isViewVisible(view, true, 50), mKey); // report impression
                    }
                } else {
                    if (mRawAd != null) {
                        mLastReportCampaignid = mRawAd.campaignid;
                        mLastReportImpTime = System.currentTimeMillis();
                        List<AdInfo> list = new ArrayList<>();
                        list.add(mRawAd);
                        if (mListener != null) {
                            mListener.onShowed();
                        }
                        RecordUtil.uploadAdImpressionEvent(mContext, list, ApplicationUtil.isViewVisible(view, true, 50), mKey); // report impression
                    }
                }
            }
        }, 1000);// ViewVisible definition: 50% area is visible for least one second.

        mAdContent = view;
        mAdContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(LogUtil.TAG, "onClick");
                if (mListener != null) {
                    mListener.onAdClicked(ApxNativeAdapter.this);
                }
                if (mIsLoadSubscsribeAd) {
                    ApplicationUtil.jumpToWebSite(mContext, mSubscribeAdInfo, mKey);
                } else {
                    if (!mIsPbshowing) {
//                        jumpToMarket(mRawAd);
                    }
                }
            }
        });
    }

    @Override
    public void registerTransitionViewForAdClick(View view) {
        if (view != null) {
        }
    }

    @Override
    public void enableTransitionViewForAdClick(boolean enable) {
        mIsEnableTransitionView = enable;
    }

    @Override
    public String getPackageName() {
        return mIsLoadSubscsribeAd ? "" : mRawAd.pkgname;
    }

    @Override
    public String getAdType() {
        return mIsLoadSubscsribeAd ? Constants.NativeAdSource.AD_SOURCE_APX_CONTENT : Constants.NativeAdSource.AD_SOURCE_APX_INSTALL;
    }

    @Override
    public String getCoverImageUrl() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo.imageUrl : mRawAd.imageUrl;
    }

    @Override
    public Object getAdObject() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo : mRawAd;
    }

    @Override
    public String getIconImageUrl() {
        return mIsLoadSubscsribeAd ? "" : mRawAd.icon;
    }

    @Override
    public String getSubtitle() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo.description : mRawAd.description;
    }

    @Override
    public double getStarRating() {
        return mIsLoadSubscsribeAd ? 0 : Double.valueOf(mRawAd.apprating);
    }

    @Override
    public String getTitle() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo.title : mRawAd.title;
    }

    @Override
    public String getCallToActionText() {
        return mIsLoadSubscsribeAd ?
                "Visit" : mContext.getResources().getString(R.string.anative_install);
    }

    @Override
    public String getId() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo.campaignid : mRawAd.campaignid;
    }

    @Override
    public String getBody() {
        return mIsLoadSubscsribeAd ? mSubscribeAdInfo.description : mRawAd.description;
    }

    @Override
    public void destroy() {
        mListener = null;
        mAdContent = null;
    }

    @Override
    public  String getAppCategory() {
        Log.e(LogUtil.TAG, "getAppCategory");
        if(mRawAd == null){
            Log.e(LogUtil.TAG, "getAppCategory mRawAd == null");
            return "";
        }
        Log.e(LogUtil.TAG,"getAppCategory:"+mRawAd.appcategory);
        return mIsLoadSubscsribeAd ? "" : mRawAd.appcategory;

    }

    @Override
    public  String getAppSize() {
        Log.e(LogUtil.TAG,"getAppSize");
        if(mRawAd == null){
            Log.e(LogUtil.TAG,"getAppSize mRawAd == null");
            return "";
        }
        Log.e(LogUtil.TAG,"getAppSize:"+mRawAd.appsize);
        return mIsLoadSubscsribeAd ? "" : mRawAd.appsize;
    }

}
