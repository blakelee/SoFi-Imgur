package net.blakelee.sofiandroidchallenge.providers

import net.blakelee.model.business.ModelProvider
import net.blakelee.sofiandroidchallenge.viewmodels.SearchViewModel

object Provider {
    private lateinit var modelProvider: ModelProvider

    val searchViewModel by lazy { SearchViewModel(modelProvider.imageModel) }

    fun onCreate(modelProvider: ModelProvider) {
        Provider.modelProvider = modelProvider
    }
}