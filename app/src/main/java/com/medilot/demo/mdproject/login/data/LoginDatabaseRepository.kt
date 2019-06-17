package com.medilot.demo.mdproject.login.data

import com.medilot.demo.mdproject.common.data.database.AppDatabase
import com.medilot.demo.mdproject.common.data.model.User
import com.medilot.demo.mdproject.common.util.Constant
import io.reactivex.Observable
import java.util.concurrent.Executors

class LoginDatabaseRepository (val database : AppDatabase) {

    private fun initUserLogin(): List<User> {
        val loginOne = User()
        loginOne.username = "mila"
        loginOne.password = "mila"
        loginOne.name = "Vierda Mila"
        loginOne.role = Constant.ADMIN

        val loginTwo = User()
        loginTwo.username = "dumi"
        loginTwo.password = "dumi"
        loginTwo.name = "Dumitrel Loghin"
        loginTwo.role = Constant.ADMIN

        val loginThree = User()
        loginThree.username = "jane"
        loginThree.password = "jane"
        loginThree.name = "Jane Doe"
        loginThree.role = Constant.USER

        return arrayListOf(loginOne, loginTwo, loginThree)

    }

    fun populateUser(): Observable<Boolean> {
        return Observable.unsafeCreate<Boolean> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val loginUsers = initUserLogin()
                database.userDao().insertAll(loginUsers)
                it.onNext(true)
            })
        }
    }


    fun getAuthenticatedUser(username: String, password: String): Observable<User> {
        return Observable.unsafeCreate<User> { subscriber ->
            Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                val user = database.userDao().getAuthenticationUser(username, password)
                subscriber.onNext(user)
            })
        }
    }
}