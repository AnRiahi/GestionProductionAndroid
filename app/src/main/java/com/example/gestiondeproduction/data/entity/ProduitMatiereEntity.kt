package com.example.gestiondeproduction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produit_matiere")
data class ProduitMatiereEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val produitId: Int,
    val matiereId: Int,
    val quantite: Double
)
