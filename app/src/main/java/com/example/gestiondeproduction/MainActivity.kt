package com.example.gestiondeproduction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCategories = findViewById<Button>(R.id.btnCategories)
        val btnMatieres = findViewById<Button>(R.id.btnMatieres)
        val btnProduits = findViewById<Button>(R.id.btnProduits)

        findViewById<Button>(R.id.btnComposition).setOnClickListener {
            startActivity(Intent(this, ProduitMatiereActivity::class.java))
        }

        findViewById<Button>(R.id.btnProduction).setOnClickListener {
            startActivity(Intent(this, ProductionActivity::class.java))
        }
        findViewById<Button>(R.id.btnStats).setOnClickListener {
            startActivity(
                Intent(this, StatistiqueActivity::class.java))}
        // ✅ HISTORIQUE
        findViewById<Button>(R.id.btnHistorique).setOnClickListener {
            startActivity(
                Intent(this, HistoriqueProductionActivity::class.java)
            )
        }
        findViewById<Button>(R.id.btnDashboard).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        findViewById<Button>(R.id.btnStockFaible).setOnClickListener {
            startActivity(Intent(this, StockFaibleActivity::class.java))
        }


        btnCategories.setOnClickListener {
            startActivity(Intent(this, CategorieActivity::class.java))
        }

        btnMatieres.setOnClickListener {
            startActivity(Intent(this, MatierePremiereActivity::class.java))
        }

        btnProduits.setOnClickListener {
            startActivity(Intent(this, ProduitActivity::class.java))
        }
    }
}
