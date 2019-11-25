package com.example.wlgusdn.ourhealth


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amazonaws.mobile.client.*
import java.lang.Exception


class AuthenticationActivity : AppCompatActivity() {

    private val TAG = AuthenticationActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

            override fun onResult(userStateDetails: UserStateDetails) {
                Log.i(TAG, userStateDetails.userState.toString())
                when (userStateDetails.userState) {
                    UserState.SIGNED_IN -> {


                         val i = Intent(this@AuthenticationActivity, AccountActivity::class.java)


                        startActivity(i)
                    }
                    UserState.SIGNED_OUT -> showSignIn()
                    else ->
                    {
                        AWSMobileClient.getInstance().signOut()
                        showSignIn()
                    }
                }
            }

            override fun onError(e: Exception) {
                Log.e(TAG, e.toString())
            }
        })
    }

    private fun showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(
                    this,
                    SignInUIOptions.builder().nextActivity(AccountActivity::class.java).build()
            )
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }
}