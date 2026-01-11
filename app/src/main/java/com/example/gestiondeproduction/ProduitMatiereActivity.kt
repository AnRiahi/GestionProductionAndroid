package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.*

class ProduitMatiereActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var spProduits: Spinner
    private lateinit var spMatieres: Spinner
    private lateinit var etQuantite: EditText
    private lateinit var btnAdd: Button
    private lateinit var listView: ListView
    private lateinit var tvProduction: TextView

    private var produits = listOf<ProduitEntity>()
    private var matieres = listOf<MatierePremiereEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produit_matiere)

        db = AppDatabase.getDatabase(this)

        spProduits = findViewById(R.id.spProduits)
        spMatieres = findViewById(R.id.spMatieres)
        etQuantite = findViewById(R.id.etQuantite)
        btnAdd = findViewById(R.id.btnAdd)
        listView = findViewById(R.id.listViewPM)
        tvProduction = findViewById(R.id.tvProduction)

        loadProduits()
        loadMatieres()

        btnAdd.setOnClickListener { ajouterComposition() }

        spProduits.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val produitId = produits[position].id
                    loadCompositions(produitId)
                    loadProductionPossible(produitId)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    // LOAD
    private fun loadProduits() {
        Thread {
            produits = db.produitDao().getAll()
            runOnUiThread {
                spProduits.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    produits.map { it.nom }
                )
            }
        }.start()
    }

    private fun loadMatieres() {
        Thread {
            matieres = db.matierePremiereDao().getAll()
            runOnUiThread {
                spMatieres.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    matieres.map { it.nom }
                )
            }
        }.start()
    }

    // ADD
    private fun ajouterComposition() {
        val qte = etQuantite.text.toString().toDoubleOrNull()
        if (qte == null || qte <= 0) {
            Toast.makeText(this, "Quantité invalide", Toast.LENGTH_SHORT).show()
            return
        }

        val produit = produits[spProduits.selectedItemPosition]
        val matiere = matieres[spMatieres.selectedItemPosition]

        Thread {
            val dao = db.produitMatiereDao()
            val exist = dao.getByProduitAndMatiere(produit.id, matiere.id)

            if (exist != null) {
                dao.update(exist.copy(quantite = exist.quantite + qte))
            } else {
                dao.insert(
                    ProduitMatiereEntity(
                        produitId = produit.id,
                        matiereId = matiere.id,
                        quantite = qte
                    )
                )
            }

            runOnUiThread {
                etQuantite.text.clear()
                loadCompositions(produit.id)
                loadProductionPossible(produit.id)
            }
        }.start()
    }

    //  LIST
    private fun loadCompositions(produitId: Int) {
        Thread {
            val list = db.produitMatiereDao().getByProduit(produitId)
            runOnUiThread {
                listView.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    list.map {
                        val m = matieres.find { mat -> mat.id == it.matiereId }
                        "${m?.nom} : ${it.quantite}"
                    }
                )
            }
        }.start()
    }

    private fun loadProductionPossible(produitId: Int) {
        Thread {
            val prod = db.produitMatiereDao()
                .getProductionPossible(produitId) ?: 0.0

            runOnUiThread {
                tvProduction.text =
                    "Production possible : ${prod.toInt()}"
            }
        }.start()
    }

}
