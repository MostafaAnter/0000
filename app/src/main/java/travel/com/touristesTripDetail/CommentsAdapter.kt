package travel.com.touristesTripDetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler_list.view.*
import travel.com.R
import travel.com.utility.CircleTransform
import java.util.*
import com.bumptech.glide.load.resource.bitmap.FitCenter
import travel.com.utility.Constants
import travel.com.utility.Util


/**
 * A custom adapter to use with the RecyclerView widget.
 */
class CommentsAdapter(private val mContext: Context, private var modelList: ArrayList<CommentsModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<CommentsModel>) {
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

    private fun getItem(position: Int): CommentsModel {
        return modelList!![position]
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, model: CommentsModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener { mItemClickListener!!.onItemClick(itemView, adapterPosition, modelList!![adapterPosition]) }

        }

        fun bindTrip(commentsModel: CommentsModel?) {
            with(commentsModel) {

            }
        }
    }

}

