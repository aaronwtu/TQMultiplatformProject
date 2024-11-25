package com.example.common.state

import com.example.common.services.APIService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.aaronwtlu.project.Klog

@Serializable
data class AppState(
    val moviesState: MoviesState = MoviesState(),
    val peoplesState: PeoplesState = PeoplesState()
) {
    fun encode(): String {
        val json = Json.encodeToString(serializer(), this)
        Klog.i(json)
        return json
    }

    fun getSaveState() = copy(moviesState = moviesState.getSaveState(),
        peoplesState = peoplesState.getSaveState())

    companion object {
        fun decode(jsonStr: String): AppState {
            Klog.i(jsonStr)
            return Json.decodeFromString(serializer(), jsonStr)
        }
        fun initialValue() = AppState()
    }
}


enum class MoviesMenu(val title: String, val endpoint: APIService.Endpoint) {
    popular("Popular", APIService.Endpoint.popular),
    topRated("Top Rated", APIService.Endpoint.topRated),
    upcoming("Upcoming", APIService.Endpoint.upcoming),
    nowPlaying("Now Playing", APIService.Endpoint.nowPlaying),
    trending("Trending", APIService.Endpoint.trending),
    genres("Genres", APIService.Endpoint.genres);

    companion object {
        fun allValues(): List<MoviesMenu> = values().toList()
        fun fromOrdinal(ordinal: Int): MoviesMenu = MoviesMenu.values()[ordinal]
    }
}
fun allMovieMenuValues() = MoviesMenu.values().toList()