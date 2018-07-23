//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.interactive.suspend.ad.http;

import android.text.TextUtils;

import com.interactive.suspend.ad.taskObject.ThreadStackLog;

import org.json.JSONException;
import org.json.JSONObject;

public class TmResponse extends BaseResponse {
    private String request_id;
    private String adslot_id;
    private String ad_title;
    private String activity_title;
    private String activity_id;
    private String ad_icon;
    private String ad_close;
    private String description;
    private String img_url;
    private boolean ad_icon_visible;
    private boolean ad_close_visible;
    private int img_width;
    private int img_height;
    private String ad_content;
    private int ad_type;
    private long expire;
    private String click_url;
    private String data1;
    private String data2;

    public TmResponse(String var1) {
        super(var1);
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String var1) {
        this.request_id = var1;
    }

    public int getError_code() {
        return this.error_code;
    }

    public void setError_code(int var1) {
        this.error_code = var1;
    }

    public String getAdslot_id() {
        return this.adslot_id;
    }

    public void setAdslot_id(String var1) {
        this.adslot_id = var1;
    }

    public String getAd_title() {
        return this.ad_title;
    }

    public void setAd_title(String var1) {
        this.ad_title = var1;
    }

    public String getActivity_title() {
        return this.activity_title;
    }

    public void setActivity_title(String var1) {
        this.activity_title = var1;
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    public void setActivity_id(String var1) {
        this.activity_id = var1;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String var1) {
        this.description = var1;
    }

    public String getImg_url() {
        return this.img_url;
    }

    public void setImg_url(String var1) {
        this.img_url = var1;
    }

    public int getImg_width() {
        return this.img_width;
    }

    public void setImg_width(int var1) {
        this.img_width = var1;
    }

    public int getImg_height() {
        return this.img_height;
    }

    public void setImg_height(int var1) {
        this.img_height = var1;
    }

    public String getAd_content() {
        return this.ad_content;
    }

    public void setAd_content(String var1) {
        this.ad_content = var1;
    }

    public long getExpire() {
        return this.expire;
    }

    public void setExpire(long var1) {
        this.expire = var1;
    }

    public String getClick_url() {
        return this.click_url;
    }

    public void setClick_url(String var1) {
        this.click_url = var1;
    }

    public String getAd_icon() {
        return this.ad_icon;
    }

    public void setAd_icon(String var1) {
        this.ad_icon = var1;
    }

    public String getAd_close() {
        return this.ad_close;
    }

    public void setAd_close(String var1) {
        this.ad_close = var1;
    }

    public int getAd_type() {
        return this.ad_type;
    }

    public void setAd_type(int var1) {
        this.ad_type = var1;
    }

    public boolean isAd_icon_visible() {
        return this.ad_icon_visible;
    }

    public void setAd_icon_visible(boolean var1) {
        this.ad_icon_visible = var1;
    }

    public boolean isAd_close_visible() {
        return this.ad_close_visible;
    }

    public void setAd_close_visible(boolean var1) {
        this.ad_close_visible = var1;
    }

    public String getData1() {
        return this.data1;
    }

    public void setData1(String var1) {
        this.data1 = var1;
    }

    public String getData2() {
        return this.data2;
    }

    public void setData2(String var1) {
        this.data2 = var1;
    }

    public static class a implements loadFinish {
        public a() {
        }

        public TmResponse a(String var1) throws InterException {
            if(var1 != null && !var1.trim().isEmpty()) {
                TmResponse var2 = new TmResponse(var1);

                try {
                    JSONObject var3 = new JSONObject(var1);
                    String var4 = var3.has("request_id")?var3.getString("request_id"):null;
                    int var5 = var3.has("error_code")?var3.getInt("error_code"):-1;
                    var2.error_code = var5;
                    String var6 = var3.has("message")?var3.getString("message"):"";
                    String var7 = var3.has("adslot_id")?var3.getString("adslot_id"):"";
                    String var8 = var3.has("ad_title")?var3.getString("ad_title"):"";
                    String var9 = var3.has("activity_title")?var3.getString("activity_title"):"";
                    String var10 = var3.has("activity_id")?var3.getString("activity_id"):"";
                    String var11 = var3.has("description")?var3.getString("description"):"";
                    String var12 = var3.has("img_url")?var3.getString("img_url"):"";
                    int var13 = var3.has("img_width")?var3.getInt("img_width"):0;
                    int var14 = var3.has("img_height")?var3.getInt("img_height"):0;
                    String var15 = var3.has("ad_content")?var3.getString("ad_content"):"";
                    long var16 = var3.has("expire")?var3.getLong("expire"):0L;
                    String var18 = var3.has("click_url")?var3.getString("click_url"):"";
                    String var19 = var3.has("tm_ad_icon")?var3.getString("tm_ad_icon"):"";
                    String var20 = var3.has("ad_close")?var3.getString("ad_close"):"";
                    int var21 = var3.has("ad_type")?var3.getInt("ad_type"):0;
                    boolean var22 = var3.has("ad_icon_visible")?var3.getBoolean("ad_icon_visible"):false;
                    boolean var23 = var3.has("ad_close_visible")?var3.getBoolean("ad_close_visible"):false;
                    String var24 = var3.has("data1")?var3.getString("data1"):"";
                    String var25 = var3.has("data2")?var3.getString("data2"):"";
                    var2.request_id = var4;
                    var2.message = var6;
                    var2.adslot_id = var7;
                    var2.ad_title = var8;
                    var2.activity_title = var9;
                    var2.activity_id = var10;
                    var2.description = var11;
                    var2.img_url = var12;
                    var2.img_width = var13;
                    var2.img_height = var14;
                    if(!TextUtils.isEmpty(var15) && !"null".equals(var15)) {
                        var2.ad_content = var15;
                    } else {
                        var2.ad_content = "";
                    }

                    var2.expire = var16;
                    var2.click_url = var18;
                    var2.ad_type = var21;
                    var2.ad_icon = var19;
                    var2.ad_close = var20;
                    var2.ad_close_visible = var23;
                    var2.ad_icon_visible = var22;
                    var2.data1 = var24;
                    var2.data2 = var25;
                    return var2;
                } catch (JSONException var26) {
                    ThreadStackLog.a().a("failed to parse JSON，  error_code:  " + var2.error_code);
                        throw new InterException(var1, var26);
                }
            } else {
                throw new InterException("Server returned an empty response.");
            }
        }

        @Override
        public BaseResponse getResponse(String var1) {
            return null;
        }
    }
}
