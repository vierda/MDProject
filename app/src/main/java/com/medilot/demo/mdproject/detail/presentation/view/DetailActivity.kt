package com.medilot.demo.mdproject.detail.presentation.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.approval.presentation.view.ApprovalActivity
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.detail.presentation.entity.DetailEntity
import com.medilot.demo.mdproject.detail.presentation.viewmodel.DetailViewModel
import com.medilot.demo.mdproject.zoom.presentation.view.ZoomActivity
import kotlinx.android.synthetic.main.image_detail_activity.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailLiveData: LiveData<DetailEntity>
    private lateinit var detailViewModel: DetailViewModel
    private var imageId = ""
    private var byteImage: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_detail_activity)

        if (Constant.CURRENT_ROLE == Constant.USER)
            give_review_btn.visibility = View.GONE
        else
            give_review_btn.visibility = View.VISIBLE

        imageId = intent.getStringExtra(Constant.IMAGE_ID)

        give_review_btn.setOnClickListener {
            val intent = Intent(baseContext, ApprovalActivity::class.java)
            intent.putExtra(Constant.IMAGE_ID, imageId)
            startActivity(intent)
        }

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        detailLiveData = detailViewModel.getDetailLiveData()

        detailViewModel.getDetailEntity(imageId)
        observe()

        uploaded_image.setOnClickListener {
            if (byteImage != null) {
                val intent = Intent(baseContext, ZoomActivity::class.java)
                intent.putExtra(Constant.BYTE_IMAGE, byteImage)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (imageId.isNotEmpty() && imageId.isNotBlank()) {
            detailViewModel.getDetailEntity(imageId)
            observe()
        }
    }

    private fun observe() {
        detailLiveData.observe(this, Observer<DetailEntity> { entity ->

            byteImage = entity?.imageData

            //image
            Glide.with(this)
                .asBitmap()
                .load(entity?.imageData)
                .override(300, 300)
                .fitCenter()
                .into(uploaded_image)

            //get reviewer one
            if (entity?.reviewEntityOne != null) {
                val reviewOne = entity.reviewEntityOne
                comment.text = reviewOne?.comments
                reviewer.text = reviewOne?.status
                timestamp.text = reviewOne?.timestamp
            }

            //get reviewer two
            if (entity?.reviewEntityTwo != null) {
                val reviewerTwo = entity.reviewEntityTwo
                comment_two.text = reviewerTwo?.comments
                reviewer_two.text = reviewerTwo?.status
                timestamp_two.text = reviewerTwo?.timestamp
            }

            //get status
            banner_status.text = entity?.statusBanner?.status
            banner_status.setBackgroundColor(getColor(entity?.statusBanner?.colorCode!!))

        })
    }
}