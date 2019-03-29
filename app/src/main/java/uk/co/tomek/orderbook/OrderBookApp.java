package uk.co.tomek.orderbook;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;
import android.os.StrictMode;

import timber.log.Timber;

/**
 * Main Application class.
 */
public class OrderBookApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            setupLeakCanary();
            enableStrictMode();
        }
    }

    /**
     * Enables slow actions detection.
     */
    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    /**
     * Memory leaks detection.
     * TODO: Remove it as is it a 3rd party library
     */
    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.refWatcher(this)
                .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
                .buildAndInstall();
    }
}
