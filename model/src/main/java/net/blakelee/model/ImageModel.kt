package net.blakelee.model

import io.reactivex.Single

interface ImageModel {
    fun loadImages(page: Int, query: String): Single<List<ImageDetails>>
}