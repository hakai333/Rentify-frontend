package cl.MyMGroup.rentify.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.MyMGroup.rentify.data.dao.PackDao
import cl.MyMGroup.rentify.data.entity.PackEntity

@Database(entities = [PackEntity::class], version = 1)
abstract class RentifyDataBase : RoomDatabase() {
    abstract fun packDao(): PackDao
}