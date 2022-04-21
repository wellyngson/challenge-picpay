package challenge.picpay.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import challenge.picpay.data.model.UserDto

@Database(entities = [UserDto::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
