package com.medilot.demo.mdproject.zoom.data

import com.medilot.demo.mdproject.common.data.database.AppDatabase
import io.reactivex.Observable
import java.util.concurrent.Executors

class ZoomDatabaseRepository (val database : AppDatabase) {

    fun getImage(imageId: String): Observable<ByteArray> {
        return Observable.unsafeCreate<ByteArray> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val image = database.imageDao().getImage(imageId).imageData
                it.onNext(image)
            })
        }
    }
}