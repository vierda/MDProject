package com.medilot.demo.mdproject.approval.presentation.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.approval.presentation.viewmodel.ApprovalViewModel
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.zoom.presentation.view.ZoomActivity
import kotlinx.android.synthetic.main.approval_activity.*

class ApprovalActivity : AppCompatActivity() {

    private lateinit var imageLiveData: LiveData<Image>
    private lateinit var reviewLiveData: LiveData<Int>
    private var byteImage : ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.approval_activity)

        val imageId = intent.getStringExtra(Constant.IMAGE_ID)

        val approvalViewModel = ViewModelProviders.of(this).get(ApprovalViewModel::class.java)
        imageLiveData = approvalViewModel.getImageLiveData()

        approvalViewModel.getImage(imageId)
        observeImage()

        reviewLiveData = approvalViewModel.getApprovalLiveData()

        approve_btn.setOnClickListener {
            approvalViewModel.saveReview(imageId, Constant.APPROVED, comment_edit_text.text.toString())
            observeReview()
        }

        reject_btn.setOnClickListener {
            approvalViewModel.saveReview(imageId, Constant.REJECTED, comment_edit_text.text.toString())
            observeReview()
        }

        uploaded_image_view.setOnClickListener {
            if (byteImage!=null) {
                val intent = Intent(baseContext, ZoomActivity::class.java)
                intent.putExtra(Constant.BYTE_IMAGE,byteImage)
                startActivity(intent)
            }
        }
    }

    private fun observeImage() {
        imageLiveData.observe(this, Observer<Image> { image ->

            byteImage = image?.imageData

            if (image?.imageData!=null){
                Glide.with(this)
                    .asBitmap()
                    .load(image.imageData)
                    .override(300, 300)
                    .fitCenter()
                    .into(uploaded_image_view)
            }

        })
    }

    private fun observeReview() {
        reviewLiveData.observe(this, Observer<Int> {

            when (it) {
                Constant.SUCCESS_SUBMIT_REVIEW -> toast(getString(R.string.submit_review_success))
                Constant.ERROR_ENOUGH_REVIEWER -> toast(getString(R.string.error_reviewed_by_two_reviewers))
                Constant.ERROR_SUBMIT_REVIEW -> toast(getString(R.string.error_submit_review))
                Constant.ERROR_SAME_REVIEWER -> toast(getString(R.string.error_same_reviewer))
                Constant.ERROR_REJECT_WITHOUT_COMMENT -> toast(getString(R.string.error_rejecting_without_comment))
            }
        })
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}