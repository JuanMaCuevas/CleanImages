package nl.delascuevas.imagesearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import nl.delascuevas.imagesearch.fragments.HistoryFragment;
import nl.delascuevas.imagesearch.fragments.SearchFragment;
import nl.delascuevas.imagesearch.utils.OnFragmentInteractionListener;
import nl.delascuevas.imagesearch.utils.SearchObserver;
import nl.delascuevas.imagesearch.views.widgets.DepthPageTransformer;


public class MainActivity extends FragmentActivity implements
        OnFragmentInteractionListener {

    @InjectView(R.id.pager)
    ViewPager mPager;
    @InjectView(R.id.edit_search)
    EditText mEditSearch;

    private Fragment[] mFragments;
    private PagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mFragments = new Fragment[]{new SearchFragment(), new HistoryFragment()};

        mPager.setAdapter(adapter);
        mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
        mPager.setPageTransformer(true, new DepthPageTransformer());

        showKeyboardOnView(mEditSearch);

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String s = v.getText().toString();
                    MyApp.getInstance().getmService().search(s);
                    hideKeyboard();
                    for (Fragment f : mFragments) {
                        ((SearchObserver) f).newSearch(s);
                    }
                    mPager.setCurrentItem(0, true);
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.edit_search)
    public void editClicked() {
        //go to history
        mPager.setCurrentItem(1, true);
        mEditSearch.setText("");
    }

    private void showKeyboardOnView(final View v) {
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(v, 0);
            }
        }, 100);
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mEditSearch.getWindowToken(), 0);
        }
    }

    public void searchQuery(String query) {
        mEditSearch.setText(query);
        MyApp.getInstance().getmService().search(query);
        hideKeyboard();
        mPager.setCurrentItem(0, true);

    }

    @Override
    public void onFragmentInteraction(String uri) {

    }

}
