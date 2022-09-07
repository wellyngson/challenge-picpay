package challenge.picpay.di

import challenge.picpay.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            getUsersUseCase = get()
        )
    }
}

object PresentationModule {
    fun load() {
        loadKoinModules(viewModelModule)
    }
}
