package com.medilot.demo.mdproject.review.presentation.view

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.gallery.presentation.view.GalleryActivity
import com.medilot.demo.mdproject.review.presentation.viewmodel.ReviewViewModel
import com.medilot.demo.mdproject.zoom.presentation.view.ZoomActivity
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.image_review_activity.*
import java.io.ByteArrayOutputStream
import java.util.*


class ReviewActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 1011
    private val CAMERA_REQUEST = 1012
    private val CONTENT_MEDIA_PATH = "content://media"
    private val MEDIA_TYPE = "image/*"
    private val DATA_TYPE = "data"

    private var byteImages : ByteArray?=null
    private lateinit var reviewLiveData: MutableLiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_review_activity)

        toolbar_text.text = this.getText(R.string.send_image)

        val reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel::class.java)
        reviewLiveData = reviewViewModel.getReviewLiveData()

        image_review.setOnClickListener {
            if (byteImages!=null) {
                val intent = Intent(baseContext, ZoomActivity::class.java)
                intent.putExtra(Constant.BYTE_IMAGE,byteImages)
                startActivity(intent)
            }
        }

        review_gallery_btn.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = MEDIA_TYPE
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        capture_btn.setOnClickListener {
            val photoCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(photoCaptureIntent, CAMERA_REQUEST)
        }

        send_btn.setOnClickListener {

                if (byteImages!=null) {
                    val image = Image()
                    image.imageId = UUID.randomUUID().toString()
                    image.imageData = byteImages

                    reviewViewModel.saveImage(image)
                    observe()
                } else {
                    Toast.makeText(this,
                        getString(R.string.pick_or_take_photo), Toast.LENGTH_SHORT).show()
                }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

           lateinit var bitmap: Bitmap

            when (requestCode) {
                GALLERY_REQUEST -> {
                    val path = data!!.data.path
                    val uri = Uri.parse(CONTENT_MEDIA_PATH + path)
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                }

                CAMERA_REQUEST -> {
                    bitmap = data!!.extras.get(DATA_TYPE) as Bitmap
                }
            }

            byteImages = convertBitmapToByteArray(bitmap)
            Glide.with(this)
                .asBitmap()
                .load(byteImages)
                .override(300, 300)
                .fitCenter()
                .into(image_review)

        }
    }


    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap,400,400,false)
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    private fun observe() {
        reviewLiveData.observe(this, Observer<Boolean?> { t ->
            if (t!!) {

                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.send_image))
                builder.setMessage(getString(R.string.success_send_approval))
                builder.setPositiveButton(getString(R.string.close)){dialog, which ->
                    dialog.dismiss()
                    val intent = Intent(baseContext, GalleryActivity::class.java)
                    startActivity(intent)
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()


            } else {
                val message = getString(R.string.error_send_approval)
                toast(message)

            }
        })
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}