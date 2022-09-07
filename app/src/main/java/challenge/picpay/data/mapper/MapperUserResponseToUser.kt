package challenge.picpay.data.mapper

import challenge.picpay.data.model.UserResponse
import challenge.picpay.domain.model.User
import challenge.picpay.utils.Mapper

class MapperUserResponseToUser : Mapper<UserResponse, User> {
    override fun map(source: UserResponse): User =
        User(
            id = source.id,
            name = source.name,
            username = source.username,
            img = source.img
        )
}
