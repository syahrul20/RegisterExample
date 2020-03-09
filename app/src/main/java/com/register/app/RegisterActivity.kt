package com.register.app

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


/**
 * بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ
 * Created By fahmi on 08/03/20
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        initListener()
    }

    private fun initListener() {
        btnRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val pass = editTextPassword.text.toString()
            if(editTextEmail.text.isNullOrBlank()) {
                editTextEmail.error = "Masukan email"
                return@setOnClickListener
            }

            if (editTextPassword.text.isNullOrBlank()) {
                editTextPassword.error = "Password harus diisi"
                return@setOnClickListener
            }

            if (isValidate()) {
                progressDialog = ProgressDialog.show(this, "","Silahkan Tunggu", true)
                progressDialog?.show()
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        progressDialog?.hide()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        progressDialog?.hide()
                        Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString().trim()).matches()) {
            true
        } else {
            editTextEmail.error = "Email tidak valid"
            false
        }
    }
}