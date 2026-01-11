package com.example.gestiondeproduction
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import com.example.gestiondeproduction.data.entity.ProduitEntity
import com.example.gestiondeproduction.data.entity.ProductionEntity
class ProductionActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var spProduits: Spinner
    private lateinit var etQuantite: EditText
    private lateinit var tvResult: TextView
    private var produits = listOf<ProduitEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_production)

        db = AppDatabase.getDatabase(this)

        spProduits = findViewById(R.id.spProduits)
        etQuantite = findViewById(R.id.etQuantite)
        tvResult = findViewById(R.id.tvResult)
        val btnProduire = findViewById<Button>(R.id.btnProduire)

        loadProduits()

        btnProduire.setOnClickListener {
            produire()
        }
    }
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

    private fun produire() {
        val qte = etQuantite.text.toString().toDoubleOrNull()
        if (qte == null || qte <= 0) {
            Toast.makeText(this, "Quantité invalide", Toast.LENGTH_SHORT).show()
            return
        }

        val produit = produits[spProduits.selectedItemPosition]

        Thread {
            val pmDao = db.produitMatiereDao()
            val mpDao = db.matierePremiereDao()
            val prodDao = db.productionDao()

            val productionPossible =
                pmDao.getProductionPossible(produit.id) ?: 0.0

            if (qte > productionPossible) {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Stock insuffisant ❌",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return@Thread
            }


            val compositions = pmDao.getByProduit(produit.id)
            compositions.forEach {
                val consommation = it.quantite * qte
                mpDao.decreaseStock(it.matiereId, consommation)
            }


            prodDao.insert(
                ProductionEntity(
                    produitId = produit.id,
                    quantite = qte,
                    date = System.currentTimeMillis()
                )
            )

            runOnUiThread {
                tvResult.text =
                    "Production réussie ✅\n$qte ${produit.nom}"
                etQuantite.text.clear()
            }
        }.start()
    }
}
