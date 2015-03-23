package nl.delascuevas.imagesearch.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nl.delascuevas.imagesearch.MyApp;
import nl.delascuevas.imagesearch.R;
import nl.delascuevas.imagesearch.datasource.Response;
import nl.delascuevas.imagesearch.datasource.SearchService;
import nl.delascuevas.imagesearch.util.OnFragmentInteractionListener;
import nl.delascuevas.imagesearch.util.SearchObserver;
import nl.delascuevas.imagesearch.ui.view.adapters.GridViewAdapter;
import nl.delascuevas.imagesearch.ui.view.widgets.EndlessScrollListener;


public class SearchFragment extends Fragment implements SearchObserver {

    @Inject
    SearchService service;
    @InjectView(R.id.background_icon)
    ImageView background;

    private OnFragmentInteractionListener mListener;
    private GridViewAdapter mAdapter;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, rootView);

        GridView grid = (GridView) rootView.findViewById(R.id.grid_view);

        mAdapter = new GridViewAdapter(getActivity());
        grid.setAdapter(mAdapter);
        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int amount, int totalItemsCount) {
                requestMoreImages(amount);
//                Toast.makeText(getActivity(), "moar!",
//                        Toast.LENGTH_SHORT).show();
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = ((Response.Item)view.getTag()).url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        service.subscribe(mAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
        service.unsubscribe(mAdapter);
    }


    // Append more data into the adapter
    public void requestMoreImages(int offset) {
        service.loadEnd(offset);
    }

    @Override
    public void newSearch(String s) {
        //TODO: add nice loader
        background.animate().alpha(1).setDuration(200);
        background.animate().alpha(0).setDuration(300);
    }
}
