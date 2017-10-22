package com.example.apichaya.addrealmsudent;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;


public class MyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Realm.init(this);

  }
}

