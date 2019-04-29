package net.blakelee.sofiandroidchallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_result.view.*
import net.blakelee.model.Image

class SearchAdapter(context: Context) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val items = mutableListOf<Image>()

    fun replaceItems(items: List<Image>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun appendItems(items: List<Image>) {
        this.items.addAll(items)
    }

    private val picasso: Picasso = Picasso.Builder(context).build()

    private fun getItemViewId() = R.layout.item_search_result

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(getItemViewId(), parent, false))
    }

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.image
        private val title = view.title

        fun onBind(item: Image) {
            picasso.load(item.link).into(image)
            title.text = item.title
        }
    }
}