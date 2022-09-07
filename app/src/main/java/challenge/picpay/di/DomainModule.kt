package challenge.picpay.di

import challenge.picpay.domain.usecase.GetUsersUseCase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetUsersUseCase(userRepository = get())
    }
}

object DomainModule {
    fun load() {
        loadKoinModules(useCaseModule)
    }
}
