package travel.com.myBookings

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_bookings_list.view.*
import travel.com.R
import travel.com.myBookings.models.DataItem
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.*


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

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_bookings_list, viewGroup, false)

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

        fun bindTrip(myBookingsModel: DataItem?) {
            with(myBookingsModel) {
                itemView.item_txt_title.text = this?.trip?.title

                // todo difference between start date and end date
                val dayNum = Util.printDifference(Util.conVertDateTextToObject(this?.trip?.start_date?: ""),
                        Util.conVertDateTextToObject(this?.trip?.end_date?: ""))
                itemView.item_txt_message.text = "$dayNum" + " أيام" + " / " + "${dayNum - 1}" + " ليالى"

                itemView.ratingBar.rating = this?.trip?.stars!!.toFloat()
                itemView.ratingValue.text = this.trip.stars.toFloat().toString()

                itemView.item_txt_booking_num.text = "  رقم الرحلة " + trip_id



                Glide.with(mContext)   // pass Context
                        .load(trip.image) // add your image url
                        .transform(CircleTransform(mContext)) // applying the image transformer
                        .into(itemView.img_user)

                // change font
                Util.changeViewTypeFace(mContext, Constants.FONT_REGULAR, itemView.item_txt_message,
                        itemView.button1, itemView.item_txt_booking_num)
                Util.changeViewTypeFace(mContext, Constants.FONT_BOLD,
                        itemView.item_txt_title)

            }
        }
    }

}

