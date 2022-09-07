package challenge.picpay.ui.model

import challenge.picpay.domain.model.User

sealed class ViewAction {
    object Initial : ViewAction()
    object Loading : ViewAction()
    data class UsersLoaded(val users: List<User>) : ViewAction()
    object NoConnectionError : ViewAction()
    object GenericError : ViewAction()
}
