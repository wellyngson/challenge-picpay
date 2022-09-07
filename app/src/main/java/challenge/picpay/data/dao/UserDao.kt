package challenge.picpay.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import challenge.picpay.data.model.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto")
    fun getUsers(): List<UserDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserDto(userDto: UserDto)

    @Update
    fun update(userDto: UserDto)
}
