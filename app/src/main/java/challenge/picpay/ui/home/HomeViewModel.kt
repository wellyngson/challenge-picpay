package challenge.picpay.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import challenge.picpay.data.model.UserState
import challenge.picpay.data.repository.UserRepository
import challenge.picpay.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _users = MutableStateFlow<UserState>(UserState.Initial)
    val users: StateFlow<UserState> get() = _users

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch(dispatcher) {
            _users.value = UserState.Loading

            val response = userRepository.getAllUser()

            _users.value = response
        }
    }
}
