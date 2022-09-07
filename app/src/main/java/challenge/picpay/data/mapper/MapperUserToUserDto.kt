package challenge.picpay.data.mapper

import challenge.picpay.data.model.UserDto
import challenge.picpay.domain.model.User
import challenge.picpay.utils.Mapper

class MapperUserToUserDto : Mapper<User, UserDto> {
    override fun map(source: User): UserDto =
        UserDto(
            id = source.id,
            name = source.name,
            username = source.username,
            img = source.img
        )
}
