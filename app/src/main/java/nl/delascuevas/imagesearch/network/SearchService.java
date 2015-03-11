package nl.delascuevas.imagesearch.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.delascuevas.imagesearch.BuildConfig;
import nl.delascuevas.imagesearch.models.Response;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by juanma on 12/02/15.
 */
public class SearchService {

    private static final Integer PAGE_SIZE = 8;

    private static final int IDLE = 0;
    private static final int WAITING = 1;
    private final LinkedHashSet<ResultsPresenter> mPresenters;
    private final Context mContext;
    private Callback callback = new Callback<Response>() {

        @Override
        public void success(Response imagesResults, retrofit.client.Response response) {
            if (imagesResults != null && imagesResults.responseData != null && imagesResults.responseData.results != null) {
                if (!imagesResults.responseData.results.isEmpty()) {

                    for (ResultsPresenter p : mPresenters) {
                        p.addResults(imagesResults.responseData.results);
                    }
                    mPages.add(mPagesQueue.poll());
                    processQueue();
                }
                if (imagesResults.responseStatus == 403) {
                    Toast.makeText(mContext, "Oops! Google is angry. No more images for us :(", Toast.LENGTH_LONG);
                }
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Log.v("", "error: " + error.toString());
        }

    };
    private final GoogleImagesService mService;
    private ArrayList<String> mQueries;
    private String mQuery;
    private int mServiceStatus;

    private ConcurrentLinkedQueue<Integer> mPagesQueue;
    private ArrayList<Integer> mPages;

    public SearchService(Context context) {

        mContext = context;

        mPresenters = new LinkedHashSet<>();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://ajax.googleapis.com")
                .build();

        if (BuildConfig.DEBUG) restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        mService = restAdapter.create(GoogleImagesService.class);

        initQueries();
    }

    public boolean subscribe(ResultsPresenter p) {
        return mPresenters.add(p);
    }

    public boolean unsubscribe(ResultsPresenter p) {
        return mPresenters.remove(p);
    }


    public void search(String query) {
        mQuery = query;
        for (ResultsPresenter p : mPresenters) {
            p.clearResults();
        }
        mQueries.add(mQuery);

        mPages = new ArrayList<>();
        mPagesQueue = new ConcurrentLinkedQueue<>();

        mServiceStatus = IDLE;
        queuePages(0, 5);
        processQueue();
    }


    public void loadEnd(int amount) {
        if (mPages != null && mPages.size() > 0) {
            int last = mPages.get(mPages.size() - 1);
            queuePages(last + 1, last + amount);
        }
        if (mServiceStatus == IDLE) {
            processQueue();
        }
    }


    private void initQueries() {
        //TODO: load old mQueries from Shared Preferences
        mQueries = new ArrayList<>();
    }

    private void queuePages(int ini, int end) {
        if (end < ini) return;
        for (int i = ini; i <= end; i++) {
            mPagesQueue.add(i);
        }

    }

    private void processQueue() {
        Integer page = mPagesQueue.peek();
        if (page != null) {
            requestPage(page);
        } else {
            mServiceStatus = IDLE;
        }

    }

    private void requestPage(Integer page) {
        mService.searchImages(mQuery, page * PAGE_SIZE, callback);
        mServiceStatus = WAITING;
    }


    public interface GoogleImagesService {

        @GET("/ajax/services/search/images?v=1.0&rsz=8")
            //max page size == 8
        void searchImages(@Query("q") String query, @Query("start") int start, Callback<Response> cb);
    }


    public interface ResultsPresenter {
        public void addResults(List<Response.Item> list);

        public void clearResults();
    }

}
