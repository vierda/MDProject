package com.medilot.demo.mdproject.approval.data

import com.medilot.demo.mdproject.MedilotApplication
import com.medilot.demo.mdproject.common.data.model.Image
import io.reactivex.Observable

class ApprovalRepository {

    private var databaseRepository = ApprovalDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!)

    fun saveReview(imageId:String, status:String, comments:String) : Observable<Int> {
        return databaseRepository.saveReview(imageId,status,comments)
    }

    fun getImage(imageId:String) : Observable<Image> {
        return databaseRepository.getImageDetail(imageId)
    }
}