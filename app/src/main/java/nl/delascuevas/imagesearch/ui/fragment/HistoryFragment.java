package nl.delascuevas.imagesearch.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nl.delascuevas.imagesearch.ui.MainActivity;
import nl.delascuevas.imagesearch.R;
import nl.delascuevas.imagesearch.util.OnFragmentInteractionListener;
import nl.delascuevas.imagesearch.util.SearchObserver;
import nl.delascuevas.imagesearch.ui.view.adapters.HistoryListAdapter;

public class HistoryFragment extends Fragment implements SearchObserver {

    private static final String PREFS_HISTORY = "history";
    @InjectView(R.id.history_listvew)
    ListView mListView;

    private OnFragmentInteractionListener mListener;
    private HistoryListAdapter mAdapter;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.inject(this, view);
        mAdapter = new HistoryListAdapter(getActivity(), R.layout.history_list_row);
        loadHistory(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).searchQuery((String) view.getTag());
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        saveHistory(getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void loadHistory(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        Set<String> set = sp.getStringSet(PREFS_HISTORY, null);
        if(set!=null) {
            mAdapter.addAll(set);
        }
    }

    private void saveHistory(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        sp.edit().putStringSet(PREFS_HISTORY, mAdapter.getAll()).apply();

    }

    @Override
    public void newSearch(String s) {
        mAdapter.add(s);
        saveHistory(getActivity());
    }
}
