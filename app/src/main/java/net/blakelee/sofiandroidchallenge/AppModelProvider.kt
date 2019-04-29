package net.blakelee.sofiandroidchallenge

import net.blakelee.model.ImageModel
import net.blakelee.model.ImpImageModel
import net.blakelee.model.ModelProvider
import net.blakelee.model.RetrofitClient

class AppModelProvider(client: RetrofitClient) : ModelProvider {

    override val imageModel: ImageModel = ImpImageModel(client.searchService)
}