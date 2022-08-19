package a77777_888.me.t.https.hhcustombasis

import a77777_888.me.t.https.hhcustombasis.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class

MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchMenuItem -> {
                findNavController(R.id.navContainer).navigate(R.id.searchFragment)
            }
            R.id.exitMenuItem -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (findNavController(R.id.navContainer).currentDestination?.id == R.id.searchFragment)
            finish()
        else
            super.onBackPressed()
    }
}

