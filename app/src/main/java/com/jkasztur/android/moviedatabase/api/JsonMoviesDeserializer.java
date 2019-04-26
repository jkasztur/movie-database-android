package com.jkasztur.android.moviedatabase.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jkasztur.android.moviedatabase.model.Movie;
import com.jkasztur.android.moviedatabase.model.Movies;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonMoviesDeserializer implements JsonDeserializer<Movies> {
    @Override
    public Movies deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Movies movies = new Movies();
        if (json.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                if (entry.getKey().equals("results")) {
                    JsonArray jsonArray = entry.getValue().getAsJsonArray();
                    for(JsonElement elem: jsonArray) {
                        JsonObject movieResult = elem.getAsJsonObject();
                        if(movieResult.get("adult").toString().equals("true")) {
                            continue;
                        }
                        Movie movie = new Movie();
                        movie.setId(movieResult.get("id").toString());
                        movies.addMovie(movie);
                    }
                }

            }
        }
        return movies;
    }
}
