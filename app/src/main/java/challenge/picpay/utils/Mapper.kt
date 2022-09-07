package challenge.picpay.utils

interface Mapper<S, T> {
    fun map(source: S): T
}
