package com.hengzhi.test.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.hengzhi.test.utils.UiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.squareup.leakcanary.LeakCanary;

import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * 作者: zhangl
 * 时间: 2017/11/2 0002
 * 描述: 自定义Application提供全局Context以及初始化第三方api
 **/

public class SuperApplication extends MultiDexApplication {

    private static SuperApplication myApplication = null;

    public static SuperApplication getApplication() {
        return myApplication;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        if (LeakCanary.isInAnalyzerProcess(myApplication)) {
            return;
        }
        LeakCanary.install(myApplication);
        Utils.init(myApplication);
        UiUtil.init(myApplication);
        initOkGo();


    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        OkGo.getInstance().init(myApplication)
                .setOkHttpClient(builder.build())
        ;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
