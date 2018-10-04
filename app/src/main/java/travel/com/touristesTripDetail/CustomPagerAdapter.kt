package travel.com.touristesTripDetail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import travel.com.R
import travel.com.utility.SlideshowDialogFragment
import java.util.*

// Custom pager adapter not using fragments
internal class CustomPagerAdapter(var mContext: Context, val pages: ArrayList<String>) : PagerAdapter() {
    var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    // Returns the number of pages to be displayed in the ViewPager.
    override fun getCount(): Int {
        return pages.size
    }

    // Returns true if a particular object (page) is from a particular page
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    // This method should create the page for the given position passed to it as an argument.
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // Inflate the layout for the page
        val itemView = mLayoutInflater.inflate(R.layout.viewholder_image_card, container, false)
        // Find and populate data into the page (i.e set the image)
        Glide.with(mContext).load(pages[position])
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.findViewById(R.id.card_image_view))
        // ...
        // Add the page to the container
        container.addView(itemView)
        // set on item click
        itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("images", ArrayList<String>(pages))
            bundle.putInt("position", position)
            val ft = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
            val newFragment = SlideshowDialogFragment.newInstance()
            newFragment.arguments = bundle
            newFragment.show(ft, "slideshow")
        }
        // Return the page
        return itemView
    }

    // Removes the page from the container for the given position.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}