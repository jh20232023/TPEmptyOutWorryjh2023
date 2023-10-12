package com.hunstory.tpemptyoutworryjh2023.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signup.php")
    Call<String> signUp(@Field("id") String id, @Field("pw") String pw, @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signin.php")
    Call<String> signIn(@Field("id") String id, @Field("pw") String pw);



}
