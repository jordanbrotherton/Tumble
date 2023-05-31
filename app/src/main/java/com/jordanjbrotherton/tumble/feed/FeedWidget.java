package com.jordanjbrotherton.tumble.feed;

//This is the data for the feed widgets.

public class FeedWidget {
    private String feedTitle; //Header of widget.
    private String feedContents; //Contents of the inner widget. Only for Generic Text (1).
    private int widgetType; //Specifies which layout to use.

    public FeedWidget(String text1, String text2, int int1){
        feedTitle = text1;
        feedContents = text2;
        widgetType = int1;
    }

    public String getFeedTitle(){
        return feedTitle;
    }

    public String getFeedContents(){
        return feedContents;
    }

    public int getWidgetType() {return widgetType;}
}
