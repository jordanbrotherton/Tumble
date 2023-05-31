package com.jordanjbrotherton.tumble.feed;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jordanjbrotherton.tumble.R;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FeedWidget> homeFeedArrayList;

    public FeedAdapter(ArrayList<FeedWidget> feedList) {
        homeFeedArrayList = feedList;
    }

    //Creates the widgets based on the type of the widget.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_generic, parent, false);
            return new FeedViewHolder(v1);
        }else if (viewType == 2 ){
            View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_favorites, parent, false);
            return new FeedViewFavoritesHolder(v2);
        }else{
            View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_music, parent, false);
            return new FeedViewMusicHolder(v3);
        }
    }

    //Sets the text on the widgets.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedWidget currentItem = homeFeedArrayList.get(position);

        switch(getItemViewType(position)){
            case 1:
                ((FeedViewHolder) holder).widgetTitle.setText(currentItem.getFeedTitle());
                ((FeedViewHolder) holder).widgetContent.setText(currentItem.getFeedContents());
                break;
            case 2:
                ((FeedViewFavoritesHolder) holder).widgetTitle.setText(currentItem.getFeedTitle());
                break;
            case 3:
                ((FeedViewMusicHolder) holder).widgetTitle.setText(currentItem.getFeedTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homeFeedArrayList.size();
    }

    //Fetches the type of widget to render.
    public int getItemViewType(int position){
        FeedWidget currentItem = homeFeedArrayList.get(position);
        int result = 0;
        switch(currentItem.getWidgetType()){
            case 1:
                result = 1;
                break;
            case 2:
                result = 2;
                break;
            case 3:
                result = 3;
                break;
        }
        return result;
    }

    //==================
    //Widget ViewHolders
    //==================

    //Generic Text Widget (1)
    static class FeedViewHolder extends RecyclerView.ViewHolder{
        public TextView widgetTitle;
        public TextView widgetContent;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            widgetTitle = itemView.findViewById(R.id.TitleWidget);
            widgetContent = itemView.findViewById(R.id.WidgetContent);
        }
    }

    //Favorite Apps Widget (2)
    static class FeedViewFavoritesHolder extends RecyclerView.ViewHolder{
        public TextView widgetTitle;
        public ImageView app1;
        public ImageView app2;
        public ImageView app3;
        public ImageView app4;
        public ImageView app5;

        public FeedViewFavoritesHolder(@NonNull View itemView) {
            super(itemView);
            widgetTitle = itemView.findViewById(R.id.TitleWidget);
            app1 = itemView.findViewById(R.id.imageView);
            app2 = itemView.findViewById(R.id.imageView2);
            app3 = itemView.findViewById(R.id.imageView3);
            app4 = itemView.findViewById(R.id.imageView4);
            app5 = itemView.findViewById(R.id.imageView5);
        }
    }

    //Media Widget (3)
    static class FeedViewMusicHolder extends RecyclerView.ViewHolder{
        public TextView widgetTitle;
        public TextView songTitle;
        public TextView artistTitle;
        public ImageView albumArt;

        public FeedViewMusicHolder(@NonNull View itemView) {
            super(itemView);
            widgetTitle = itemView.findViewById(R.id.TitleWidget);
            songTitle = itemView.findViewById(R.id.songTitle);
            artistTitle = itemView.findViewById(R.id.ArtistName);
            albumArt = itemView.findViewById(R.id.album);
        }
    }
}
