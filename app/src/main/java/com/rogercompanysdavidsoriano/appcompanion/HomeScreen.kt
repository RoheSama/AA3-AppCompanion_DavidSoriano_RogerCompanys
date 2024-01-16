package com.rogercompanysdavidsoriano.appcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import java.nio.channels.spi.AsynchronousChannelProvider

class HomeScreen : AppCompatActivity() {

    private lateinit var logOutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        logOutButton = findViewById(R.id.logOutButton)

        setup()
    }

    private fun setup(){

        logOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}