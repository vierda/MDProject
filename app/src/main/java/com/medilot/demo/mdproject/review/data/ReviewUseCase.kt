package com.medilot.demo.mdproject.review.data

import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase

class ReviewUseCase : UseCase() {

    private var repository: ReviewRepository = ReviewRepository()

    fun saveImage(image: Image, subscriber: DefaultSubscriber<Boolean>) {
        execute(repository.saveImage(image), subscriber)
    }
}