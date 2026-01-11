package com.example.gestiondeproduction.data.dao

import androidx.room.*
import com.example.gestiondeproduction.data.entity.ProductionEntity

@Dao
interface ProductionDao {

    @Insert
    fun insert(production: ProductionEntity)

    @Query("SELECT * FROM production ORDER BY date DESC")
    fun getAll(): List<ProductionEntity>
}
