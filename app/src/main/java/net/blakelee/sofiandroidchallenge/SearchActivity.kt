package net.blakelee.sofiandroidchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = Provider.searchViewModel
        adapter = SearchAdapter(this)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        disposable = viewModel.results()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it) {
                    is SearchResults.Loading -> {}
                    is SearchResults.Replace -> { adapter.replaceItems(it.results) }
                    is SearchResults.Append -> { adapter.appendItems(it.results) }
                    is SearchResults.Error -> {}
                }
            }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.loadImages(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.loadImages(query)
                    return true
                }
            })
        }

        return super.onPrepareOptionsMenu(menu)
    }
}
