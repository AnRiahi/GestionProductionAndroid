package com.example.gestiondeproduction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "production")
data class ProductionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val produitId: Int,
    val quantite: Double,
    val date: Long = System.currentTimeMillis()
)
