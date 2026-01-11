package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.CategorieEntity

class CategorieActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var listView: ListView
    private lateinit var etNom: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorie)
        db = AppDatabase.getDatabase(this)
        listView = findViewById(R.id.listViewCategories)
        etNom = findViewById(R.id.etCategorie)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        loadCategories()

        btnAdd.setOnClickListener {
            val nom = etNom.text.toString()
            if (nom.isNotEmpty()) {
                Thread {
                    db.categorieDao().insert(
                        CategorieEntity(nom = nom)
                    )
                    runOnUiThread {
                        etNom.text.clear()
                        loadCategories()
                    }
                }.start()
            }
        }
    }

    private fun loadCategories() {
        Thread {
            val list = db.categorieDao().getAll()
            runOnUiThread {
                listView.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    list.map { it.nom }
                )
            }
        }.start()
    }
}
