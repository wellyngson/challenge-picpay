package challenge.picpay

import android.app.Application
import challenge.picpay.di.DataModule
import challenge.picpay.di.DomainModule
import challenge.picpay.di.PresentationModule
import org.koin.android.ext.koin.androidContext

class PicPayApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin()
        loadModules()
    }

    private fun loadModules() {
        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@PicPayApp)
        }
    }
}
