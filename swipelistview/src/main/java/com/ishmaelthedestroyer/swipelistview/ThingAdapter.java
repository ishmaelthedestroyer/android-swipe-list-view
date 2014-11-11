package com.ishmaelthedestroyer.swipelistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThingAdapter extends BaseAdapter {

    private static final String TAG = "ThingAdapter";

    private ArrayList<Thing> mThings;
    private Context mContext;

    public ThingAdapter(Context context, ArrayList<Thing> things) {
        mContext = context;
        mThings = things;
    }

    @Override
    public int getCount() {
        return mThings.size();
    }

    @Override
    public Thing getItem(int pos) {
        return mThings.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        final Thing thing = getItem(pos);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView) parent).recycle(convertView, pos);

        holder.title.setText(thing.getTitle());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "List item clicked.");
                Toast.makeText(mContext, "Delete btn clicked.", Toast.LENGTH_LONG).show();

            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        Button deleteBtn;
    }

}
