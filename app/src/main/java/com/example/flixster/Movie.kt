package com.example.flixster

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie (
    val movieId: Int,
    val movieName: String,
    val movieDesc: String,
    val movieImageUrl: String,
    val releaseDate: String,
    val voteAverage: Double
) : Parcelable {
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
                    movieJson.getInt("id"),
                    movieJson.getString("title"),
                    movieJson.getString("overview"),
                    imageUrl,
                    movieJson.getString("release_date"),
                    movieJson.getDouble("vote_average")
                )

                // log data
                Log.d("Movie", "Created movie: ${movie.movieName}")
                Log.d("Movie", "Poster path: $posterPath")
                Log.d("Movie", "Full URL: ${movie.movieImageUrl}")
                Log.d("Movie", "Release date: ${movie.releaseDate}")
                Log.d("Movie", "Vote average: ${movie.voteAverage}")

                movies.add(movie)
            }
            return movies
        }
    }
}