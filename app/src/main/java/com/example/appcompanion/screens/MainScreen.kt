package com.example.appcompanion.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.appcompanion.R
import com.google.firebase.analytics.FirebaseAnalytics

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "integració complerta")
        analytics.logEvent("InitScreen", bundle)

        var button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            val intent1 = Intent (this, LoginScreen::class.java)
            startActivity(intent1)
        }
    }
}