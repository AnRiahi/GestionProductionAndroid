package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase

class StatistiqueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistique)

        val tvStats = findViewById<TextView>(R.id.tvStats)
        val db = AppDatabase.getDatabase(this)

        Thread {
            val productions = db.productionDao().getAll()
            val produits = db.produitDao().getAll()

            val stats = productions.groupBy { it.produitId }
                .map { entry ->
                    val produit = produits.find { it.id == entry.key }
                    val total = entry.value.sumOf { it.quantite }
                    "${produit?.nom ?: "?"} → $total unités"
                }

            runOnUiThread {
                tvStats.text = if (stats.isEmpty())
                    "Aucune statistique disponible"
                else
                    stats.joinToString("\n")
            }
        }.start()
    }
}
