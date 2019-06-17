package com.medilot.demo.mdproject.zoom.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.zoom.data.ZoomUseCase

class ZoomViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = ZoomViewModel::class.java.simpleName
    private val useCase: ZoomUseCase = ZoomUseCase()
    private val imageLiveData: MutableLiveData<ByteArray> = MutableLiveData()

    fun getImageLiveData(): MutableLiveData<ByteArray> {
        return imageLiveData
    }

    fun getImage(imageId: String) {
        useCase.getImage(imageId, object : DefaultSubscriber<ByteArray>() {
            override fun onError(t: Throwable?) {
                Log.e(TAG, "error getting image")
            }

            override fun onNext(t: ByteArray) {
                imageLiveData.postValue(t)
            }
        })
    }
}