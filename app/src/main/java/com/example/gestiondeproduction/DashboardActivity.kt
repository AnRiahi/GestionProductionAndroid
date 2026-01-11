package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val db = AppDatabase.getDatabase(this)

        val tvCategories = findViewById<TextView>(R.id.tvCategories)
        val tvMatieres = findViewById<TextView>(R.id.tvMatieres)
        val tvProduits = findViewById<TextView>(R.id.tvProduits)
        val tvCompositions = findViewById<TextView>(R.id.tvCompositions)
        val tvProductions = findViewById<TextView>(R.id.tvProductions)
        val tvAlertes = findViewById<TextView>(R.id.tvAlertes)

        Thread {
            val nbCategories = db.categorieDao().getAll().size
            val nbMatieres = db.matierePremiereDao().getAll().size
            val nbProduits = db.produitDao().getAll().size
            val nbCompositions = db.produitMatiereDao().getAll().size
            val nbProductions = db.productionDao().getAll().size

            val nbAlertes = 0

            runOnUiThread {
                tvCategories.text = "📦 Catégories : $nbCategories"
                tvMatieres.text = "🧱 Matières premières : $nbMatieres"
                tvProduits.text = "🏭 Produits : $nbProduits"
                tvCompositions.text = "🔧 Compositions : $nbCompositions"
                tvProductions.text = "🏗️ Productions : $nbProductions"
                tvAlertes.text = "⚠️ Stock faible : $nbAlertes"
            }
        }.start()
    }
}
