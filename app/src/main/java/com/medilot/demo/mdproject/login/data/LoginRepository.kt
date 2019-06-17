package com.medilot.demo.mdproject.login.data

import com.medilot.demo.mdproject.MedilotApplication
import com.medilot.demo.mdproject.common.data.model.User
import io.reactivex.Observable

class LoginRepository {

    private var databaseRepository = LoginDatabaseRepository(MedilotApplication.getInstance().getDatabase()!!)

    fun populateUser(): Observable<Boolean> {
        return databaseRepository.populateUser()
    }

    fun getAuthenticatedUser(username: String, password: String): Observable<User> {
        return databaseRepository.getAuthenticatedUser(username, password)
    }
}