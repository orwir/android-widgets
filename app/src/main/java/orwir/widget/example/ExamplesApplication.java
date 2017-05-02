package orwir.widget.example;

import android.app.Application;

import timber.log.Timber;

public class ExamplesApplication extends Application {

    @Override
    public void onCreate() {
        Timber.plant(new Timber.DebugTree());
        super.onCreate();
    }

}
