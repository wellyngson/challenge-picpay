package challenge.picpay.data.providers

import retrofit2.Retrofit

object ApiFactory {

    fun <T> build(retrofit: Retrofit, apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}
