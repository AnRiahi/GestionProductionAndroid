package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.CategorieEntity
import com.example.gestiondeproduction.data.entity.ProduitEntity

class ProduitActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var spCategorie: Spinner
    private lateinit var etNomProduit: EditText
    private lateinit var btnAddProduit: Button
    private lateinit var listViewProduits: ListView

    private var categories = listOf<CategorieEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produit)

        // DB
        db = AppDatabase.getDatabase(this)

        // Views
        spCategorie = findViewById(R.id.spCategorie)
        etNomProduit = findViewById(R.id.etNomProduit)
        btnAddProduit = findViewById(R.id.btnAddProduit)
        listViewProduits = findViewById(R.id.listViewProduits)

        loadCategories()
        loadProduits()

        btnAddProduit.setOnClickListener {
            ajouterProduit()
        }
    }

    //  CATEGORIES
    private fun loadCategories() {
        Thread {
            categories = db.categorieDao().getAll()

            runOnUiThread {
                if (categories.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Ajoutez une catégorie d'abord",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    categories.map { it.nom }
                )
                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )
                spCategorie.adapter = adapter
            }
        }.start()
    }

    // ADD PRODUIT
    private fun ajouterProduit() {
        val nomProduit = etNomProduit.text.toString().trim()

        if (nomProduit.isEmpty()) {
            Toast.makeText(this, "Nom produit vide", Toast.LENGTH_SHORT).show()
            return
        }

        if (categories.isEmpty()) {
            Toast.makeText(this, "Aucune catégorie", Toast.LENGTH_SHORT).show()
            return
        }

        val categorie = categories[spCategorie.selectedItemPosition]

        Thread {
            db.produitDao().insert(
                ProduitEntity(
                    nom = nomProduit,
                    categorieId = categorie.id
                )
            )

            runOnUiThread {
                etNomProduit.text.clear()
                loadProduits()
                Toast.makeText(this, "Produit ajouté", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    //  LIST PRODUITS
    private fun loadProduits() {
        Thread {
            val produits = db.produitDao().getAll()
            val cats = db.categorieDao().getAll()

            val displayList = produits.map { produit ->
                val cat = cats.find { it.id == produit.categorieId }
                "${produit.nom}  →  ${cat?.nom ?: "?"}"
            }

            runOnUiThread {
                listViewProduits.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayList
                )
            }
        }.start()
    }
}
