package com.example.musicfinal.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.musicfinal.R
import com.example.musicfinal.firebase.AuthUpdater
import com.example.musicfinal.firebase.DatabaseUpdater
import com.example.musicfinal.firebase.FirebaseAuthUtils
import com.example.musicfinal.firebase.FirebaseDatabaseUtils
import com.example.musicfinal.model.User
import com.example.musicfinal.sharepreference.UserSharePreference
import com.example.musicfinal.utils.checkUserLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        private val TAG = LoginActivity::class.qualifiedName
        const val REQUEST_CODE = 100
    }

    private lateinit var userSharedPreferences: UserSharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSharedPreferences = UserSharePreference(this)
        if (userSharedPreferences.isLogin()) {
            openListMusicActivity()
            return
        }

        setContentView(R.layout.activity_login)
        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val mail = data?.getStringExtra(SignUpActivity.MAIL_KEY)
            edtMail.setText(mail)
        }
    }

    private fun initListeners() {
        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        btnLogin.setOnClickListener {
            val mail = edtMail.text.toString()
            val password = edtPassword.text.toString()
            login(mail, password)
        }
    }

    private fun login(mail: String, password: String) {
        val result = checkUserLogin(mail, password)

        if (!result.isSuccess()) {
            displayErrorMessage(result.getMessage())
        } else {
            FirebaseAuthUtils.login(mail, password, this, object : AuthUpdater {
                override fun onSuccess() {
                    saveCurrentUser(mail)
                }

                override fun onFail() {
                    displayErrorMessage(R.string.error_login)
                }
            })
        }
    }

    private fun saveCurrentUser(mail: String) {
        FirebaseDatabaseUtils.getCurrentUser(mail, object : DatabaseUpdater {
            override fun onComplete(user: User) {
                Log.e(TAG, user.toString())
                userSharedPreferences.saveUserLogin(user)
                openListMusicActivity()
            }
        })
    }

    private fun displayErrorMessage(messageReference: Int) {
        tvError.text = resources.getString(messageReference)
        tvError.setBackgroundResource(R.drawable.border_text_view_error)
    }

    private fun openListMusicActivity() {
        val profileIntent = Intent(this, MainActivity::class.java)
        startActivity(profileIntent)
    }
}
