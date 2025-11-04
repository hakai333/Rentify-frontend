package cl.MyMGroup.rentify.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.MyMGroup.rentify.data.entity.PackEntity

@Dao
interface PackDao {
    @Query("SELECT * FROM packs WHERE categoria = :categoria")
    suspend fun getPacksByCategoria(categoria: String): List<PackEntity>

    @Query("SELECT * FROM packs WHERE destacado = 1")
    suspend fun getDestacados(): List<PackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(packs: List<PackEntity>)

    @Query("DELETE FROM packs")
    suspend fun clearAll()
}