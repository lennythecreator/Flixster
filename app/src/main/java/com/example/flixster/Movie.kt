package com.example.flixster

import android.util.Log
import org.json.JSONArray

class Movie (
    val movieName: String,
    val movieDesc: String,
    val movieImageUrl: String,
){
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)

                val posterPath = if (movieJson.isNull("poster_path")) {
                    null
                } else {
                    movieJson.getString("poster_path")
                }

                val imageUrl = if (posterPath != null && posterPath.isNotEmpty()) {
                    "http://image.tmdb.org/t/p/w500$posterPath"
                } else {
                    ""
                }

                val movie = Movie(
                    movieJson.getString("title"),
                    movieJson.getString("overview"),
                    imageUrl
                )

                // log data
                Log.d("Movie", "Created movie: ${movie.movieName}")
                Log.d("Movie", "Poster path: $posterPath")
                Log.d("Movie", "Full URL: ${movie.movieImageUrl}")

                movies.add(movie)
            }
            return movies
        }
    }
}