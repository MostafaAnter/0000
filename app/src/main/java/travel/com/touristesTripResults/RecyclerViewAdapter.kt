package travel.com.touristesTripResults

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler_list.view.*
import travel.com.R
import travel.com.touristesTripResults.models.DataItem
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.Util


/**
 * A custom adapter to use with the RecyclerView widget.
 */
class RecyclerViewAdapter(private val mContext: Context, private var modelList: ArrayList<DataItem>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<DataItem>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recycler_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //Here you can fill your row view
        if (holder is ViewHolder) {
            val model = getItem(position)
            holder.bindTrip(model)

        }
    }


    override fun getItemCount(): Int {

        return modelList!!.size
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    private fun getItem(position: Int): DataItem {
        return modelList!![position]
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, model: DataItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener { mItemClickListener!!.onItemClick(itemView, adapterPosition, modelList!![adapterPosition]) }

        }

        fun bindTrip(tripItemModel: DataItem?) {
            with(tripItemModel) {
                itemView.item_txt_title.text = this?.title

                // todo difference between start date and end date
                itemView.days_numbers.text = "4 أيام / 3 ليالي"

                itemView.ratingBar.rating = this?.stars!!.toFloat()

                Glide.with(mContext)   // pass Context
                        .load(image) // add your image url
                        .transform(CircleTransform(mContext)) // applying the image transformer
                        .into(itemView.img_user)

                // change font
                Util.changeViewTypeFace(mContext, Constants.FONT_REGULAR, itemView.days_numbers,
                        itemView.button1)
                Util.changeViewTypeFace(mContext, Constants.FONT_BOLD,
                        itemView.item_txt_title)

            }
        }
    }

}

