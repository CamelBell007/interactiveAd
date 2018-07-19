package com.interactive.suspend.ad.constant;

import com.interactive.suspend.ad.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class CollectorError
{
  private static final Map errorMap = new TreeMap();
  
  static
  {
    addError(CollectorError.TYPE.ERROR_INIT, "Did not invoke init");
  }
  
  public static void addError(CollectorError.TYPE paramTYPE, JSONObject paramJSONObject)
  {
    if (!errorMap.containsKey(paramTYPE.code()))
    {
      if (paramJSONObject != null)
      {
        errorMap.put(paramTYPE.code(), paramJSONObject.toString());
        return;
      }
      errorMap.put(paramTYPE.code(), "{}");
    }
  }
  
  public static void addError(CollectorError.TYPE paramTYPE, String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("reason", paramString);
    }
    catch (JSONException localJSONException)
    {
      LogUtil.err(
        localJSONException.toString());
    }
    addError(paramTYPE, localJSONObject);
  }
  
  public static void clearError()
  {
    errorMap.clear();
  }
  
  public static void remove(CollectorError.TYPE paramTYPE)
  {
    if (errorMap != null) {
      errorMap.remove(paramTYPE.code());
    }
  }
  
  public static String getErrorCode()
  {
    return errorMap.keySet().toString();
  }
  
  public static String getErrorMsg()
  {
    return errorMap.values().toString();
  }
  
  public static JSONObject catchErr(Throwable paramThrowable)
  {
    try
    {
      JSONObject localJSONObject;
      (localJSONObject = new JSONObject()).put("reason", paramThrowable.getLocalizedMessage());
      localJSONObject.put("type", paramThrowable.getClass().getName());
      StackTraceElement[] paramThrowables = paramThrowable.getStackTrace();
      for (int i = 0; i < paramThrowables.length; i++) {
        if (paramThrowables[i].getClassName().contains("cn.tongdun.android"))
        {
          localJSONObject.put("stack", paramThrowables[i].toString());
          break;
        }
      }
      return localJSONObject;
    }
    catch (Throwable localThrowable) {}
    return new JSONObject();
  }


  public enum TYPE
  {
    ERROR_INIT("000"),  ERROR_SO_LOAD("001"),  ERROR_SO_DEDEX("002"),  ERROR_SO_COMPR("003"),  ERROR_DEX_NULL("004"),  ERROR_DEX_FAIL("005"),  ERROR_DEX_DECODE("006"),  ERROR_DEX_LOAD("007"),  ERROR_INSTANCE_FAIL("008"),  ERROR_OPTION_PARTNER("009"),  ERROR_OPTION_PROCESS("010"),  ERROR_PROFILE_FAILED("011"),  ERROR_PROFILE_DELAY("012"),  ERROR_SHORT_INTERVAL("013"),  ERROR_ALWAYS_DEMOTION("014"),  ERROR_SIZE_LIMIT("101");

    private String code;

    private TYPE(String paramString1)
    {
      this.code = paramString1;
    }

    public final String code()
    {
      return this.code;
    }
  }
}
