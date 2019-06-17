package com.medilot.demo.mdproject.login.data

import com.medilot.demo.mdproject.common.data.model.User
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase

class LoginUseCase : UseCase() {

    private var repository: LoginRepository = LoginRepository()

    fun populateData(subscriber: DefaultSubscriber<Boolean>) {
        execute(repository.populateUser(), subscriber)
    }

    fun getAuthenticatedUser(username: String, password: String, subscriber: DefaultSubscriber<User>) {
        execute(repository.getAuthenticatedUser(username, password), subscriber)
    }
}