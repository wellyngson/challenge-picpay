package challenge.picpay.utils

import challenge.picpay.data.model.User
import challenge.picpay.data.model.UserDto

fun User.toUserDto(): UserDto =
    UserDto(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.img
    )

fun UserDto.toUser(): User =
    User(
        id = this.id,
        name = this.name,
        username = this.username,
        img = this.img
    )
