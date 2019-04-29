package net.blakelee.model.business

import io.reactivex.Single
import net.blakelee.model.services.Image

interface ImageModel {
    fun loadImages(page: Int, query: String): Single<List<Image>>
}