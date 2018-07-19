package com.interactive.suspend.ad.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BoxUtil
{
  private static final String PLACEHOLDER = "XX";
  
  public static String limitBox(JSONObject paramJSONObject, int paramInt) throws JSONException {
    if ((paramJSONObject.toString().length() << 2) / 3 < paramInt) {
      return paramJSONObject.toString();
    }
    JSONObject localJSONObject1 = paramJSONObject.getJSONObject("error_init");
    JSONArray localJSONArray1 = new JSONArray(localJSONObject1.getString("error_msg"));
    
    JSONArray localJSONArray2 = new JSONArray();
    for (int i = 0; i < localJSONArray1.length(); i++)
    {
      JSONObject localJSONObject2;
      (localJSONObject2 = localJSONArray1.getJSONObject(i)).put("reason", "XX");
      localJSONArray2.put(localJSONObject2);
    }
    localJSONObject1.put("error_msg", localJSONArray2);
    paramJSONObject.put("error_init", localJSONObject1);
    if ((paramJSONObject.toString().length() << 2) / 3 > paramInt)
    {
      localJSONObject1.put("error_msg", "XX");
      localJSONObject1.put("device", "XX");
      paramJSONObject.put("error_init", localJSONObject1);
    }
    return paramJSONObject.toString();
  }
}
