package com.medilot.demo.mdproject.detail.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.detail.data.DetailUseCase
import com.medilot.demo.mdproject.detail.presentation.entity.DetailEntity


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = DetailViewModel::class.java.simpleName

    private val useCase: DetailUseCase = DetailUseCase(application.applicationContext)
    private val detailLiveData: MutableLiveData<DetailEntity> = MutableLiveData()


    fun getDetailLiveData(): MutableLiveData<DetailEntity> {
        return detailLiveData
    }


    fun getDetailEntity(imageId: String) {
        useCase.getDetailEntity(imageId, object : DefaultSubscriber<DetailEntity>() {
            override fun onError(t: Throwable?) {
                Log.e(TAG, "error getting image")
            }

            override fun onNext(t: DetailEntity) {
                detailLiveData.postValue(t)
            }
        })
    }
}