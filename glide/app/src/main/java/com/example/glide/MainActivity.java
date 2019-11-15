package com.example.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener  {
    MyAPIService myAPIService;
    private ArrayList<String> photoUrl;
    private  ArrayList<Photo> saveUrl;
    private ArrayList<String> newPhotoUrl;
    MyRecyclerViewAdapter adapter;
    private Call<Example> call;
    private Call<Example> callSearch;
    private Call<Example> callExample;
    private FragmentManager mFragmentManager;
    private DialogFragment mHomeFragment;
    private  RecyclerView recyclerView;
    private  SwipeRefreshLayout mRefreshLayout;
    public static final String HOME = "HOME";
    public PreferencesHelperImp preferencesHelperImp;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAPIService = RetrofitManager.getInstance().getAPI();
        photoUrl = new ArrayList<>();
        newPhotoUrl  = new ArrayList<>();
        saveUrl = new ArrayList<>();
        preferencesHelperImp =new PreferencesHelperImp(this);
        // set up the RecyclerView
         recyclerView = findViewById(R.id.rvNumbers);
//        adapter.setClickListener(this);

        // 3. 建立連線的Call，此處設置call為myAPIService中的getAlbums()連線
      call  = myAPIService.getNew();

        // 4. 執行call
      call.enqueue(new Callback<Example>() {
          @Override
          public void onResponse(Call<Example> call, Response<Example> response) {
              int numberOfColumns = 3;
//              public String getUrl(){
//                  String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
//                  return url;
//              }
              Example data = response.body();
                saveUrl.addAll(response.body().getPhotos().getPhoto());
              Integer perpage=response.body().getPhotos().getPerpage();

              for (int i=0;i<perpage;i++){
                  Integer farm=response.body().getPhotos().getPhoto().get(i).getFarm();
                  String server=response.body().getPhotos().getPhoto().get(i).getServer();
                  String secret=response.body().getPhotos().getPhoto().get(i).getSecret();
                  String id=response.body().getPhotos().getPhoto().get(i).getId();
                  String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
                  photoUrl.add(url);

              }
              Log.d("111", "onResponse: "+photoUrl.get(0));

              recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
              adapter = new MyRecyclerViewAdapter(getApplicationContext(), photoUrl);
              adapter.setClickListener(MainActivity.this);
              recyclerView.setAdapter(adapter);
//              Log.d("111", "onResponse: "+url);
          }

          @Override
          public void onFailure(Call<Example> call, Throwable t) {

          }
      });

        editText=findViewById(R.id.input_tag);
        button=findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag=editText.getText().toString();
                callSearch  = myAPIService.getSearch(tag);
                photoUrl.clear();
                // 4. 執行call
                callSearch.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> callSearch, Response<Example> response) {
                        saveUrl.addAll(response.body().getPhotos().getPhoto());
                        Integer perpage=response.body().getPhotos().getPerpage();

                        for (int i=0;i<perpage;i++){
                            Integer farm=response.body().getPhotos().getPhoto().get(i).getFarm();
                            String server=response.body().getPhotos().getPhoto().get(i).getServer();
                            String secret=response.body().getPhotos().getPhoto().get(i).getSecret();
                            String id=response.body().getPhotos().getPhoto().get(i).getId();
                            String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
                            photoUrl.add(url);
                        }
                        Log.d("111", "onResponse: "+photoUrl.get(0));
                        adapter.addData(photoUrl);
                    }

                    @Override
                    public void onFailure(Call<Example> callSearch, Throwable t) {
                    }
                });
            }
        });

        mRefreshLayout = findViewById(R.id.refreshlayout);
        //设置刷新进度条的颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        );
        //设置刷新监听事件下拉刷新

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
                public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callExample  = myAPIService.getExample();
                    callExample.enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {

                            int numberOfColumns = 3;
                            saveUrl.clear();
                            saveUrl.addAll(response.body().getPhotos().getPhoto());
                            Integer perpage=response.body().getPhotos().getPerpage();
                            newPhotoUrl.clear();
                            for (int i=0;i<perpage;i++){
                                Integer farm=response.body().getPhotos().getPhoto().get(i).getFarm();
                                String server=response.body().getPhotos().getPhoto().get(i).getServer();
                                String secret=response.body().getPhotos().getPhoto().get(i).getSecret();
                                String id=response.body().getPhotos().getPhoto().get(i).getId();
                                String url="https://farm" +farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
                                newPhotoUrl.add(url);
                            }
                            Log.d("222", "onResponse: "+newPhotoUrl.get(0));
//                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
//                                adapter = new MyRecyclerViewAdapter(getApplicationContext(), photoUrl);
                            adapter.addData(newPhotoUrl);
//                                recyclerView.setAdapter(adapter);
                        }
                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                        }
                    });
                    //结束刷新进度条的旋转
                    mRefreshLayout.setRefreshing(false);
                }
                },2000);
            }
        });




}

    @Override
    public void onItemClick(View view, int position) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
// Create and show the dialog.
        DialogFragment newFragment = new DialogFragment();
        newFragment.show(ft, "dialog");
        preferencesHelperImp.setId(saveUrl.get(position).getId());
        preferencesHelperImp.setFarm(saveUrl.get(position).getFarm());
        preferencesHelperImp.setTitle(saveUrl.get(position).getTitle());
        preferencesHelperImp.setUrl(photoUrl.get(position));
        Log.d("111", "onItemClick: "+preferencesHelperImp.getTitle());
    }
}
