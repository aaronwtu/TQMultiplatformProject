package com.example.common.models

import com.example.common.preferences.AppUserDefaults
import korlibs.time.DateFormat
import korlibs.time.DateTimeTz
import korlibs.time.parse
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.aaronwtlu.project.createUUID


@Serializable
data class Movie(
    val id: String,
    val original_title: String,
    val title: String,
    val overview: String,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val popularity: Float,
    val vote_average: Float,
    val vote_count: Int,
    val release_date: String? = null,
    val genres: List<Genre>? = null,
    val runtime: Int? = null,
    val status: String? = null,
    var keywords: Keywords? = null,
    var images: MovieImages? = null,
    var production_countries: List<ProductionCountry>? = null,
    var character: String? = null,
    var department: String? = null
) {
    private val dateFormatter: DateFormat by lazy { DateFormat("yyyy-MM-dd") }
    private val displayDateFormat = "MMM dd, YYYY"

    fun userTitle(appUserDefaults: AppUserDefaults): String =
        if (appUserDefaults.alwaysOriginalTitle) original_title else title

    //Nov 23, 1937
    val releaseDate: String
        get() = if (release_date != null) dateFormatter.parse(release_date).format(displayDateFormat) else DateTimeTz.nowLocal().format(displayDateFormat)

    @Serializable
    data class Keywords(val keywords: List<Keyword>? = null)

    @Serializable
    data class MovieImages(
        val posters: List<ImageData>? = null,
        val backdrops: List<ImageData>? = null
    )

    @Serializable
    data class ProductionCountry(
        @Transient
        val id: String = createUUID(),
        val name: String
    )
}

val sampleMovie = Movie(
    id = "0",
    original_title = "Test movie Test movie Test movie Test movie Test movie Test movie Test movie ",
    title = "Test movie Test movie Test movie Test movie Test movie Test movie Test movie  Test movie Test movie Test movie",
    overview = "Test desc",
    poster_path = "/uC6TTUhPpQCmgldGyYveKRAu8JN.jpg",
    backdrop_path = "/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg",
    popularity = 50.5f,
    vote_average = 8.9f,
    vote_count = 1000,
    release_date = "1972-03-14",
    genres = listOf(Genre(id = "0", name = "test")),
    runtime = 80,
    status = "released"
)