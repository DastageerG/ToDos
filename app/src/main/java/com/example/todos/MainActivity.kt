package com.example.todos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragmentHost))

    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.fragmentHost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}