package com.medilot.demo.mdproject.login.presentation.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.medilot.demo.mdproject.R
import com.medilot.demo.mdproject.common.data.model.User
import com.medilot.demo.mdproject.common.util.Constant
import com.medilot.demo.mdproject.login.presentation.viewmodel.LoginViewModel
import com.medilot.demo.mdproject.main.presentation.view.MainActivity
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var loginLiveData: LiveData<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginLiveData = loginViewModel.getLoginLiveData()

        loginViewModel.populateUserData()

        startAnimation()


        login_button.setOnClickListener(View.OnClickListener {
            username = id_username.text.toString()
            password = id_password.text.toString()

            loginViewModel.getAuthenticatedUser(username, password)
            observe()
        })


        this.setSupportActionBar(custom_toolbar_id as Toolbar)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun startAnimation() {
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        login_container.visibility = View.VISIBLE
        login_container.startAnimation(fadeInAnimation)
    }

    private fun observe() {
        loginLiveData.observe(this, Observer<User> { user ->

            Handler().postDelayed(Runnable {
                if (user != null) {

                    Constant.CURRENT_ROLE = user.role
                    Constant.CURRENT_USER_ID = user.userId

                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    val message = getString(R.string.error_invalid_login)
                    toast(message)

                }

            }, 500)

        })
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}