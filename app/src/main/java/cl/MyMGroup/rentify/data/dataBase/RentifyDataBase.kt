package cl.MyMGroup.rentify.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.MyMGroup.rentify.data.dao.PackDao
import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.entity.PackEntity
import cl.MyMGroup.rentify.data.entity.UsuarioEntity

@Database(
    entities = [PackEntity::class, UsuarioEntity::class],
    version = 5,
    exportSchema = false
)
abstract class RentifyDataBase : RoomDatabase() {
    abstract fun packDao(): PackDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: RentifyDataBase? = null

        fun genInstance(context: Context): RentifyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RentifyDataBase::class.java,
                    "rentify_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}