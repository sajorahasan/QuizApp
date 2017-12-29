package com.sajorahasan.quizapp.api.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajora on 27-12-2017.
 */

public class CallbackResults {
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public List<Result> getResults() {
        return results;
    }
}
