package challenge.picpay.data.model

import java.lang.Exception

sealed class UserState {
    object Initial : UserState()
    object Loading : UserState()
    data class Loaded(val users: List<User>) : UserState()
    data class Failed(val users: List<User>, val exception: Exception) : UserState()
}
