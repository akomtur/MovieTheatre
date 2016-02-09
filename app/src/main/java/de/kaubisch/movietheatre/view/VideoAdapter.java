package de.kaubisch.movietheatre.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.kaubisch.movietheatre.R;

/**
 * Created by kaubisch on 06.02.16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    public List<VideoItem> itemList;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout layout;
        private TextView videoTitle;
        private VideoAdapter adapter;

        public ViewHolder(View v, VideoAdapter adapter) {
            super(v);
            this.adapter = adapter;
            videoTitle = (TextView) v.findViewById(R.id.video_title);
            layout = (LinearLayout) v.findViewById(R.id.video_item);
            layout.setOnClickListener(this);
        }

        public void setTitle(final String title) {
            videoTitle.setText(title);
        }
        @Override
        public void onClick(View v) {
            adapter.onClick(this);
        }
    }

    public VideoAdapter(final Activity activity) {
        itemList = new ArrayList<>();
        this.activity = activity;
    }

    public VideoAdapter(final Activity activity, final List<VideoItem> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_trailer, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoItem videoItem = itemList.get(position);
        holder.setTitle(videoItem.name);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<VideoItem> getItems() {
        return itemList;
    }

    private void notifyUI() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    private void onClick(final ViewHolder vh) {
        VideoItem videoItem = itemList.get(vh.getAdapterPosition());
        playVideo(videoItem.id);
    }

    private void playVideo(final String id) {
        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentActivities(videoIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if(resolveInfos.isEmpty()) {
            videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + id));
        }
        activity.startActivity(videoIntent);

    }
}
