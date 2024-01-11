package com.example.appcompanion.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcompanion.R

enum class ProviderType{
    BASIC
}
class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

    }

    private fun setup(email: String, provider: String){
        title = "Inicio"
    }
}