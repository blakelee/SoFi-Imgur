package net.blakelee.sofiandroidchallenge

import net.blakelee.model.ModelProvider

object Provider {
    private lateinit var modelProvider: ModelProvider

    val searchViewModel by lazy { SearchViewModel(modelProvider.imageModel) }

    fun onCreate(modelProvider: ModelProvider) {
        this.modelProvider = modelProvider
    }
}