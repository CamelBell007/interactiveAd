package com.interactive.suspend.ad.html;

import android.content.Context;
import android.util.Log;

import com.interactive.suspend.ad.util.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by csc on 15/11/20.
 */
public class SdkLoader {
    private Context mContext;
//    private DexClassLoader mDexClassLoader;
    private ClassLoader mDexClassLoader;

    //每次将此值改为和adsdkcore里的BuildConfig值一致
    static final int ASSETS_DEX_LOADER_VERSION = 122;

    private static SdkLoader INSTANCE;

    public synchronized static SdkLoader getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SdkLoader(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private SdkLoader(Context context) {
        mContext = context.getApplicationContext();
//        loadJar();
        mDexClassLoader = getClass().getClassLoader();
    }

    public Class<?> loadClass(String className) {
        try {
            return mDexClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BaseLoaderInterface newPreloader(String name, Context ctx) {
        try {
            Class<?> preloader = mDexClassLoader.loadClass(name);
            Method getInstance = preloader.getDeclaredMethod("getInstance", Context.class);
            return (BaseLoaderInterface) getInstance.invoke(preloader, ctx);
        } catch (ClassNotFoundException e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }  catch (IllegalAccessException e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e(LogUtil.TAG,e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(LogUtil.TAG,e.getMessage());
        }
        return null;
    }

    public NativeInterface newNtInterface() {
        //FIXME 改为正常类的引用
        return NtInterface.getInstance();
    }

    public Object newObject(String name, Object... params) {
        try {
            ClassLoader classLoader = mDexClassLoader;
            Class<?> klass = Class.forName(name, true, classLoader);
            if (params.length == 0) {
                return klass.newInstance();
            }

            Class<?> paramTypes[] = new Class<?>[params.length / 2];
            for (int i = 0; i < paramTypes.length; i++) {
                paramTypes[i] = (Class<?>) params[i];
            }

            Object[] paramObjects = new Object[params.length / 2];
            for (int i = 0; i < paramObjects.length; i++) {
                paramObjects[i] = params[params.length / 2 + i];
            }
            Constructor<?> constructor = klass.getConstructor(paramTypes);
            return constructor.newInstance(paramObjects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
