package nl.delascuevas.imagesearch.ui.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import nl.delascuevas.imagesearch.R;
import nl.delascuevas.imagesearch.ui.view.adapters.TwoPagesAdapter;
import nl.delascuevas.imagesearch.ui.view.widgets.DepthPageTransformer;

/**
 * Created by juanma on 23/03/15.
 */
public class TwoViewPager extends ViewPager {
    public TwoViewPager(Context context) throws Exception {
        super(context);
        init();
    }

    public TwoViewPager(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        init();
    }

    private void init() throws Exception {
        android.support.v4.app.FragmentManager fm = null;
        try {
            fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
        } catch (ClassCastException e) {
            throw new Exception("View Pager should be inflated by a FragmentActivity");
        }

        TwoPagesAdapter mAdapter = new TwoPagesAdapter(fm);
        setAdapter(mAdapter);
        setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
        setPageTransformer(true, new DepthPageTransformer());
    }
}
