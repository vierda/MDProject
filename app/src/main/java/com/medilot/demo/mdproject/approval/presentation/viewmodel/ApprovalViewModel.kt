package com.medilot.demo.mdproject.approval.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.medilot.demo.mdproject.approval.data.ApprovalUseCase
import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.common.util.DefaultSubscriber

class ApprovalViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = ApprovalViewModel::class.java.simpleName
    private val useCase: ApprovalUseCase = ApprovalUseCase()
    private val approvalLiveData: MutableLiveData<Int> = MutableLiveData()
    private val imageLiveData: MutableLiveData<Image> = MutableLiveData()

    fun getApprovalLiveData(): MutableLiveData<Int> {
        return approvalLiveData
    }

    fun getImageLiveData() : MutableLiveData<Image>{
        return imageLiveData
    }

    fun saveReview(imageId: String, status: String, comments: String) {
        useCase.saveReview(imageId,status,comments, object : DefaultSubscriber<Int>() {
            override fun onError(t: Throwable?) {
                approvalLiveData.postValue(Constant.ERROR_SUBMIT_REVIEW)
            }

            override fun onNext(t: Int) {
                approvalLiveData.postValue(t)
            }
        })
    }

    fun getImage(imageId: String) {
        useCase.getImage(imageId, object : DefaultSubscriber<Image>() {
            override fun onError(t: Throwable?) {
                Log.e(TAG, "error getting image")
            }

            override fun onNext(t: Image) {
                imageLiveData.postValue(t)
            }
        })
    }
}