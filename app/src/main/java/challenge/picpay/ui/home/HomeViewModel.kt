package challenge.picpay.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import challenge.picpay.domain.model.User
import challenge.picpay.domain.usecase.GetUsersUseCase
import challenge.picpay.ui.model.ViewAction
import challenge.picpay.utils.Error
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _viewAction = MutableLiveData<ViewAction>(ViewAction.Initial)
    val viewAction: LiveData<ViewAction> = _viewAction

    init {
        getUsers()
    }

    fun init() {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase.execute()
                .onStart { handleLoading() }
                .catch {
                    it.showConnectionErrorOrThrowable {
                        handleGenericError()
                    }
                }
                .collect(::handleGetUsers)
        }
    }

    private fun handleGetUsers(users: List<User>) {
        _viewAction.value = ViewAction.UsersLoaded(users)
    }

    private fun handleLoading() {
        _viewAction.value = ViewAction.Loading
    }

    private fun Throwable.showConnectionErrorOrThrowable(errorCallback: () -> Unit) {
        if (this is Error.NoConnectionError) {
            showNoConnectionError()
        } else {
            errorCallback()
        }
    }

    private fun showNoConnectionError() {
        _viewAction.value = ViewAction.NoConnectionError
    }

    private fun handleGenericError() {
        _viewAction.value = ViewAction.GenericError
    }
}
