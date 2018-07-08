package bandhook.android.com.bandhook

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_grid_item.view.*


class BandHookAdapter(val bandHookList: ArrayList<BandHookResponse>, val context: Context)
    : RecyclerView.Adapter<BandHookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bandHook = bandHookList.get(position)
        if (!bandHookList.isEmpty()) {
            holder.mHomeTextView.text = bandHook.title
            Picasso.get().load(bandHook.image).fit().centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(holder.mHomeImage)

            holder.mItemsLayout.setOnClickListener {
                val intent = Intent(context, BandHookDetailsActivity::class.java)
                intent.putExtra("BandHook",bandHook)
                context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return bandHookList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mHomeTextView = itemView.tvBandHookName
        val mHomeImage = itemView.ivBandHookImage
        val mItemsLayout = itemView.itemsLayout
    }
}