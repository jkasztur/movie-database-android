package com.jkasztur.android.moviedatabase.api;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jkasztur.android.moviedatabase.model.Movie;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonDetailsDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movie movie = new Movie();
        Log.i("Test", json.toString());
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                switch(entry.getKey()) {
                    case "title":
                        movie.setTitle(cleanStrElement(entry.getValue()));
                        Log.i("Test", "title: " + movie.getTitle());
                        break;
                    case "poster_path":
                        movie.setPosterPath(cleanStrElement(entry.getValue()));
                        Log.i("Test", "posterPath: " + movie.getPosterPath());
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

