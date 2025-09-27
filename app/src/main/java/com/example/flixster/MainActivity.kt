package com.example.flixster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(movies)
        recyclerView.adapter = movieAdapter

        getMovies()
        println(getMovies())

    }
    fun getMovies(){
        val client = AsyncHttpClient()
        val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
        val url = "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1&api_key=$apiKey"

        client.get(url, object:JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                // TODO: Handle failure gracefully
                throwable?.printStackTrace()
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val movieJsonArray = json.jsonObject.getJSONArray("results")
                movies.addAll(Movie.fromJsonArray(movieJsonArray))
                movieAdapter.notifyDataSetChanged()
            }
        })
    }
}