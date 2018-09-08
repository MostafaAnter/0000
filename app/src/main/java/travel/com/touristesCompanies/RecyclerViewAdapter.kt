package travel.com.touristesCompanies

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_companies_list.view.*
import travel.com.R
import travel.com.touristesTripResults.models.Member
import travel.com.utility.CircleTransform
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.*


/**
 * A custom adapter to use with the RecyclerView widget.
 */
class RecyclerViewAdapter(private val mContext: Context, private var modelList: ArrayList<Member>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<Member>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_companies_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val model = getItem(position)
            holder.bindCompany(model)
        }
    }


    override fun getItemCount(): Int {

        return modelList!!.size
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    private fun getItem(position: Int): Member {
        return modelList!![position]
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, model: Member)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener { mItemClickListener!!.onItemClick(itemView, adapterPosition, modelList!![adapterPosition]) }

        }

        fun bindCompany(model: Member?){
            with(model){
                itemView.item_txt_title.text = this?.name
                itemView.item_txt_message.text = this?.email

                itemView.ratingBar.rating = this?.stars!!.toFloat()
                itemView.ratingValue.text = this.stars.toFloat().toString()

                Glide.with(mContext)   // pass Context
                        .load(this?.image) // add your image url
                        .transform(CircleTransform(mContext)) // applying the image transformer
                        .into(itemView.img_user)

                // change font
                Util.changeViewTypeFace(mContext, Constants.FONT_REGULAR, itemView.item_txt_message,
                        itemView.button1)
                Util.changeViewTypeFace(mContext, Constants.FONT_BOLD,
                        itemView.item_txt_title)
            }
        }
    }

}

