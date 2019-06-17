package com.medilot.demo.mdproject.login.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.medilot.demo.mdproject.common.data.model.User
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.login.data.LoginUseCase

class LoginViewModel (application: Application) : AndroidViewModel(application) {

    private val TAG = LoginViewModel::class.java.simpleName
    private val useCase: LoginUseCase = LoginUseCase()
    private val loginLiveData: MutableLiveData<User> = MutableLiveData()

    fun getLoginLiveData(): MutableLiveData<User> {
        return loginLiveData
    }

    fun populateUserData() {
        useCase.populateData(object : DefaultSubscriber<Boolean>() {
            override fun onError(t: Throwable?) {
                Log.e(TAG, "Error populate data")
            }

            override fun onNext(t: Boolean) {
                Log.i(TAG, "populate data ok")
            }
        })
    }

    fun getAuthenticatedUser(username: String, password: String) {
        useCase.getAuthenticatedUser(username, password, object : DefaultSubscriber<User>() {

            override fun onError(t: Throwable?) {
                loginLiveData.postValue(User())
            }

            override fun onNext(t: User) {
                loginLiveData.postValue(t)
            }

        })
    }
}