package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.CategorieEntity
import com.example.gestiondeproduction.data.entity.MatierePremiereEntity

class MatierePremiereActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var spCategorie: Spinner
    private lateinit var listView: ListView

    private var categories = listOf<CategorieEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matiere_premiere)

        db = AppDatabase.getDatabase(this)

        spCategorie = findViewById(R.id.spCategories)
        listView = findViewById(R.id.listViewMatieres)

        val etNom = findViewById<EditText>(R.id.etNom)
        val etStock = findViewById<EditText>(R.id.etStock)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val etSeuil = findViewById<EditText>(R.id.etSeuil)

        btnAdd.setOnClickListener {
            val nom = etNom.text.toString()
            val seuil = etSeuil.text.toString().toDoubleOrNull() ?: 5.0

            if (nom.isNotEmpty() && categories.isNotEmpty()) {
                val categorie = categories[spCategorie.selectedItemPosition]

                Thread {
                    db.matierePremiereDao().insert(
                        MatierePremiereEntity(
                            nom = nom,
                            categorieId = categorie.id,
                            stock = 0.0,
                            seuil = seuil
                        )
                    )

                    runOnUiThread {
                        etNom.text.clear()
                        etSeuil.text.clear()
                        loadMatieres()
                    }
                }.start()
            }
        }


        loadCategories()
        loadMatieres()

        btnAdd.setOnClickListener {
            val nom = etNom.text.toString()
            val stock = etStock.text.toString().toDoubleOrNull()

            if (nom.isEmpty() || stock == null || categories.isEmpty()) {
                Toast.makeText(
                    this,
                    "verfier  nom، stock، categorie",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val categorie = categories[spCategorie.selectedItemPosition]

            Thread {
                db.matierePremiereDao().insert(
                    MatierePremiereEntity(
                        nom = nom,
                        categorieId = categorie.id,
                        stock = stock
                    )
                )

                runOnUiThread {
                    etNom.text.clear()
                    etStock.text.clear()
                    loadMatieres()
                }
            }.start()
        }
    }

    private fun loadCategories() {
        Thread {
            categories = db.categorieDao().getAll()
            runOnUiThread {
                spCategorie.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    categories.map { it.nom }
                )
            }
        }.start()
    }

    private fun loadMatieres() {
        Thread {
            val matieres = db.matierePremiereDao().getAll()
            val cats = db.categorieDao().getAll()

            val displayList = matieres.map { matiere ->
                val cat = cats.find { it.id == matiere.categorieId }
                "${matiere.nom}  →  ${cat?.nom ?: "?"} | Stock : ${matiere.stock}"
            }

            runOnUiThread {
                listView.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayList
                )
            }
        }.start()
    }
}
