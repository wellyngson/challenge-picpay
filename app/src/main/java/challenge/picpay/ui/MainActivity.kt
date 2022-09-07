package challenge.picpay.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import challenge.picpay.databinding.ActivityMainBinding
import challenge.picpay.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        goToHome()
    }

    private fun goToHome() {
        supportFragmentManager.commit(true) {
            replace(viewBinding.container.id, HomeFragment.newInstance(), HomeFragment::class.java.name)
        }
    }
}
