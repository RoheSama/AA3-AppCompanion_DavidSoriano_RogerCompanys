package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FilterQueryProvider
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LogInScreen : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var logInButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        signUpButton = findViewById(R.id.singUpButton)
        logInButton = findViewById(R.id.logInButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        setup()
    }

    private fun setup(){
        title = "Autenticació"

        signUpButton.setOnClickListener {
            //Comprovem que rebem text als formularis
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        //Si el log in funciona, t'envia a l'activitat principal
                        showHome()
                    } else {
                        //Si el login falla, missatge d'alerta
                        showAlert()
                    }
                }
            }
        }

        logInButton.setOnClickListener{
            //Comprovem que rebem text als formularis
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                //Si el botó premut es el log in, no afegim l'usuari a Firebase
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener(){
                    if(it.isSuccessful){
                        //Si el log in funciona, t'envia a l'activitat principal
                        showHome()
                    }else{
                        //Si el login falla, missatge d'alerta
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        //Missatge d'alerta mostrat quan l'intent de log in falla
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error en la autenticación del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(){
        // Carregar activitat principal de l'aplicació
        val homeIntent = Intent(this, HomeScreen::class.java)
        startActivity(homeIntent)
    }
}