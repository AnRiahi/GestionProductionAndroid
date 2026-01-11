package com.example.gestiondeproduction.data.dao

import androidx.room.*
import com.example.gestiondeproduction.data.entity.CategorieEntity

@Dao
interface CategorieDao {

    @Insert
    fun insert(categorie: CategorieEntity)

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategorieEntity>

    @Delete
    fun delete(categorie: CategorieEntity)

    @Update
    fun update(categorie: CategorieEntity)

}
