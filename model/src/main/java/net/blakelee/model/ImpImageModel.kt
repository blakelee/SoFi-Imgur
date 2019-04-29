package net.blakelee.model

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class ImpImageModel(private val service: SearchService) : ImageModel {

    override fun loadImages(page: Int, query: String): Single<List<ImageDetails>> {
        return service.loadImages(page, query)
            .flatMap {
                if (it.success) {
                    Single.just(it.data)
                } else {
                    val response = Response.error<Exception>(it.status, ResponseBody.create(null, "Unknown error"))
                    Single.error(HttpException(response))
                }
            }
    }
}