package com.hunstory.tpemptyoutworryjh2023.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signup.php")
    Call<String> signUp(@Field("id") String id, @Field("pw") String pw, @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signin.php")
    Call<String> signIn(@Field("id") String id, @Field("pw") String pw);

    @GET("TPEmptyOutWorry/insertDBtext.php")
    Call<String> insertDBText(@Query("date") String date, @Query("title") String title, @Query("message") String message, @Query("em") String em);




}
