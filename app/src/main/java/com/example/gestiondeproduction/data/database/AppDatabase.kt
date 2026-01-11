package com.example.gestiondeproduction.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestiondeproduction.data.dao.*
import com.example.gestiondeproduction.data.entity.*
@Database(
    entities = [
        CategorieEntity::class,
        MatierePremiereEntity::class,
        ProduitEntity::class,
        ProduitMatiereEntity::class,
        ProductionEntity::class,
        UserEntity::class
    ],
    version = 7,    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categorieDao(): CategorieDao
    abstract fun matierePremiereDao(): MatierePremiereDao
    abstract fun produitDao(): ProduitDao
    abstract fun produitMatiereDao(): ProduitMatiereDao
    abstract fun productionDao(): ProductionDao
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestion_production_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
