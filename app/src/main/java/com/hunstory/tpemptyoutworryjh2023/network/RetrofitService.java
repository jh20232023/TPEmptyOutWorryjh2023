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

import java.io.File;

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
    Call<String> insertDBText(@Query("date") String date, @Query("title") String title, @Query("message") String message, @Query("em") String em);

    @GET("TPEmptyOutWorry/insertDBImagePath.php")
    // Call<String> 인데 int no Query 해도 되는 지...
    Call<String> insertDBImagePath(@Query("date")String date,@Query("imagePath")String imagePath);
    @GET("TPEmptyOutWorry/loadDBToNo.php")
    Call<Integer> loadDBToNo(@Query("no") Integer no);
}
//
//
//public class MainActivity extends AppCompatActivity {
//    ActivityMainBinding binding;
//
//    String imgPath; // 이미지의 실제경로
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // setContentView(R.layout.activity_main);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.btnSelect.setOnClickListener(view -> clickSelect());
//        binding.btnUpload.setOnClickListener(view -> clickUpload());
//
//    }
//    void clickSelect() {
//        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//        resultLauncher.launch(intent);
//    }
//    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode()==RESULT_CANCELED) return;
//
//        Intent intent = result.getData();
//        Uri uri = intent.getData();
//
//        binding.tv.setText(uri.toString());
//        Glide.with(this).load(uri).into(binding.iv);
//
//        // uri는 파일의 실제 경로가 아님. 미디어저장소의 DB 경로임.
//        // Http 통신을 파일을 전송하려면 DB주소가 아니라.. 실제 파일의 경로가 필요함.
//        imgPath= getRealPathFromUri(uri);
//        binding.tv.setText(imgPath);
//    });
//    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
//    String getRealPathFromUri(Uri uri){
//        String[] proj= {MediaStore.Images.Media.DATA};
//        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
//        Cursor cursor= loader.loadInBackground();
//        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result= cursor.getString(column_index);
//        cursor.close();
//        return  result;
//    }
//    void  clickUpload() {
//        if (imgPath==null){
//            Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        // retrofit 라이브러리를 이용하여 이미지파일 업로드하기..
//        // 1) Retrofit 객체 생성
//        Retrofit.Builder builder = new Retrofit.Builder();
//        builder.baseUrl("http://jh2023.dothome.co.kr");
//        builder.addConverterFactory(ScalarsConverterFactory.create());
//        Retrofit retrofit = builder.build();
//
//        // 2) 원하는 작업에 대한 명세를 작성하는 인터페이스와 추상메소드를 설계 -RetrofitService.java , uploadImage()
//
//        // 3) 인터페이스 객체로 생성
//        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
//
//        // 4) 원하는 작업명세를 호출 - 이때 통신하지 않음. 업로드 하는 코드를 가진 Call 객체를 리턴해줌
//
//        // 이미지 파일을 HTTP 통신용 파일 택배박스로 포장하기
//        File file = new File(imgPath);
//        RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"),file); // 진공팩같은 객체
//        MultipartBody.Part part= MultipartBody.Part.createFormData("img",file.getName(),requestBody);
//
//        Call<String> call = retrofitService.uploadImage(part);
//        // 5) 네트워크작업 실행
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                String s =response.body();
//                new AlertDialog.Builder(MainActivity.this).setMessage(s).create().show();
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//}
////////////
//public interface RetrofitService {
//
//    @Multipart
//    @POST("05Retrofit/aaa.php")
//    Call<String> uploadImage(@Part MultipartBody.Part file);
//
//
//}

