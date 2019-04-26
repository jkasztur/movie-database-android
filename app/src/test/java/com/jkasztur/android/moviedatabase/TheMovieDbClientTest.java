package com.jkasztur.android.moviedatabase;

import com.jkasztur.android.moviedatabase.api.TheMovieDbClient;
import com.jkasztur.android.moviedatabase.model.Movies;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TheMovieDbClientTest {

    @Test
    public void testClient() throws IOException {
        Response<Movies> response = TheMovieDbClient.getApi().getMovies("7b6d38875404b0b7a8c7b8dbbe72e16c").execute();
        assertNotNull(response);
        assertEquals(200, response.code());
    }
}
