package com.roundmelon.moviesapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private final static String API_KEY = "enter api key";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



//            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            ApiInterface apiService =
                    RetrofitClient.getClient().create(ApiInterface.class);

            Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    int statusCode = response.code();
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new ListAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });

           // Call<MoviesResponse> call2 = apiService.getMovieDetails(,API_KEY);
        }
    }
