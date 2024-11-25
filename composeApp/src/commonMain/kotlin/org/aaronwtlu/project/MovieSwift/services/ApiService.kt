package com.example.common.services

import com.example.common.actions.MoviesActions
import com.example.common.actions.PeopleActions
import com.example.common.getPreferredLanguage
import com.example.common.models.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.utils.CacheControl
import io.ktor.serialization.kotlinx.json.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.aaronwtlu.project.Klog
import kotlin.coroutines.CoroutineContext

// https://ktor.io/docs/eap/client-serialization.html#configure_serializer

class APIService(
    private val networkContext: CoroutineContext,
    private val uiContext: CoroutineContext
): CoroutineScope {
    val baseURL: String = "https://api.themoviedb.org/3"
    val apiKey: String = "1d9b898a212ea52e283351e521e17871"
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = networkContext + job
    val uiScope: CoroutineScope = CoroutineScope(coroutineContext)
    sealed class APIError : Error() {
        object noResponse : APIError()
        data class jsonDecodingError(val error: Error) : APIError()
        data class networkError(val error: Error) : APIError()
    }

    // TODO: sealed 是什么意思？？？
    sealed class Endpoint {
        object popular : Endpoint()
        object topRated : Endpoint()
        object upcoming : Endpoint()
        object nowPlaying : Endpoint()
        object trending : Endpoint()
        data class movieDetail(val movie: String) : Endpoint()
        data class recommended(val movie: String) : Endpoint()
        data class similar(val movie: String) : Endpoint()
        data class credits(val movie: String) : Endpoint()
        data class review(val movie: String) : Endpoint()
        object searchMovie : Endpoint()
        object searchKeyword : Endpoint()
        object searchPerson : Endpoint()
        object popularPersons : Endpoint()
        data class personDetail(val person: String) : Endpoint()
        data class personMovieCredits(val person: String) : Endpoint()
        data class personImages(val person: String) : Endpoint()
        object genres : Endpoint()
        object discover : Endpoint()


        fun path(): String = when (this) {
            popular -> "movie/popular"
            is popularPersons -> "person/popular"
            is topRated -> "movie/top_rated"
            is upcoming -> "movie/upcoming"
            is nowPlaying -> "movie/now_playing"
            is trending -> "trending/movie/day"
            is movieDetail -> "movie/$movie"
            is personDetail -> "person/$person}"
            is credits -> "movie/$movie/credits"
            is review -> "movie/$movie/reviews"
            is recommended -> "movie/$movie/recommendations"
            is similar -> "movie/$movie/similar"
            is personMovieCredits -> "person/$person/movie_credits"
            is personImages -> "person/$person/images"
            is searchMovie -> "search/movie"
            is searchKeyword -> "search/keyword"
            is searchPerson -> "search/person"
            is genres -> "genre/movie/list"
            is discover -> "discover/movie"
        }
    }

    val client by lazy {
        return@lazy try {
            HttpClient()
            HttpClient {
                install(ContentNegotiation) {
                    json()
//                    serializer = KotlinxSerializer(Json.nonstrict).apply {
//                        setMapper(ImageData::class, ImageData.serializer())
//                        setMapper(Movie.MovieImages::class, Movie.MovieImages.serializer())
//                        setMapper(Movie::class, Movie.serializer())
//                        setMapper(MovieUserMeta::class, MovieUserMeta.serializer())
//                        setMapper(CustomList::class, CustomList.serializer())
//                        setMapper(DiscoverFilter::class, DiscoverFilter.serializer())
//                        setMapper(MovieUserMeta::class, MovieUserMeta.serializer())
//                        setMapper(People::class, People.serializer())
//                        setMapper(Genre::class, Genre.serializer())
//                        setMapper(Keyword::class, Keyword.serializer())
//                        setMapper(Movie.Keywords::class, Movie.Keywords.serializer())
//                        setMapper(CastResponse::class, CastResponse.serializer())
//                        setMapper(Review::class, Review.serializer())
//                        setMapper(MoviesActions.GenresResponse::class, MoviesActions.GenresResponse.serializer())
//                        setMapper(MoviePaginatedResponse::class, MoviePaginatedResponse.serializer())
//                        setMapper(GenrePaginatedResponse::class, GenrePaginatedResponse.serializer())
//                        setMapper(PeoplePaginatedResponse::class, PeoplePaginatedResponse.serializer())
//                        setMapper(ReviewPaginatedResponse::class, ReviewPaginatedResponse.serializer())
//                        setMapper(KeywordPaginatedResponse::class, KeywordPaginatedResponse.serializer())
//                        setMapper(Movie.ProductionCountry::class, Movie.ProductionCountry.serializer())
//                        setMapper(People.KnownFor::class, People.KnownFor.serializer())
//                        setMapper(PeopleActions.PeopleCreditsResponse::class, PeopleActions.PeopleCreditsResponse.serializer())
//                        setMapper(PeopleActions.ImagesResponse::class, PeopleActions.ImagesResponse.serializer())
//
//                    }
                }
//                install(Logging) {
//                    logger = Logger.DEFAULT
//                    level = LogLevel.ALL
//                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Error initializing: ${e.message}")
        }
    }

    inline fun <reified T> GET(
        endpoint: Endpoint,
        params: Map<String, String>?,
        crossinline completionHandler: (Result<T>).() -> Unit
    ) {
        launch {
            Klog.i("BASE_URL = $baseURL")
            try {
//                urlString = "${baseURL}/${endpoint.path()}",
//                block = {
//
//                }
                val builder = HttpRequestBuilder()
                builder.apiUrl(endpoint.path())
                builder.parameter("api_key", apiKey)
                builder.parameter("language", getPreferredLanguage())
                params?.forEach {
                    builder.parameter(it.key, it.value)
                }
                val response = client.get(builder = builder).body<T>()

//                val response = client.get<T> {
//                    apiUrl(endpoint.path())
//                    parameter("api_key", apiKey)
//                    parameter("language", getPreferredLanguage())
//                    params?.forEach { parameter(it.key, it.value) }
//
//                    HydraLog.info("URL: ${url.buildString()}")
//                }
//                uiScope.launch {
                    completionHandler(Result.success(response))
//                }
            } catch (e: Exception) {
                //TODO return appropriate APIErrors
                completionHandler(Result.failure(e))
            }
        }
    }

    fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.CacheControl, CacheControl.MAX_AGE)
        url {
            takeFrom(baseURL)
            encodedPath = "/3/$path"
        }
    }
}
