package com.gfive.jasdipc.loopandroid.Interfaces;

import okhttp3.Response;

/**
 * Created by JasdipC on 2016-10-09.
 */

public interface OnServerResponse {
    void serverCallback(Boolean isSuccessful, Response serverResponse);
}