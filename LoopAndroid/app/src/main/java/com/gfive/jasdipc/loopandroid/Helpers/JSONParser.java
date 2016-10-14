package com.gfive.jasdipc.loopandroid.Helpers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JasdipC on 2016-10-13.
 */

public class JSONParser {
    public static void parseJSON(String responseString) {
        JSONObject responseJSON = null;
        try {
            responseJSON = new JSONObject(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HELPER", "ERROR TRYING TO PARSE JSON RESPONSE");
        }


    }
}
