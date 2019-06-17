package com.medilot.demo.mdproject.detail.data

import android.content.Context
import com.medilot.demo.mdproject.MedilotApplication
import com.medilot.demo.mdproject.detail.presentation.entity.DetailEntity
import io.reactivex.Observable

class DetailRepository (context: Context) {

    private var databaseRepository = DetailDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!,context)

    fun getDetailEntity (imageId:String) :Observable<DetailEntity> {
        return databaseRepository.getImageDetail(imageId)
            .flatMap { image -> databaseRepository.convertToDetailEntity(image)}
    }
}