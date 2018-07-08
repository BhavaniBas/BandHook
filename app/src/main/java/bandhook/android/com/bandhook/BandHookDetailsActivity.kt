package bandhook.android.com.bandhook

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.net.HttpURLConnection
import java.net.URL


class BandHookDetailsActivity : AppCompatActivity() {

    private lateinit var bandHook: BandHookResponse
    private lateinit var mPriceText: TextView
    private lateinit var bandHookImageUrl: ImageView
    private lateinit var collapsingToolBar: CollapsingToolbarLayout
    private lateinit var mBackArrow: ImageView
    private lateinit var linkName: TextView
    private lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_band_hook_details)

        mBackArrow = findViewById(R.id.backArrow)
        mPriceText = findViewById(R.id.priceValue)
        linkName = findViewById(R.id.linkName)
        bandHookImageUrl = findViewById(R.id.bandHookImageUrl)
        collapsingToolBar = findViewById(R.id.collapsing_toolbar)

        mBackArrow.setOnClickListener {
            finish()
        }

        bandHook = intent.getParcelableExtra("BandHook")
        if (true) {
            mPriceText.text = bandHook.price
            Picasso.get().load(bandHook.image).fit().centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(bandHookImageUrl)
            collapsingToolBar.title = bandHook.title
        }
        collapsingToolBar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        collapsingToolBar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)

        // Spannable String
        val content = SpannableString(getString(R.string.link_url))
        content.setSpan(UnderlineSpan(), 0, linkName.length(), 0)
        linkName.text = content

        linkName.setOnClickListener {
            url = getString(R.string.link_url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // WhatsApp View Info Collapsing tool bar color changed - Image color
        val thread = Thread(Runnable {
            try {
                ToolbarColor()//this function will help to determine the dominant colour of the image and set that colour to the toolbar and status bar.
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()//since thetoolbarcolour function has network call it has to run with the thread.
    }

    @SuppressLint("ObsoleteSdkInt")
    fun ToolbarColor() {
        try {
            val url = URL(bandHook.image)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
            Palette.from(bitmap).generate { palette ->
                val dominant = palette.dominantSwatch
                collapsingToolBar.setContentScrimColor(dominant!!.rgb)
                collapsingToolBar.setStatusBarScrimColor(dominant.rgb)
                if (Build.VERSION.SDK_INT >= 21) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = dominant.rgb
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

