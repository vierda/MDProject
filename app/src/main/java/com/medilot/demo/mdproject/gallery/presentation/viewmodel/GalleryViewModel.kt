package com.medilot.demo.mdproject.gallery.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.gallery.data.GalleryUseCase


class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = GalleryViewModel::class.java.simpleName

    private val useCase: GalleryUseCase = GalleryUseCase()
    private val galleryLiveData: MutableLiveData<List<Image>> = MutableLiveData()

    fun getGalleryLiveData(): MutableLiveData<List<Image>> {
        return galleryLiveData
    }

    fun getAllImages() {
        useCase.getAllImages(object : DefaultSubscriber<List<Image>>(){
            override fun onError(t: Throwable?) {
               Log.e(TAG,"error getting all images")
            }

            override fun onNext(t: List<Image>) {
                galleryLiveData.postValue(t)
            }
        })
    }
}