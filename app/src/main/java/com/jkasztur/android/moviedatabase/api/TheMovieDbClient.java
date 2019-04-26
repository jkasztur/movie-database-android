package com.jkasztur.android.moviedatabase.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.model.Movies;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbClient {

    private static TheMovieDbApi api;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static TheMovieDbApi getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                            Movies.class,
                            new JsonMoviesDeserializer())
                    .registerTypeAdapter(
                            Movie.class,
                            new JsonDetailsDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            api = retrofit.create(TheMovieDbApi.class);
        }
        return api;
    }
}
