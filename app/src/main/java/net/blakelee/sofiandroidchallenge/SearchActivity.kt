package net.blakelee.sofiandroidchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*

const val TITLE = "title"
const val LINK = "link"

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = Provider.searchViewModel
        adapter = SearchAdapter(this) {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(TITLE, it.title)
            intent.putExtra(LINK, it.link)
            startActivity(intent)
        }

        setupListeners()

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        disposable = viewModel.results()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it) {
                    is SearchResults.Loading -> { progress.visibility = View.VISIBLE }
                    is SearchResults.Replace -> { adapter.replaceItems(it.results) }
                    is SearchResults.Append -> { adapter.appendItems(it.results) }
                    is SearchResults.Error -> {}
                }

                if (it !is SearchResults.Loading) { progress.visibility = View.GONE }
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
            val frame = findViewById<LinearLayout>(R.id.search_edit_frame)
            (frame.layoutParams as LinearLayout.LayoutParams).apply {
                setPadding(0, 0, 0, 0)
                setMargins(0, 0, 0, 0)
            }
            findViewById<ImageView>(R.id.search_close_btn).apply {
                isEnabled = false
                setImageDrawable(null)
            }
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

    private fun setupListeners() {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recycler.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }
        })
    }
}
