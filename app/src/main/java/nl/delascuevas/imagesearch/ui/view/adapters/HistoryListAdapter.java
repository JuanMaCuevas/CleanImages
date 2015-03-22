package nl.delascuevas.imagesearch.ui.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import nl.delascuevas.imagesearch.ui.MainActivity;
import nl.delascuevas.imagesearch.R;

/**
 * Created by juanma on 15/02/15.
 */
public class HistoryListAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private LinkedHashSet<String> mQueries;

    public HistoryListAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mQueries = new LinkedHashSet<String>();

    }

    @Override
    public void addAll(Collection<? extends String> items) {
        super.addAll(items);
        mQueries.addAll(items);
    }

    @Override
    public void add(String object) {
        super.add(object);
        mQueries.add(object);
    }

    public Set<String> getAll() {
        return mQueries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.history_list_row, null);
        }

        //Handle TextView and display string from the list
        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        String q = getItem(position);
        listItemText.setText(q);
        view.setTag(q);

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        deleteBtn.setTag(q);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove((String) v.getTag());
                mQueries.remove((String) v.getTag());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this is nasty but i want to go to sleep
                ((MainActivity) mContext).searchQuery((String) v.getTag());
            }
        });

        return view;
    }

}
