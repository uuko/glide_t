package com.example.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener  {
    MyAPIService myAPIService;
    private ArrayList<String> photoUrl;
    MyRecyclerViewAdapter adapter;
    private Call<Example> call;
private  RecyclerView recyclerView;
private  SwipeRefreshLayout mRefreshLayout;
    //https://www.flickr.com/services/rest/?method=flickr.photos.search&
    // api_key=f5c47104535c1a739703c68cc1680aec&text=test&format=json&nojsoncallback=1&api_sig=c9151c817e9c27a0e376191b690652f5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAPIService = RetrofitManager.getInstance().getAPI();
        photoUrl = new ArrayList<>();


        // set up the RecyclerView
         recyclerView = findViewById(R.id.rvNumbers);
//        adapter.setClickListener(this);

        // 3. 建立連線的Call，此處設置call為myAPIService中的getAlbums()連線
      call  = myAPIService.getExample();

        // 4. 執行call
      call.enqueue(new Callback<Example>() {
          @Override
          public void onResponse(Call<Example> call, Response<Example> response) {
              int numberOfColumns = 3;
//              public String getUrl(){
//                  String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
//                  return url;
//              }
              Integer perpage=response.body().getPhotos().getPerpage();
              for (int i=0;i<perpage;i++){
                  Integer farm=response.body().getPhotos().getPhoto().get(i).getFarm();
                  String server=response.body().getPhotos().getPhoto().get(i).getServer();
                  String secret=response.body().getPhotos().getPhoto().get(i).getSecret();
                  String id=response.body().getPhotos().getPhoto().get(i).getId();
                  String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
                  photoUrl.add(url);
              }


              recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
              adapter = new MyRecyclerViewAdapter(getApplicationContext(), photoUrl);
              recyclerView.setAdapter(adapter);
//              Log.d("111", "onResponse: "+url);
          }

          @Override
          public void onFailure(Call<Example> call, Throwable t) {

          }
      });

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        //设置刷新进度条的颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        );
        //设置刷新监听事件下拉刷新






}

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}
