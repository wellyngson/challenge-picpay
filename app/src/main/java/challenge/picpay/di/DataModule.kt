package challenge.picpay.di

import challenge.picpay.BuildConfig
import challenge.picpay.data.dao.AppDataBase
import challenge.picpay.data.datasource.local.UserLocalDataSource
import challenge.picpay.data.datasource.local.UserLocalDataSourceImpl
import challenge.picpay.data.datasource.remote.UserRemoteDataSource
import challenge.picpay.data.datasource.remote.UserRemoteDataSourceImpl
import challenge.picpay.data.providers.ApiFactory
import challenge.picpay.data.providers.OkHttpClientFactory
import challenge.picpay.data.providers.RetrofitFactory
import challenge.picpay.data.repository.UserRepository
import challenge.picpay.data.repository.UserRepositoryImpl
import challenge.picpay.data.service.PicPayService
import challenge.picpay.utils.Constants.DEFAULT_DISPATCHER
import challenge.picpay.utils.Constants.DEFAULT_SCOPE
import challenge.picpay.utils.Constants.IO_DISPATCHER
import challenge.picpay.utils.Constants.MAIN_DISPATCHER
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    factory { ApiFactory.build(retrofit = get(), apiClass = PicPayService::class.java) }
}

val networkModule = module {
    single {
        OkHttpClientFactory.build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(GsonBuilder().create())
    }

    single {
        RetrofitFactory.build(url = BuildConfig.BASE_URL, client = get(), factory = get())
    }
}

val repositoryModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            dispatcher = get(named(IO_DISPATCHER))
        )
    }

    factory<UserRemoteDataSource> {
        UserRemoteDataSourceImpl(
            service = get()
        )
    }

    factory<UserLocalDataSource> {
        UserLocalDataSourceImpl(
            dao = get()
        )
    }
}

val daoModule = module {
    factory { AppDataBase.getDatabase(androidContext()).userDao }
}

val dispatcherModule = module {
    factory(named(DEFAULT_DISPATCHER)) { Dispatchers.Default }
    factory(named(IO_DISPATCHER)) { Dispatchers.IO }
    factory(named(MAIN_DISPATCHER)) { Dispatchers.Main }
    factory(named(DEFAULT_SCOPE)) { CoroutineScope(Dispatchers.Default) }
}

object DataModule {

    fun load() {
        loadKoinModules(
            networkModule + apiModule + repositoryModule + daoModule + dispatcherModule
        )
    }
}
