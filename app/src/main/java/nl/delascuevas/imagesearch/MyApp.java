package nl.delascuevas.imagesearch;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import nl.delascuevas.imagesearch.di.AndroidModule;

/**
 * Created by juanma on 15/02/15.
 */
public class MyApp extends Application {
    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules().toArray());
    }


    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

}
