package net.blakelee.sofiandroidchallenge.providers

import net.blakelee.model.business.ImageModel
import net.blakelee.model.business.ImpImageModel
import net.blakelee.model.business.ModelProvider
import net.blakelee.model.services.client.RetrofitClient

class AppModelProvider(client: RetrofitClient) : ModelProvider {

    override val imageModel: ImageModel =
        ImpImageModel(client.searchService)
}