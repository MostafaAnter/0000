package travel.com.touristesPopUpFilter

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_popup_filter.*
import travel.com.R
import travel.com.loaders.GetCategoriesAsyncTaskLoader
import travel.com.loaders.GetSubCategoriesAsyncTaskLoader
import travel.com.touristesTripsFilter.models.CategoryItem
import travel.com.utility.Constants
import travel.com.utility.Util
import java.util.*

class PopUPFilter : AppCompatActivity(), View.OnClickListener {
    var categories: MutableList<String> = mutableListOf()
    var subCategories: MutableList<String> = mutableListOf()
    var categoriesObjects: MutableList<CategoryItem> = mutableListOf()
    var subCategoriesObjects: MutableList<CategoryItem> = mutableListOf()

    var category_id: String? = null
    var subCategory_id: String? = null

    override fun onClick(p0: View?) {
        when(p0){
            button1 -> {
                finish()
            }
            categoryPicker -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("النوع الرئيسي")
                        .setSingleChoiceItems(categories.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->
                            // reset cities
                            textSubCategory.text = "النوع الفرعيي"
                            category_id = categoriesObjects[which].id.toString()
                            subCategory_id = null

                            textCategory.text = categories[which]
                            supportLoaderManager.initLoader(Random().nextInt(1000 - 10 + 1) + 10, null, getSubCategoriesLoader)
                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            subCategoryPicker -> {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("النوع الفرعي")
                        .setSingleChoiceItems(subCategories.toTypedArray(), 0, DialogInterface.OnClickListener { dialog, which ->

                            textSubCategory.text = subCategories.get(which)
                            category_id = subCategoriesObjects[which].category_id.toString()
                            subCategory_id = subCategoriesObjects[which].id.toString()

                        })
                builder.setPositiveButton("Okey") { dialog, which ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_filter)
        changeViewsFonts()
        button1.setOnClickListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        supportLoaderManager.initLoader(0, null, getCategoriesLoader)
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_up_exit, R.anim.push_up_enter)
    }

    fun changeViewsFonts(){
        Util.changeViewTypeFace(this@PopUPFilter, Constants.FONT_BOLD, text0, text1, text2,
                text3, button1)
        Util.changeViewTypeFace(this@PopUPFilter, Constants.FONT_REGULAR,
                textCategory, textSubCategory, text4, text5,
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7,
                checkbox8, checkbox9, checkbox10, checkbox11, checkbox12)
    }

    // get countries
    private val getCategoriesLoader = object : LoaderManager.LoaderCallbacks<List<CategoryItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<CategoryItem>> {
            return GetCategoriesAsyncTaskLoader(this@PopUPFilter)
        }

        override fun onLoadFinished(
                loader: Loader<List<CategoryItem>>, data: List<CategoryItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null) {
                with(categoriesObjects){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                with(categories){
                    clear()
                    data.forEach{
                        add(it.text)
                    }
                }
            }
        }

        override fun onLoaderReset(loader: Loader<List<CategoryItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

    // get cities
    private val getSubCategoriesLoader = object : LoaderManager.LoaderCallbacks<List<CategoryItem>> {
        override fun onCreateLoader(
                id: Int, args: Bundle?): Loader<List<CategoryItem>> {
            return GetSubCategoriesAsyncTaskLoader(this@PopUPFilter, category_id!!)
        }

        override fun onLoadFinished(
                loader: Loader<List<CategoryItem>>, data: List<CategoryItem>?) {
            // Display our data, for instance updating our adapter
            if (data != null && data.isNotEmpty()) {
                with(subCategoriesObjects){
                    clear()
                    data.forEach{
                        add(it)
                    }
                }
                with(subCategories){
                    clear()
                    data.forEach{
                        add(it.text)
                    }
                }
            }
        }

        override fun onLoaderReset(loader: Loader<List<CategoryItem>>) {
            // Loader reset, throw away our data,
            // unregister any listeners, etc.
            // Of course, unless you use destroyLoader(),
            // this is called when everything is already dying
            // so a completely empty onLoaderReset() is
            // totally acceptable
        }
    }

}
