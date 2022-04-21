package challenge.picpay.utils

import challenge.picpay.data.model.User
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

    fun generateListUser(
        size: Int = faker.random.nextInt(1, 10)
    ): List<User> {
        val playlists = mutableListOf<User>()

        repeat(size) {
            playlists.add(generateUser())
        }

        return playlists.toList()
    }
}
