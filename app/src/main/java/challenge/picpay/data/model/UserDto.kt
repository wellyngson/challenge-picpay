package challenge.picpay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDto(
    @PrimaryKey
    val img: String,
    val name: String,
    val id: Int,
    val username: String
)
