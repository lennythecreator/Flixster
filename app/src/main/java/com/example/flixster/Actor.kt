package com.example.flixster

import android.util.Log
import org.json.JSONArray

data class Actor (
    val actorId: Int,
    val actorName: String,
    val profileImageUrl: String
) {
    companion object {
        fun fromJsonArray(actorJsonArray: JSONArray): List<Actor> {
            val actors = mutableListOf<Actor>()
            for (i in 0 until actorJsonArray.length()) {
                val actorJson = actorJsonArray.getJSONObject(i)

                val profilePath = if (actorJson.isNull("profile_path")) {
                    null
                } else {
                    actorJson.getString("profile_path")
                }

                val imageUrl = if (profilePath != null && profilePath.isNotEmpty()) {
                    "http://image.tmdb.org/t/p/w185$profilePath"
                } else {
                    ""
                }

                val actor = Actor(
                    actorJson.getInt("id"),
                    actorJson.getString("name"),
                    imageUrl
                )

                Log.d("Actor", "Created actor: ${actor.actorName}")
                actors.add(actor)
            }
            return actors
        }
    }
}