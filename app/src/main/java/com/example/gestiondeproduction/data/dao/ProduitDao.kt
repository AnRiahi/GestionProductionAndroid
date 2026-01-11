package com.example.gestiondeproduction.data.dao

import androidx.room.*
import com.example.gestiondeproduction.data.entity.ProduitEntity

@Dao
interface ProduitDao {

    @Query("SELECT * FROM produit WHERE categorieId = :categorieId")
    fun getByCategorie(categorieId: Int): List<ProduitEntity>

    @Query("SELECT * FROM produit")
    fun getAll(): List<ProduitEntity>

    @Insert
    fun insert(produit: ProduitEntity)

    @Delete
    fun delete(produit: ProduitEntity)
}

