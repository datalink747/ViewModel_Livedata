package com.soussidev.viewholder_livedata.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.soussidev.viewholder_livedata.Repository.Channel_Repository;
import com.soussidev.viewholder_livedata.model.Channel;

import java.util.List;

/**
 * Created by Soussi on 23/03/2018.
 */

public class ChannelViewModel extends ViewModel {

    private MutableLiveData<List<Channel>> liveChannel;

    private Channel_Repository channelRepository = new Channel_Repository();
    /**
     * @auteur Soussi Mohamed
     * @see ChannelViewModel
     *
     */

    //using livedata
    public MutableLiveData<List<Channel>> getLiveChannel() {
        if(liveChannel == null){
            liveChannel = channelRepository.getchannel();
        }
        return liveChannel;
    }
}
