package me.black.library;

import android.app.Application;
import android.content.Context;

public class LibApp extends Application {

    /** Instance of the current application. */
    private static LibApp instance;
    private static String baseUrl;

    /**
     * Constructor.
     */
    public LibApp() {
        instance = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static Context getContext() {
        return instance;
    }
}
