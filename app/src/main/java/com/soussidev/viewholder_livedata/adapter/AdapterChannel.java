package com.soussidev.viewholder_livedata.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.soussidev.viewholder_livedata.R;
import com.soussidev.viewholder_livedata.model.Channel;
import com.soussidev.viewholder_livedata.server.Constant;

import java.util.List;

import static com.soussidev.viewholder_livedata.server.Constant.BASE_URL;

/**
 * Created by Soussi on 23/03/2018.
 */

public class AdapterChannel extends RecyclerView.Adapter<AdapterChannel.ItemViewHolder>{

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private List<Channel> mChannel;
    private GridLayoutManager mLayoutManager;
    private  Context mcontext;
    private Drawable mDefaultCardImage;

    /**
     * @auteur Soussi Mohamed
     * @see AdapterChannel
     * @param context
     * @param items
     * @param layoutManager
     */
    public AdapterChannel(List<Channel> items, GridLayoutManager layoutManager, Context context) {
        mChannel = items;
        mLayoutManager = layoutManager;
        mcontext = context;
    }



    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small, parent, false);
        }
        return new ItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {


        final Channel channel = mChannel.get(position );
        holder.title.setText(channel.getName_channel());

        mDefaultCardImage = mcontext.getResources().getDrawable(R.drawable.img1);
        Glide.with(mcontext)
                .load(Constant.BASE_URL+Constant.NAME_PATH+channel.getUrl_image())
                .into(holder.iv);
        if (getItemViewType(position) == VIEW_TYPE_BIG) {
            holder.info.setText(channel.getCategory() + " Cat  ·  " + channel.getQuality_ch() + " ");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),String.valueOf(channel.getName_channel()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChannel.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView title;
        TextView info;

        ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_BIG) {
                iv = (ImageView) itemView.findViewById(R.id.image_big);
                title = (TextView) itemView.findViewById(R.id.title_big);
                info = (TextView) itemView.findViewById(R.id.tv_info);
            } else {
                iv = (ImageView) itemView.findViewById(R.id.image_small);
                title = (TextView) itemView.findViewById(R.id.title_small);
            }

        }

    }
}
