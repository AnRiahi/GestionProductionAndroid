package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class HistoriqueProductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        val db = AppDatabase.getDatabase(this)
        val listView = findViewById<ListView>(R.id.listViewHistorique)

        Thread {
            val productions = db.productionDao().getAll()
            val produits = db.produitDao().getAll()

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            val display = productions.map {
                val prod = produits.find { p -> p.id == it.produitId }
                "${prod?.nom} → ${it.quantite} unités\n${sdf.format(Date(it.date))}"
            }

            runOnUiThread {
                listView.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    display
                )
            }
        }.start()
    }
}
