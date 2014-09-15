package com.lzw.talk.base;

import android.app.Application;
import android.content.Context;
import com.avos.avoscloud.*;
import com.lzw.talk.activity.LoginActivity;
import com.lzw.talk.avobject.User;
import com.lzw.talk.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw on 14-5-29.
 */
public class App extends Application {
  public static final String DB_NAME = "chat.db3";
  public static final int DB_VER = 1;
  public static boolean debug = false;
  public static Context cxt;
  public static AVUser chatUser;
  public static Session session;
  private static Map<String, User> usersCache = new HashMap<String, User>();

  @Override
  public void onCreate() {
    super.onCreate();
    cxt = this;
    Utils.fixAsyncTaskBug();
    AVOSCloud.initialize(this, "x3o016bxnkpyee7e9pa5pre6efx2dadyerdlcez0wbzhw25g",
        "057x24cfdzhffnl3dzk14jh9xo2rq6w1hy1fdzt5tv46ym78");
    User.registerSubclass(User.class);
    AVInstallation.getCurrentInstallation().saveInBackground();
    PushService.setDefaultPushCallback(cxt, LoginActivity.class);
    if (App.debug) {
      AVOSUtils.showInternalDebugLog();
      Logger.open = true;
    }
  }

  public static AVUser lookupUser(String userId) {
    return usersCache.get(userId);
  }

  public static void registerUserCache(String userId, User user) {
    usersCache.put(userId, user);
  }

  public static void registerUserCache(User user) {
    registerUserCache(user.getObjectId(), user);
  }

  public static void registerBatchUserCache(List<User> users) {
    for (User user : users) {
      registerUserCache(user);
    }
  }
}
