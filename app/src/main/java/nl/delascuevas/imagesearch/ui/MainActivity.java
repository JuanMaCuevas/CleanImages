package nl.delascuevas.imagesearch.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import nl.delascuevas.imagesearch.MyApp;
import nl.delascuevas.imagesearch.R;
import nl.delascuevas.imagesearch.ui.view.TwoViewPager;
import nl.delascuevas.imagesearch.util.OnFragmentInteractionListener;
import nl.delascuevas.imagesearch.util.SearchObserver;


public class MainActivity extends FragmentActivity implements
        OnFragmentInteractionListener {

    @InjectView(R.id.pager)
    TwoViewPager mPager;
    @InjectView(R.id.edit_search)
    EditText mEditSearch;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initialiceInput();
    }

    private void initialiceInput() {
        showKeyboardOnView(mEditSearch);
        mEditSearch.setOnEditorActionListener(mEditorAction);
    }

    private TextView.OnEditorActionListener mEditorAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String s = v.getText().toString();
                MyApp.getInstance().getmService().search(s);
                hideKeyboard();
                mPager.setCurrentItem(0, true);
                return true;
            }
            return false;
        }
    };

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
