package com.example.appcompanion.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appcompanion.R
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton

class LoginScreen : Fragment() {

    lateinit var fragmentView: View

    //buttons
    val emailLoginButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.loginButton) }
    val registerButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.registerButton) }
    val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.activity_login_screen, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailLoginButton.setOnClickListener { emailLogin() }
        registerButton.setOnClickListener { startRegister() }
        googleAuthButton.setOnClickListener { googleAuth() }
    }

    private fun googleAuth() {
        TODO("Not yet implemented")
    }

    private fun startRegister() {
        TODO("Not yet implemented")
    }

    private fun emailLogin() {
        TODO("Not yet implemented")
    }
}