package challenge.picpay.data.mapper

import challenge.picpay.data.model.UserDto
import challenge.picpay.domain.model.User
import challenge.picpay.utils.Mapper

class MapperUserDtoToUser : Mapper<UserDto, User> {
    override fun map(source: UserDto): User =
        User(
            id = source.id,
            name = source.name,
            username = source.username,
            img = source.img
        )
}
