package com.example.garajeapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehiculo::class, MarcaInfo::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vehiculoDao(): VehiculoDao
    abstract fun marcaInfoDao(): MarcaInfoDao

    companion object {

        @Volatile
        private var instancia: AppDatabase? = null

        fun obtener(context: Context): AppDatabase {

            return instancia ?: synchronized(this) {

                val nueva = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "garaje_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                instancia = nueva
                nueva
            }
        }
    }
}
