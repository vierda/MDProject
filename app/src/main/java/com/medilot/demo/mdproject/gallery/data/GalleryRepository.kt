package com.medilot.demo.mdproject.gallery.data

import com.medilot.demo.mdproject.MedilotApplication
import com.medilot.demo.mdproject.common.data.model.Image
import io.reactivex.Observable

class GalleryRepository {

    private var databaseRepository = GalleryDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!)

    fun getAllImages() : Observable<List<Image>> {
        return databaseRepository.getAllImages()
    }
}