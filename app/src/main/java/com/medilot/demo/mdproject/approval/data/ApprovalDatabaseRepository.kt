package com.medilot.demo.mdproject.approval.data

import com.medilot.demo.mdproject.common.data.database.AppDatabase
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.data.model.Reviewer
import com.medilot.demo.mdproject.common.util.Constant
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


class ApprovalDatabaseRepository(val database: AppDatabase) {

    fun saveReview(imageId: String, status: String, comments: String): Observable<Int> {

        return Observable.unsafeCreate<Int> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val image = database.imageDao().getImage(imageId)

                if (Constant.REJECTED == status && comments.isNullOrEmpty()) {
                    it.onNext(Constant.ERROR_REJECT_WITHOUT_COMMENT)
                }

                if (image.reviewers != null &&
                    (image.reviewers?.isNotEmpty()!! && image.reviewers?.size == 2)) {
                    it.onNext(Constant.ERROR_ENOUGH_REVIEWER)
                } else if (image.reviewers != null && image.reviewers?.isNotEmpty()!! &&
                    image.reviewers?.size == 1) {

                    val reviewer = image.reviewers?.get(0)?.name
                    val currentUser = database.userDao().getUser(Constant.CURRENT_USER_ID).name

                    if (reviewer == currentUser) {
                        it.onNext(Constant.ERROR_SAME_REVIEWER)
                    } else {
                        createReviewer(imageId,status,comments,image)
                        it.onNext(Constant.SUCCESS_SUBMIT_REVIEW)
                    }
                } else {
                    createReviewer(imageId,status,comments,image)
                    it.onNext(Constant.SUCCESS_SUBMIT_REVIEW)
                }
            })
        }
    }

    private fun createReviewer (imageId: String, status: String, comments: String, image:Image) {
        val reviewer = Reviewer()
        reviewer.reviewerId = UUID.randomUUID().toString()
        reviewer.imageId = imageId
        reviewer.status = status
        reviewer.comments = comments
        reviewer.timestamp = Date().time

        val user = database.userDao().getUser(Constant.CURRENT_USER_ID)
        reviewer.name = user.name

        var currentReviewers = image.reviewers
        if (currentReviewers == null) {
            currentReviewers = ArrayList()
        }

        currentReviewers.add(reviewer)
        database.imageDao().updateImageReviewers(currentReviewers, imageId)

    }

    fun getImageDetail(imageId: String): Observable<Image> {
        return Observable.unsafeCreate<Image> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val image = database.imageDao().getImage(imageId)
                it.onNext(image)
            })
        }
    }
}