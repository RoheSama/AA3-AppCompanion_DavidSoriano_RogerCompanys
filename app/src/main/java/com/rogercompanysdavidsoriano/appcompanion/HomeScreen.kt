package com.rogercompanysdavidsoriano.appcompanion

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import java.nio.channels.spi.AsynchronousChannelProvider

enum class ProviderType {
    BASIC,
    GOOGLE
}

class HomeScreen : AppCompatActivity() {

    private lateinit var logOutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        logOutButton = findViewById(R.id.logOutButton)

        // Dades del usuari autenticat
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")

        // Guardar dades del usuari al fitxer strings
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup(email: String, provdirer: String){

        logOutButton.setOnClickListener{

            // Quan tanquem sessio, borrem les dades guardades del usuari
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}