package com.example.gestiondeproduction.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "matiere_premiere",
    foreignKeys = [
        ForeignKey(
            entity = CategorieEntity::class,
            parentColumns = ["id"],
            childColumns = ["categorieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categorieId"])]
)
data class MatierePremiereEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val categorieId: Int,
    val stock: Double = 0.0,
    val seuil: Double = 5.0

)
