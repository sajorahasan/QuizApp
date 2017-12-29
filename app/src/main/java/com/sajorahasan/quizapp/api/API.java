package com.sajorahasan.quizapp.api;

import com.sajorahasan.quizapp.api.callback.CallbackResults;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sajora on 27-12-2017.
 */

public interface API {

    /* Default api to get quiz by que number*/
    @GET("api.php")
    Single<CallbackResults> getQuizByQueNo(@Query("amount") int amount);

    /* Api to get quiz by category*/
    @GET("api.php")
    Single<CallbackResults> getQuizByCategory(@Query("amount") int amount,
                                              @Query("category") int category);

    /* Api to get quiz by difficulty*/
    @GET("api.php")
    Single<CallbackResults> getQuizByDifficulty(@Query("amount") int amount,
                                                @Query("difficulty") String difficulty);

    /* Api to get quiz by type*/
    @GET("api.php")
    Single<CallbackResults> getQuizByType(@Query("amount") int amount,
                                          @Query("type") String type);

    /* Api to get quiz by category and difficulty*/
    @GET("api.php")
    Single<CallbackResults> getQuizByCatAndDiff(@Query("amount") int amount,
                                                @Query("category") int category,
                                                @Query("difficulty") String difficulty);

    /* Api to get quiz by category and type*/
    @GET("api.php")
    Single<CallbackResults> getQuizByCatAndType(@Query("amount") int amount,
                                                @Query("category") int category,
                                                @Query("type") String type);

    /* Api to get quiz by difficulty and type*/
    @GET("api.php")
    Single<CallbackResults> getQuizByDiffAndType(@Query("amount") int amount,
                                                 @Query("difficulty") String difficulty,
                                                 @Query("type") String type);

    /* Api to get quiz by category, difficulty and type*/
    @GET("api.php")
    Single<CallbackResults> getQuizByCatDiffAndType(@Query("amount") int amount,
                                                    @Query("category") int category,
                                                    @Query("difficulty") String difficulty,
                                                    @Query("type") String type);
}
