package com.medilot.demo.mdproject.main.presentation.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.gallery.presentation.view.GalleryActivity
import com.medilot.demo.mdproject.review.presentation.view.ReviewActivity
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        when (Constant.CURRENT_ROLE) {
            Constant.ADMIN -> {
                review_btn.visibility = View.VISIBLE
                send_image_btn.visibility = View.GONE
            }

            Constant.USER -> {
                review_btn.visibility = View.VISIBLE
                send_image_btn.visibility = View.VISIBLE
            }
        }

        review_btn.setOnClickListener {
            val intent = Intent(baseContext, GalleryActivity::class.java)
            startActivity(intent)
        }

        send_image_btn.setOnClickListener {
            val intent = Intent(baseContext, ReviewActivity::class.java)
            startActivity(intent)
        }


    }
}