package challenge.picpay.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Error : Exception() {
    object NoConnectionError : Error()
    object ServerError : Error()
}

fun <T> Flow<T>.mapToCustomError(): Flow<T> = catch {
    throw it.getGenericOrDefaultThrowable()
}

private fun Throwable.getGenericOrDefaultThrowable() = when (this) {
    is SocketTimeoutException, is UnknownHostException -> Error.NoConnectionError
    is HttpException -> Error.ServerError
    else -> this
}
