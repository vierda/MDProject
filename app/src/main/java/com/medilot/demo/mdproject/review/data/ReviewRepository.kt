package com.medilot.demo.mdproject.review.data

import com.medilot.demo.mdproject.MedilotApplication
import com.medilot.demo.mdproject.common.data.model.Image
import io.reactivex.Observable

class ReviewRepository {

    private var databaseRepository = ReviewDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!)

    fun saveImage(image : Image): Observable<Boolean> {
        return databaseRepository.saveImage(image)
    }
}