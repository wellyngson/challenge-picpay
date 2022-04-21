package challenge.picpay.data.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
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
