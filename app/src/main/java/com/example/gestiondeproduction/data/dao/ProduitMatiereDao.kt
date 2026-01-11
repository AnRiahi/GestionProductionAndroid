package com.example.gestiondeproduction.data.dao
import androidx.room.*
import com.example.gestiondeproduction.data.entity.ProduitMatiereEntity
@Dao
interface ProduitMatiereDao {
    @Insert
    fun insert(pm: ProduitMatiereEntity)
    @Update
    fun update(pm: ProduitMatiereEntity)
    @Delete
    fun delete(pm: ProduitMatiereEntity)
    @Query("SELECT * FROM produit_matiere WHERE produitId = :produitId")
    fun getByProduit(produitId: Int): List<ProduitMatiereEntity>
    @Query("""
        SELECT * FROM produit_matiere 
        WHERE produitId = :produitId AND matiereId = :matiereId
        LIMIT 1
    """)
    fun getByProduitAndMatiere(
        produitId: Int,
        matiereId: Int
    ): ProduitMatiereEntity?
    @Query("""
SELECT MIN(m.stock / pm.quantite)
FROM produit_matiere pm
JOIN matiere_premiere m ON m.id = pm.matiereId
WHERE pm.produitId = :produitId
""")
    fun getProductionPossible(produitId: Int): Double?
    @Query("SELECT * FROM produit_matiere")
    fun getAll(): List<ProduitMatiereEntity>


}
