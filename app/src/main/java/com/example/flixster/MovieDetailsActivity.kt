package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var posterImageView: ImageView
    private lateinit var movieTitleTextView: TextView
    private lateinit var releaseDateTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var movieDescTextView: TextView
    private lateinit var actorsRecyclerView: RecyclerView
    private lateinit var actorAdapter: ActorAdapter
    private val actors = mutableListOf<Actor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        // Initialize views
        posterImageView = findViewById(R.id.posterImageView)
        movieTitleTextView = findViewById(R.id.movieTitleTextView)
        releaseDateTextView = findViewById(R.id.releaseDateTextView)
        ratingTextView = findViewById(R.id.ratingTextView)
        movieDescTextView = findViewById(R.id.movieDescTextView)
        actorsRecyclerView = findViewById(R.id.actorsRecyclerView)

        // Get movie from intent
        val movie = intent.getParcelableExtra<Movie>("MOVIE_EXTRA")

        movie?.let {
            displayMovieDetails(it)
            loadActors(it.movieId)
        }

        // Setup actors RecyclerView
        actorsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actorAdapter = ActorAdapter(actors)
        actorsRecyclerView.adapter = actorAdapter
    }

    private fun displayMovieDetails(movie: Movie) {
        movieTitleTextView.text = movie.movieName
        releaseDateTextView.text = movie.releaseDate
        ratingTextView.text = "${String.format("%.1f", movie.voteAverage)}/10"
        movieDescTextView.text = movie.movieDesc

        // Load poster image
        Glide.with(this)
            .load(movie.movieImageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
            )
            .into(posterImageView)
    }

    private fun loadActors(movieId: Int) {
        val client = AsyncHttpClient()
        val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
        val url = "https://api.themoviedb.org/3/movie/$movieId/credits?api_key=$apiKey"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("MovieDetailsActivity", "Failed to load actors", throwable)
                throwable?.printStackTrace()
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val castJsonArray = json.jsonObject.getJSONArray("cast")

                // Clear existing actors and add new ones (limit to first 10 for UI purposes)
                actors.clear()
                val actorList = Actor.fromJsonArray(castJsonArray)
                actors.addAll(actorList.take(10))

                runOnUiThread {
                    actorAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}