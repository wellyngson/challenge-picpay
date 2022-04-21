package challenge.picpay.di

import android.app.Application
import androidx.room.Room
import challenge.picpay.data.dao.UserDao
import challenge.picpay.data.dao.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): UserDataBase {
        return Room
            .databaseBuilder(application, UserDataBase::class.java, "pokemon_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(userDataBase: UserDataBase): UserDao {
        return userDataBase.userDao()
    }
}
