package com.medilot.demo.mdproject.review.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.review.data.ReviewUseCase

class ReviewViewModel (application: Application) : AndroidViewModel(application) {

    private val useCase: ReviewUseCase = ReviewUseCase()
    private val reviewLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getReviewLiveData(): MutableLiveData<Boolean> {
        return reviewLiveData
    }

    fun saveImage(image: Image) {
        useCase.saveImage(image, object : DefaultSubscriber<Boolean>(){
            override fun onError(t: Throwable?) {
                reviewLiveData.postValue(false)
            }

            override fun onNext(t: Boolean) {
                reviewLiveData.postValue(t)
            }
        })
    }
}