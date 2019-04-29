package net.blakelee.sofiandroidchallenge

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.model.ImageDetails
import net.blakelee.model.ImageModel
import java.util.concurrent.TimeUnit

class SearchViewModel(private val imageModel: ImageModel) {

    private val query = BehaviorSubject.createDefault("")
    private val page = BehaviorSubject.createDefault(0)

    private val loadDebounce: Observable<SearchResults> = query.debounce(250, TimeUnit.MILLISECONDS)
        .flatMap { query ->
            page.onNext(0)
            Observable.concat(
                Observable.just(SearchResults.Loading),
                loadImage(0, query).map(SearchResults::Replace).toObservable()
            )

        }
        .onErrorReturn { SearchResults.Error }

    private val loadNext: Observable<SearchResults> = page.flatMap { page ->
        val query = query.value
        if (page == 0 || query == null || query.count() == 0) {
            Observable.never<SearchResults>()
        } else {
            Observable.concat(
                Observable.just(SearchResults.Loading),
                loadImage(page, query).map(SearchResults::Append).toObservable()
            )
        }
    }.onErrorReturn { SearchResults.Error }


    fun results() = Observable.merge<SearchResults>(loadDebounce, loadNext)

    fun loadImages(query: String) {
        this.query.onNext(query)
    }

    fun loadNextPage() {
        val value = page.value ?: return
        page.onNext(value + 1)
    }

    private fun loadImage(page: Int, query: String) = imageModel.loadImages(page, query)
}

sealed class SearchResults {
    object Loading : SearchResults()
    class Replace(val results: List<ImageDetails>) : SearchResults()
    class Append(val results: List<ImageDetails>) : SearchResults()
    object Error : SearchResults()
}