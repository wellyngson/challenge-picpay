package challenge.picpay.di

import challenge.picpay.data.repository.UserRepository
import challenge.picpay.data.repository.UserRepositoryImpl
import challenge.picpay.data.repository.datasource.local.UserLocalDataSource
import challenge.picpay.data.repository.datasource.local.UserLocalDataSourceImpl // ktlint-disable no-wildcard-imports
import challenge.picpay.data.repository.datasource.remote.UserRemoteDataSource
import challenge.picpay.data.repository.datasource.remote.UserRemoteDataSourceImpl
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
