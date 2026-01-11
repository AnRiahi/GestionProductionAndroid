package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.UserEntity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val db = AppDatabase.getDatabase(this)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val btn = findViewById<Button>(R.id.btnSignUp)

        btn.setOnClickListener {
            val u = etUser.text.toString()
            val p = etPass.text.toString()
            val c = etConfirm.text.toString()

            if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
                Toast.makeText(this, "Complete les donnees", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (p != c) {
                Toast.makeText(this, " Mot de Passe n'est pas valide  ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Thread {
                db.userDao().insert(
                    UserEntity(username = u, password = p)
                )
                runOnUiThread {
                    Toast.makeText(this, "Compte créé ✔️", Toast.LENGTH_SHORT).show()
                    finish() //  Login
                }
            }.start()
        }
    }
}
