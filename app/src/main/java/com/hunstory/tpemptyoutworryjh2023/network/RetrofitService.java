package com.hunstory.tpemptyoutworryjh2023.network;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.hunstory.tpemptyoutworryjh2023.data.LoadDataImagePath;
import com.hunstory.tpemptyoutworryjh2023.data.LoadDataText;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signup.php")
    Call<String> signUp(@Field("id") String id, @Field("pw") String pw, @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST("TPEmptyOutWorry/signin.php")
    Call<String> signIn(@Field("id") String id, @Field("pw") String pw);

    @GET("TPEmptyOutWorry/insertDBText.php")
    Call<String> insertDBText(@Query("id") String id ,@Query("date") String date, @Query("title") String title, @Query("message") String message, @Query("em") String em);

    @Multipart
    @POST("TPEmptyOutWorry/insertDBImagePath.php")
    Call<String> insertDBImagePath(@Part("no") String no, @Part("id") String id, @Part("date")String date,@Part MultipartBody.Part imagePath);

     @GET("TPEmptyOutWorry/loadDBText.php")
     Call<ArrayList<LoadDataText>> loadDBSPL(@Query("date") String date, @Query("id") String id);
    @GET("TPEmptyOutWorry/loadDBImagePath.php")
    Call<ArrayList<LoadDataImagePath>> loadDBSPLI(@Query("date") String date, @Query("id") String id);

}
