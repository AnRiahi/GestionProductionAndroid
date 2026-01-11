package com.example.gestiondeproduction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produit")
data class ProduitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val categorieId: Int
)
