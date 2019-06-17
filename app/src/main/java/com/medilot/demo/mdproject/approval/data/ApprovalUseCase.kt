package com.medilot.demo.mdproject.approval.data

import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase

class ApprovalUseCase : UseCase() {

    private var repository: ApprovalRepository = ApprovalRepository()

    fun saveReview(imageId: String, status: String, comments: String, subscriber: DefaultSubscriber<Int>) {
        execute(repository.saveReview(imageId, status, comments), subscriber)
    }

    fun getImage(imageId:String,subscriber: DefaultSubscriber<Image>) {
        execute(repository.getImage(imageId), subscriber)
    }
}