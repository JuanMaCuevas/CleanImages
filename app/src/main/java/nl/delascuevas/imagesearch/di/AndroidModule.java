package nl.delascuevas.imagesearch.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.delascuevas.imagesearch.MyApp;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(library = true)
public class AndroidModule {
    private final MyApp application;

    public AndroidModule(MyApp application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext() {
        return application;
    }

//    @Provides @Singleton LocationManager provideLocationManager() {
//        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
//    }
}