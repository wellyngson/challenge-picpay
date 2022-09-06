package challenge.picpay.di

import challenge.picpay.data.datasource.local.UserLocalDataSource
import challenge.picpay.data.datasource.local.UserLocalDataSourceImpl
import challenge.picpay.data.datasource.remote.UserRemoteDataSource
import challenge.picpay.data.datasource.remote.UserRemoteDataSourceImpl
import challenge.picpay.data.repository.UserRepository
import challenge.picpay.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSource: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserLocalDataSource(
        userLocalDataSource: UserLocalDataSourceImpl
    ): UserLocalDataSource
}
