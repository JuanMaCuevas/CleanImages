package nl.delascuevas.imagesearch;

import android.app.Application;

import nl.delascuevas.imagesearch.datasource.SearchService;

/**
 * Created by juanma on 15/02/15.
 */
public class MyApp extends Application {


    private static MyApp singleton;
    private SearchService mService;

    public static MyApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mService = new SearchService(this);

    }

    public SearchService getmService() {
        return mService;
    }

}
