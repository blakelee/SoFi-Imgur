package net.blakelee.sofiandroidchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*
import net.blakelee.sofiandroidchallenge.R

class ImageActivity : AppCompatActivity() {

    private val picasso: Picasso by lazy { Picasso.Builder(this).build() }
    private lateinit var title: String
    private lateinit var link: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_image)

        title = intent.getStringExtra(TITLE)
        link = intent.getStringExtra(LINK)

        picasso.load(link).into(image)
        findViewById<TextView>(R.id.title).text = title

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(TITLE, title)
        outState?.putString(LINK, link)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        title = savedInstanceState?.getString(TITLE, "") ?: ""
        link = savedInstanceState?.getString(LINK, "") ?: ""
        super.onRestoreInstanceState(savedInstanceState)
    }
}
