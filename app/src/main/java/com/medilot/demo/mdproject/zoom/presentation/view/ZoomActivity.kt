package com.medilot.demo.mdproject.zoom.presentation.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.zoom.presentation.viewmodel.ZoomViewModel
import kotlinx.android.synthetic.main.zoom_activity.*

class ZoomActivity : AppCompatActivity() {

    private lateinit var imageLiveData: MutableLiveData<ByteArray>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zoom_activity)

        val zoomViewModel = ViewModelProviders.of(this).get(ZoomViewModel::class.java)
        imageLiveData = zoomViewModel.getImageLiveData()

        val imageId = intent.getStringExtra(Constant.IMAGE_ID)
        val byteImage = intent.getByteArrayExtra(Constant.BYTE_IMAGE)

        if (imageId != null) {
            zoomViewModel.getImage(imageId)
            observe()
        } else if (byteImage != null) {
            showPicture(byteImage)
        }

        close_btn.setOnClickListener {
            finish()
        }
    }

    private fun observe() {
        imageLiveData.observe(this, Observer<ByteArray> { image ->

            if (image != null) {
                showPicture(image)
            }

        })
    }

    private fun showPicture(image: ByteArray) {
        Glide.with(this)
            .asBitmap()
            .load(image)
            .override(300, 300)
            .fitCenter()
            .into(zoom_image_view)
    }
}