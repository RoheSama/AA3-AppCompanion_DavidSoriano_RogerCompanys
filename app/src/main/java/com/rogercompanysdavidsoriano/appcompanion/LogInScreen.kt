package com.rogercompanysdavidsoriano.appcompanion

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FilterQueryProvider
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInScreen : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    private lateinit var signUpButton: Button
    private lateinit var logInButton: Button
    private lateinit var googleButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var authLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        signUpButton = findViewById(R.id.singUpButton)
        googleButton = findViewById(R.id.googleButton)
        logInButton = findViewById(R.id.logInButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        authLayout = findViewById(R.id.authLayout)

        setup()
        session()
    }

    override fun onStart(){
        super.onStart()

        authLayout.visibility = View.VISIBLE
    }

    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        // Comprovem si teniam dades de sessio emmagatzemades
        if(email!= null && provider != null){
            authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
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
                        showHome(it.result?.user?.email ?: "",ProviderType.BASIC)
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
                        showHome(it.result?.user?.email ?: "",ProviderType.BASIC)
                    }else{
                        //Si el login falla, missatge d'alerta
                        showAlert()
                    }
                }
            }
        }

        googleButton.setOnClickListener {
            // Configuracio de autenticacio
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
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

    private fun showHome(email: String, providerType: ProviderType){
        // Carregar activitat principal de l'aplicació
        val homeIntent = Intent(this, HomeScreen::class.java).apply {
            putExtra("email", email)
            putExtra("provider", providerType)
        }
        startActivity(homeIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(){
                        if(it.isSuccessful){
                            //Si el log in funciona, t'envia a l'activitat principal
                            showHome( account.email ?: "",ProviderType.GOOGLE)
                        }else{
                            //Si el login falla, missatge d'alerta
                            showAlert()
                        }
                    }
                }
            }catch (e:ApiException){
                e.printStackTrace()
                showAlert()
            }

        }
    }
}