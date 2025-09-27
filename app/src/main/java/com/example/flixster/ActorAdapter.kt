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

class ActorAdapter(private val actors: List<Actor>) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val actorImage: ImageView = itemView.findViewById(R.id.actorImage)
        val actorName: TextView = itemView.findViewById(R.id.actorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        holder.actorName.text = actor.actorName

        Log.d("ActorAdapter", "Loading actor: ${actor.actorName}")
        Log.d("ActorAdapter", "Profile URL: ${actor.profileImageUrl}")

        Glide.with(holder.itemView.context)
            .load(actor.profileImageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
            )
            .into(holder.actorImage)
    }
}