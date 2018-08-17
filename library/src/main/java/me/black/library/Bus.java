package me.black.library;

import org.greenrobot.eventbus.EventBus;

public class Bus {
    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }
}
