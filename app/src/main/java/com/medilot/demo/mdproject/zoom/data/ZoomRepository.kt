package com.medilot.demo.mdproject.zoom.data

import com.medilot.demo.mdproject.MedilotApplication
import io.reactivex.Observable

class ZoomRepository {

    private var databaseRepository = ZoomDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!)

    fun getImage(imageId:String) : Observable<ByteArray> {
        return databaseRepository.getImage(imageId)
    }
}