package com.jkasztur.android.moviedatabase.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jkasztur.android.moviedatabase.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.movito.themoviedbapi.model.Language;

public class JsonDetailsDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movie movie = new Movie();
        Log.i("Test", json.toString());
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                switch(entry.getKey()) {
                    case "id":
                        movie.setId(cleanStrElement(entry.getValue()));
                        Log.d("DetailsDeserializer", "id: " + movie.getId());
                        break;
                    case "title":
                        movie.setTitle(cleanStrElement(entry.getValue()));
                        Log.i("DetailsDeserializer", "title: " + movie.getTitle());
                        break;
                    case "poster_path":
                        movie.setPosterPath(cleanStrElement(entry.getValue()));
                        Log.d("DetailsDeserializer", "posterPath: " + movie.getPosterPath());
                        break;
                    case "genres":
                        JsonArray jsonArray = entry.getValue().getAsJsonArray();
                        List<String> genres = new ArrayList<>();
                        for(JsonElement elem: jsonArray) {
                            JsonObject genre = elem.getAsJsonObject();
                            genres.add(cleanStrElement(genre.get("name")));
                        }
                        movie.setGenres(genres);
                        Log.d("DetailsDeserializer", "genres: " + movie.getGenres().toString());
                        break;
                    case "overview":
                        movie.setOverview(cleanStrElement(entry.getValue()));
                        Log.d("DetailsDeserializer", "overview: " + movie.getOverview());
                        break;
                    case "original_language":
                        String langCode = cleanStrElement(entry.getValue());
                        movie.setLanguage(new Locale(langCode).getDisplayLanguage(Locale.getDefault()));
                        Log.d("DetailsDeserializer", "language: " + movie.getLanguage());
                        break;
                    case "release_date":
                        movie.setReleaseDate(cleanStrElement(entry.getValue()));
                        Log.d("DetailsDeserializer", "release date: " + movie.getLanguage());
                        break;
                }
            }
        }
        return movie;
    }

    private String cleanStrElement(JsonElement elem) {
        if(elem == null || "null".equals(elem.toString())) {
            return null;
        }
        return elem.toString().replace("\"", "");
    }
}

