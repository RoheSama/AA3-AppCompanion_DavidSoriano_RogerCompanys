package com.rogercompanysdavidsoriano.appcompanion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var singOutButton: AppCompatButton
    private lateinit var goBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        singOutButton = findViewById(R.id.singOutButton)
        goBack = findViewById(R.id.back)

        singOutButton.setOnClickListener{

            // Quan tanquem sessio, borrem les dades guardades del usuari
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LogInScreen::class.java)
            startActivity(intent)
        }

        goBack.setOnClickListener{
            onBackPressed()
        }
    }
}