package com.soussidev.viewholder_livedata;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.soussidev.viewholder_livedata.RxPref.PrefItem;
import com.soussidev.viewholder_livedata.RxPref.RxSharedPreferences;
import com.soussidev.viewholder_livedata.adapter.AdapterChannel;
import com.soussidev.viewholder_livedata.model.Channel;
import com.soussidev.viewholder_livedata.server.Constant;
import com.soussidev.viewholder_livedata.server.RequestInterface;
import com.soussidev.viewholder_livedata.server.ServerRequest;
import com.soussidev.viewholder_livedata.server.ServerResponse;
import com.soussidev.viewholder_livedata.viewmodel.ChannelViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName();

    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private RecyclerView recyclerView;
    private AdapterChannel itemAdapter;
    private GridLayoutManager gridLayoutManager;

    private SharedPreferences sharedPreferences;
    private RxSharedPreferences rxShared;

    private ChannelViewModel channelViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelViewModel = ViewModelProviders.of(this).get(ChannelViewModel.class);
        channelViewModel.getLiveChannel().observe(this, ch -> {

                itemAdapter = new AdapterChannel(ch, gridLayoutManager,this);
                recyclerView.setAdapter(itemAdapter);

        });


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        rxShared = RxSharedPreferences.with(sharedPreferences);

        rxShared.getInt("span", 0)
                .subscribe(span_count -> {
                    //Get Span Count
                    Log.d("Get pref", "SPAN: " + span_count);
                    if(span_count.equals(0))
                    {
                        // If Span Count 0 Replace With 1
                        rxShared.putString("app_name","layout_switch_RXShared_Pref")
                                .flatMap(span_c ->rxShared.putInteger("span",SPAN_COUNT_ONE))
                                .flatMap(span_name ->rxShared.putString("span.name","Single"))
                                .flatMap(span_item -> rxShared.getAll())
                                .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                                .map(Object::toString)

                                .subscribe(s -> Log.d("TAG 1", s));
                    }
                });


        Observable.just(new PrefItem())
                .flatMap(prefItem -> rxShared.getInt("span", 1), (prefItem, sp) -> {
                    prefItem.setSpan_count(sp);
                    return prefItem;
                })

                .flatMap(prefItem -> rxShared.getString("span.name", ""), (prefItem, sp) -> {
                    prefItem.setSpan_name(sp);
                    return prefItem;
                })

                .subscribe(prefItem -> {
                    Log.d("TAG 3 NUM:", String.valueOf(prefItem.getSpan_count()));
                    Log.d("TAG 3 NAME:", prefItem.getSpan_name());
                    //Get span From RXPref to Layout Manager
                    gridLayoutManager = new GridLayoutManager(this, prefItem.getSpan_count());
                });




        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(gridLayoutManager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_switch_layout) {
            switchLayout();
            switchIcon(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);

            rxShared.putString("app_name","layout_switch_RXShared_Pref")
                    .flatMap(span_count ->rxShared.putInteger("span",SPAN_COUNT_THREE))
                    .flatMap(span_name ->rxShared.putString("span.name","Multiple"))
                    .flatMap(span_item -> rxShared.getAll())
                    .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                    .map(Object::toString)

                    .subscribe(s -> Log.d("Switch_layout 3:", s));


        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);

            rxShared.putString("app_name","layout_switch_RXShared_Pref")
                    .flatMap(span_count ->rxShared.putInteger("span",SPAN_COUNT_ONE))
                    .flatMap(span_name ->rxShared.putString("span.name","Single"))
                    .flatMap(span_item -> rxShared.getAll())
                    .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                    .map(Object::toString)

                    .subscribe(s -> Log.d("Switch_layout 1:", s));


        }
        itemAdapter.notifyItemRangeChanged(0, itemAdapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        }
    }


}
