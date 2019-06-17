package com.medilot.demo.mdproject.gallery.presentation.view


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.detail.presentation.view.DetailActivity
import com.medilot.demo.mdproject.gallery.presentation.adapter.GalleryAdapter
import com.medilot.demo.mdproject.gallery.presentation.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.gallery_activity.*

class GalleryActivity : AppCompatActivity() {

    private val images = ArrayList<Image>()
    private lateinit var adapter: GalleryAdapter
    private lateinit var galleryLiveData: LiveData<List<Image>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)

        toolbar_text.text = this.getText(R.string.gallery)

        adapter = GalleryAdapter(this, images)
        val displayMetrics = this.resources
            .displayMetrics
        val screenWidth = displayMetrics.widthPixels.toFloat()
        val columnWidth = (screenWidth / 2).toInt()
        gridView.columnWidth = columnWidth
        gridView.verticalSpacing = 2
        gridView.horizontalSpacing = 2
        gridView.adapter = adapter

        val galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        galleryLiveData = galleryViewModel.getGalleryLiveData()

        galleryViewModel.getAllImages()
        observe()

        gridView.setOnItemClickListener { adapterView, view, position, id ->
            val image = adapter.getItem(position) as Image
            val intent = Intent(baseContext, DetailActivity::class.java)
            intent.putExtra(Constant.IMAGE_ID, image.imageId)
            startActivity(intent)
        }

    }


    private fun observe() {
        galleryLiveData.observe(this, Observer <List<Image>>{t ->
            if (t!=null && t.isNotEmpty()) {
                images.clear()
                images.addAll(t)
                adapter.notifyDataSetChanged()
            }
        })
    }


}