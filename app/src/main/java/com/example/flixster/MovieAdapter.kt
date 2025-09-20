package com.example.flixster
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieDesc: TextView = itemView.findViewById(R.id.movieDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.movieName
        holder.movieDesc.text = movie.movieDesc

        // Debug logging
        Log.d("MovieAdapter", "Loading movie: ${movie.movieName}")
        Log.d("MovieAdapter", "Image URL: ${movie.movieImageUrl}")

        // Simple Glide configuration that should work
        Glide.with(holder.itemView.context)
            .load(movie.movieImageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
            )
            .into(holder.movieImage)
    }
}