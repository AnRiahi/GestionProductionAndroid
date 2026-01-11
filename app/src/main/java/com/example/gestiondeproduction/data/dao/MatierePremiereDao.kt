package com.example.gestiondeproduction.data.dao

import androidx.room.*
import com.example.gestiondeproduction.data.entity.MatierePremiereEntity

@Dao
interface MatierePremiereDao {

    @Insert
    fun insert(mp: MatierePremiereEntity)

    @Query("SELECT * FROM matiere_premiere")
    fun getAll(): List<MatierePremiereEntity>

    @Delete
    fun delete(mp: MatierePremiereEntity)

    @Query("""
UPDATE matiere_premiere
SET stock = stock - :qte
WHERE id = :matiereId
""")
    fun decreaseStock(matiereId: Int, qte: Double)
    @Query("""
SELECT * FROM matiere_premiere
WHERE stock <= seuil
""")
    fun getStockFaible(): List<MatierePremiereEntity>


}
