package com.soussidev.viewholder_livedata.RxPref;

/**
 * Created by Soussi on 23/03/2018.
 */

public class PrefItem {

    private  int span_count;
    private  String span_name;
   /* public int getSPAN_COUNT() {
        return span_count;
    }

    public void setSPAN_COUNT(int SPAN_COUNT) {
        this.span_count = SPAN_COUNT;
    }*/

    public PrefItem() {
    }

    public String getSpan_name() {
        return span_name;
    }

    public void setSpan_name(String span_name) {
        this.span_name = span_name;
    }

    public int getSpan_count() {
        return span_count;
    }

    public void setSpan_count(int span_count) {
        this.span_count = span_count;
    }
}
