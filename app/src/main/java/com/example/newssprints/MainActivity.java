package com.example.newssprints;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newssprints.api.ApiClient;
import com.example.newssprints.api.ApiInterface;
import com.example.newssprints.models.Article;
import com.example.newssprints.models.News;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
public static final String API_KEy="755b109270ce4f81a02385cb7d82482e";
    private RecyclerView recyclerView;
    private RecyclerView. LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter ;
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();
    }
        public void LoadJson(){
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            String country=Utils.getCountry();
            Call<News> call;
            call=apiInterface.getNews(country,API_KEy);
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if(response.isSuccessful() && response.body().getArticle() !=null){
                        if(!articles.isEmpty()){
                            articles.clear();
                        }
                        articles=response.body().getArticle();
                        adapter=new Adapter(articles,MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Result!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }


}