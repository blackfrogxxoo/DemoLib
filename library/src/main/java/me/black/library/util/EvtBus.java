package me.black.library.util;

import org.greenrobot.eventbus.EventBus;

public class EvtBus {
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
