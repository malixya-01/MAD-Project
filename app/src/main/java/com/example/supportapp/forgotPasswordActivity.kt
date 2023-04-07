package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class forgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_forgot_password)





    }
}