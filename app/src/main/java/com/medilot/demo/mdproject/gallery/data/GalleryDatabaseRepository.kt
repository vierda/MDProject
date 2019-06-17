package com.medilot.demo.mdproject.gallery.data

import com.medilot.demo.mdproject.common.data.database.AppDatabase
import com.medilot.demo.mdproject.common.data.model.Image
import io.reactivex.Observable
import java.util.concurrent.Executors

class GalleryDatabaseRepository(val database: AppDatabase) {

    fun getAllImages(): Observable<List<Image>> {
        return Observable.unsafeCreate<List<Image>> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val images = database.imageDao().getAll()
                it.onNext(images)
            })
        }
    }
}