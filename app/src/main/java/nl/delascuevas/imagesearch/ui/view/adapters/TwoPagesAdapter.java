package nl.delascuevas.imagesearch.ui.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nl.delascuevas.imagesearch.ui.fragment.HistoryFragment;
import nl.delascuevas.imagesearch.ui.fragment.SearchFragment;

/**
 * Created by juanma on 23/03/15.
 */
public class TwoPagesAdapter extends FragmentStatePagerAdapter {

    private final Fragment[] mFragments;

    public TwoPagesAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        mFragments = new Fragment[]{new SearchFragment(), new HistoryFragment()};
    }

    public TwoPagesAdapter(FragmentManager supportFragmentManager, Fragment[] fragments) {
        super(supportFragmentManager);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragments[0];
            case 1:
                return mFragments[1];
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Search";
            case 1:
                return "History";
        }
        return null;
    }
}
