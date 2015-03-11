package nl.delascuevas.imagesearch.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.delascuevas.imagesearch.R;
import nl.delascuevas.imagesearch.models.Response;
import nl.delascuevas.imagesearch.network.SearchService;
import nl.delascuevas.imagesearch.views.widgets.SquaredImageView;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public final class GridViewAdapter extends BaseAdapter implements SearchService.ResultsPresenter {
    private final Context context;
    private final List<Response.Item> urls = new ArrayList<Response.Item>();

    public GridViewAdapter(Context context) {
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        Response.Item item = urls.get(position);
        view.setTag(item);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(item.tbUrl) //
                .placeholder(R.color.dark_purple) //
                .error(R.color.dark_grey) //
                .fit().centerCrop()
                .tag(context) //
                .into(view);

        return view;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position).tbUrl;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void appendResults(List<Response.Item> results) {

    }


    @Override
    public void addResults(List<Response.Item> list) {
        for (Response.Item i : list) {
            urls.add(i);
        }
        super.notifyDataSetChanged();
    }

    @Override
    public void clearResults() {
        urls.clear();
        super.notifyDataSetChanged();
    }
}
