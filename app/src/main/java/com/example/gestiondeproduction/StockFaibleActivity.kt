package com.example.gestiondeproduction

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondeproduction.data.database.AppDatabase

class StockFaibleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_faible)
        val listView = findViewById<ListView>(R.id.listViewStockFaible)
        val db = AppDatabase.getDatabase(this)
        Thread {
            val matieres = db.matierePremiereDao().getStockFaible()
            runOnUiThread {
                if (matieres.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Aucun stock faible ✅",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val display = matieres.map {
                        "⚠️ ${it.nom}  | Stock = ${it.stock}"
                    }
                    listView.adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        display
                    )
                }
            }
        }.start()
    }
}
