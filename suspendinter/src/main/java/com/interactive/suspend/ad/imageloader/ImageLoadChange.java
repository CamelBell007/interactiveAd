package com.interactive.suspend.ad.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class ImageLoadChange extends ImageLoadBaseSingle
{
  public Bitmap getBitmapByOption(String pathName, int needWidth, int needHight)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(pathName, localOptions);

    localOptions.inSampleSize = getOptionSimpleSize(localOptions, needWidth, needHight);

    localOptions.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(pathName, localOptions);
  }

  public int getOptionSimpleSize(BitmapFactory.Options paramOptions, int needWidth, int needHeight)
  {
    int height = paramOptions.outHeight;
    int width = paramOptions.outWidth;
    int simple = 1;
    if ((height > needHeight) || (width > needWidth))
    {
      if (width > height) {
        simple = Math.round(height / needHeight);
      } else {
        simple = Math.round(width / needWidth);
      }
      float dpSize = width * height;

      float defaultSize = needWidth * needHeight * 2;
      while (dpSize / (simple * simple) > defaultSize) {
        simple++;
      }
    }
    return simple;
  }

  protected Bitmap getBitmapByNewSize(String imagePath, int width, int hight)
  {
    String pathUrl = g(imagePath);
    String pictureStyle;
    if (imagePath.contains(".png")) {
      pictureStyle = ".png";
    } else {
      pictureStyle = ".jpg";
    }
    String newImagePath = getUrlWithSize(pathUrl, width, hight, pictureStyle);

    Bitmap localBitmap = i(newImagePath);
    if ((localBitmap != null) && (!localBitmap.isRecycled())) {
      return localBitmap;
    }
    localBitmap = i(pathUrl);
    if ((localBitmap != null) && (!localBitmap.isRecycled()))
    {
      a(newImagePath, localBitmap, width, hight, pictureStyle);
      return localBitmap;
    }
    String str4 = c(imagePath);
    if (!isEmpty(str4))
    {
      localBitmap = getBitmapByOption(str4, width, hight);
      if ((localBitmap != null) && (!localBitmap.isRecycled())) {
        a(newImagePath, localBitmap, width, hight, pictureStyle);
      }
    }
    return localBitmap;
  }

  private String c(String paramString)
  {
    return d(paramString);
  }

  private Bitmap i(String paramString)
  {
    return b(paramString);
  }

  private Bitmap a(String paramString1, Bitmap paramBitmap, int paramInt1, int paramInt2, String paramString2)
  {
    if ((paramBitmap != null) && (!isEmpty(paramString1)) && (paramInt1 != 0)) {
      a(paramBitmap, paramString1, paramString2);
    }
    return null;
  }
}
