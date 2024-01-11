package com.example.appcompanion.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appcompanion.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    var loginButton = findViewById<Button>(R.id.loginButton)
    var registerButton = findViewById<Button>(R.id.registerButton)
    var emailText = findViewById<EditText>(R.id.emailTextInput)
    var passwordText = findViewById<EditText>(R.id.passwordTextInput)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle();

        bundle.putString("message", "Integracion de Firebase")
        analytics.logEvent("InitScreen", bundle)

        setup()

    }

    private fun setup(){
        title = "Autenticacion"

        registerButton.setOnClickListener{
            if(emailText.text.isNotEmpty() && passwordText.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText.text.toString(),
                    passwordText.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                }
            }
        }

        loginButton.setOnClickListener{
            if(emailText.text.isNotEmpty() && passwordText.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText.text.toString(),
                    passwordText.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error autenticando usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){

        val homeIntent = Intent(this, HomeScreen::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }

        startActivity(homeIntent)
    }
}