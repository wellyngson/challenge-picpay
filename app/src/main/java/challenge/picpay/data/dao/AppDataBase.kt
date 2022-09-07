package challenge.picpay.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import challenge.picpay.data.model.UserDto

@Database(entities = [UserDto::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "picpay_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
