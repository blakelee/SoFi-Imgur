package net.blakelee.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    @GET("3/gallery/search/time/{page}")
    fun loadImages(@Path("page") page: Int,
                   @Query("q") query: String): Single<Results>
}

data class Results(val data: List<ImageDetails>, val success: Boolean, val status: Int)

data class ImageDetails(val title: String, val id: String)