package com.interactive.suspend.ad.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InterImageLoad extends ImageLoadChange
{
  protected Bitmap a(String paramString)
  {
    return c(paramString);
  }
  
  protected Bitmap b(String paramString)
  {
    if ((!isEmpty(paramString)) && (!isEmpty("")))
    {
      File localFile1 = new File("");
      if ((localFile1.exists()) && (localFile1.isDirectory()))
      {
        File localFile2 = new File(paramString);
        if ((!isEmpty(paramString)) && (localFile2 != null) && (localFile2.exists())) {
          return BitmapFactory.decodeFile(paramString);
        }
      }
      else
      {
        localFile1.mkdirs();
      }
    }
    return null;
  }
  
  protected void a(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    if ((paramBitmap != null) && (!isEmpty(paramString1)) && (!isEmpty("")))
    {
      File localFile1 = new File("");
      if ((!localFile1.exists()) || (!localFile1.isDirectory())) {
        localFile1.mkdirs();
      }
      if ((paramString1 != null) && (paramString1.length() > 0))
      {
        File localFile2 = new File(paramString1);
        
        localFile2.delete();
        
        FileOutputStream localFileOutputStream = null;
        try
        {
          localFileOutputStream = new FileOutputStream(localFile2);
          if (paramString1.contains(".png")) {
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
          } else {
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localFileOutputStream);
          }
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          localFileNotFoundException.printStackTrace();
        }
        finally
        {
          if (localFileOutputStream != null) {
            try
            {
              localFileOutputStream.close();
            }
            catch (IOException localIOException3) {}
          }
        }
      }
    }
  }
  
  public Bitmap c(String paramString)
  {
    Bitmap localBitmap = b(g(paramString));
    if (localBitmap != null) {
      return localBitmap;
    }
    InputStream localInputStream = null;
    try
    {
      URL localURL = new URL(paramString);
      localInputStream = (InputStream)localURL.getContent();
      localBitmap = BitmapFactory.decodeStream(localInputStream);
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (paramString.contains(".png")) {
      a(localBitmap, g(paramString), ".png");
    } else {
      a(localBitmap, g(paramString), ".jpg");
    }
    return localBitmap;
  }
  
  protected String d(String paramString)
  {
    String str1 = g(paramString);
    
    HttpURLConnection localHttpURLConnection = null;
    BufferedOutputStream localBufferedOutputStream = null;
    try
    {
      File localFile = new File("");
      if ((!localFile.exists()) || (!localFile.isDirectory())) {
        localFile.mkdirs();
      }
      URL localURL = new URL(paramString);
      localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(localHttpURLConnection.getInputStream(), 8192);
      localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str1), 8192);
      int i;
      while ((i = localBufferedInputStream.read()) != -1) {
        localBufferedOutputStream.write(i);
      }
      return str1;
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
    }
    finally
    {
      if (localHttpURLConnection != null) {
        localHttpURLConnection.disconnect();
      }
      if (localBufferedOutputStream != null) {
        try
        {
          localBufferedOutputStream.close();
        }
        catch (IOException localIOException4)
        {
          localIOException4.printStackTrace();
        }
      }
    }
    return null;
  }
}
