package com.soussidev.viewholder_livedata.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Soussi on 23/03/2018.
 */

public interface RequestInterface {

    @POST("IPTVTunisia/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
