package travel.com.touristesTripDetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import travel.com.R
import java.util.*


/**
 * A custom adapter to use with the RecyclerView widget.
 */
class ServicesAvilableAdapter(private val mContext: Context, private var modelList: ArrayList<ServisesModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    fun updateList(modelList: ArrayList<ServisesModel>) {
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
            holder.bindComent(model)

        }
    }


    override fun getItemCount(): Int {

        return modelList!!.size
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    private fun getItem(position: Int): ServisesModel {
        return modelList!![position]
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, model: ServisesModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener { mItemClickListener!!.onItemClick(itemView, adapterPosition, modelList!![adapterPosition]) }

        }

        fun bindComent(servisesModel: ServisesModel?) {
            with(servisesModel) {

            }
        }
    }

}

