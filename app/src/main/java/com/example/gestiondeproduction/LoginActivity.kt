package com.example.gestiondeproduction

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.UserEntity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getDatabase(this)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val btn = findViewById<Button>(R.id.btnLogin)

        // Admin par défaut
        Thread {
            if (db.userDao().login("admin", "1234") == null) {
                db.userDao().insert(
                    UserEntity(username = "admin", password = "1234")
                )
            }
        }.start()
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        tvSignup.setOnClickListener {
            startActivity(
                Intent(this, SignUpActivity::class.java)
            )
        }


        btn.setOnClickListener {
            val u = etUser.text.toString()
            val p = etPass.text.toString()

            Thread {
                val user = db.userDao().login(u, p)
                runOnUiThread {
                    if (user != null) {
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Login incorrect ❌",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.start()
        }
    }
}
