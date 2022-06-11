package com.kl3jvi.pomodroid.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.menu.getItem(3).isEnabled = false
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {
            startActivity(Intent(this, AddUpdateToDoList::class.java))
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.diary -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                Toast.makeText(this, "Intent Started!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                Toast.makeText(this, "Calendar button!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CalendarActivity2::class.java)
                startActivity(intent)
                true
            }
        }
    }
}