package com.medilot.demo.mdproject.detail.data

import android.content.Context
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.data.database.AppDatabase
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.data.model.Reviewer
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.detail.presentation.entity.DetailEntity
import com.medilot.demo.mdproject.detail.presentation.entity.ReviewEntity
import com.medilot.demo.mdproject.detail.presentation.entity.StatusBannerEntity
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class DetailDatabaseRepository(val database: AppDatabase, val context: Context) {

    fun getImageDetail(imageId: String): Observable<Image> {
        return Observable.unsafeCreate<Image> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val image = database.imageDao().getImage(imageId)
                it.onNext(image)
                it.onComplete()
            })
        }
    }

    fun convertToDetailEntity(image: Image?): Observable<DetailEntity> {

        return Observable.unsafeCreate<DetailEntity> {
            val detailEntity = DetailEntity()
            detailEntity.imageData = image?.imageData

            if (image?.reviewers != null && image.reviewers?.isNotEmpty()!!) {
                //get reviewer one
                val reviewerOne = image.reviewers?.get(0)
                val reviewEntityOne = ReviewEntity()
                reviewEntityOne.comments = reviewerOne?.comments
                reviewEntityOne.status = getNameStatus(reviewerOne)
                reviewEntityOne.timestamp = getTimestamp(reviewerOne)
                detailEntity.reviewEntityOne = reviewEntityOne

                //get reviewer two
                if (image.reviewers?.size == 2) {
                    val reviewerTwo = image.reviewers?.get(1)
                    val reviewEntityTwo = ReviewEntity()
                    reviewEntityTwo.comments = reviewerTwo?.comments
                    reviewEntityTwo.status = getNameStatus(reviewerTwo)
                    reviewEntityTwo.timestamp = getTimestamp(reviewerTwo)
                    detailEntity.reviewEntityTwo = reviewEntityTwo
                }
            }

            detailEntity.statusBanner = getStatusBanner(image?.reviewers)
            it.onNext(detailEntity)
            it.onComplete()
        }
    }

    private fun getNameStatus(reviewer: Reviewer?): String {

        var status = ""

        when (reviewer?.status) {
            Constant.REJECTED -> status = context.getString(R.string.rejected)
            Constant.APPROVED -> status = context.getString(R.string.approved)
        }

        return "$status by ${reviewer?.name}"
    }

    private fun getTimestamp(reviewer: Reviewer?): String {
        val date = Date(reviewer?.timestamp!!)
        val format = SimpleDateFormat("MMM, dd yyyy")
        return format.format(date)
    }

    private fun getStatusBanner(reviewers: ArrayList<Reviewer>?): StatusBannerEntity {

        var statusBanner = StatusBannerEntity()

        if (reviewers != null) {

            //one reviewer rejected, then status rejected
            if (reviewers[0].status == Constant.REJECTED) {
                statusBanner.status = context.getString(R.string.rejected)
                statusBanner.colorCode = R.color.colorAccent
            } else {

                //reviewer one ok, but we need to check reviewer two
                if (reviewers.size == 1 && reviewers[0].status != Constant.REJECTED) {

                    //if reviewer one not reject then need reviewer 2, set to pending
                    statusBanner.status = context.getString(R.string.pending)
                    statusBanner.colorCode = R.color.colorPrimaryDark
                } else if (reviewers.size == 2 && reviewers[1].status == Constant.REJECTED) {

                    //if two reject then reject
                    statusBanner.status = context.getString(R.string.rejected)
                    statusBanner.colorCode = R.color.colorAccent
                } else {

                    //all ok then set to status to approve
                    statusBanner.status = context.getString(R.string.approved)
                    statusBanner.colorCode = R.color.colorPrimary
                }

            }

        } else {

            //no reviewer yet, set to pending
            statusBanner.status = context.getString(R.string.pending)
            statusBanner.colorCode = R.color.colorPrimaryDark
        }

        return statusBanner
    }
}