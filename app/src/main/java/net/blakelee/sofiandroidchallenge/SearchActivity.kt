package net.blakelee.sofiandroidchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = Provider.searchViewModel
    }

    override fun onResume() {
        super.onResume()
        disposable = viewModel.results()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it) {
                    is SearchResults.Loading -> {}
                    is SearchResults.Replace -> {}
                    is SearchResults.Append -> {}
                    is SearchResults.Error -> {}
                }
            }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
