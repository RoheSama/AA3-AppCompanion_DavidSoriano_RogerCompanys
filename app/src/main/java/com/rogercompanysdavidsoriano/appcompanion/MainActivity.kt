package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Event per provar firebase Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integració Firebase correcta")
        analytics.logEvent("InitScreen", bundle)

        //Boto que t'envia a login screen
        val button = findViewById<Button>(R.id.main_button)

        button.setOnClickListener{
            val intent = Intent(this, LogInScreen::class.java)
            startActivity(intent)
        }
    }
}