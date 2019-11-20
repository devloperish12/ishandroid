package com.indiansmarthub.ish.activity;

import android.app.Application;

import com.indiansmarthub.ish.PaymentPackage.AppEnvironment;

public class BaseApplication extends Application {
    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
        appEnvironment = AppEnvironment.SANDBOX;
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }
}
