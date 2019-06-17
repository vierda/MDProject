package com.medilot.demo.mdproject.review.data

import com.medilot.demo.mdproject.common.data.database.AppDatabase
import com.medilot.demo.mdproject.common.data.model.Image
import io.reactivex.Observable
import java.util.concurrent.Executors

class ReviewDatabaseRepository (private val database:AppDatabase){

    fun saveImage (image: Image) : Observable<Boolean> {
        return Observable.unsafeCreate<Boolean> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                database.imageDao().insert(image)
                it.onNext(true)
            })
        }
    }
}