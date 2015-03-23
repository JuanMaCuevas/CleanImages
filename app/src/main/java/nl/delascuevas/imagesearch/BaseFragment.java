package nl.delascuevas.imagesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * Base fragment which performs injection using the activity object graph of its parent.
 */
public class BaseFragment extends Fragment {
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }
}