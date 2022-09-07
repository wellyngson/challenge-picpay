package challenge.picpay.domain.usecase

import challenge.picpay.data.repository.UserRepository
import challenge.picpay.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val userRepository: UserRepository) {

    fun execute(): Flow<List<User>> = userRepository.getAllUser()
}
