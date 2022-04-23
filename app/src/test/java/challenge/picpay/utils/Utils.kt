package challenge.picpay.utils

import challenge.picpay.data.model.User
import challenge.picpay.data.model.UserDto
import io.github.serpro69.kfaker.faker

object Utils {

    private val faker = faker {}

    fun generateUser(
        id: Int = faker.random.nextInt(1, 10),
        name: String = faker.random.nextString(),
        username: String = faker.random.nextString(),
        img: String = faker.random.nextString()
    ): User =
        User(id, name, username, img)

    fun generateUserDto(
        id: Int = faker.random.nextInt(1, 10),
        name: String = faker.random.nextString(),
        username: String = faker.random.nextString(),
        img: String = faker.random.nextString()
    ): UserDto =
        UserDto(
            id = id, name = name, username = username, img = img
        )

    fun generateListUser(
        size: Int = faker.random.nextInt(1, 10)
    ): List<User> {
        val users = mutableListOf<User>()

        repeat(size) {
            users.add(generateUser())
        }

        return users.toList()
    }

    fun generateListUserDto(
        size: Int = faker.random.nextInt(1, 10)
    ): List<UserDto> {
        val usersDto = mutableListOf<UserDto>()

        repeat(size) {
            usersDto.add(generateUserDto())
        }

        return usersDto.toList()
    }
}
