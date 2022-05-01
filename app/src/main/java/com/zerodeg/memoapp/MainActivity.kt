package com.zerodeg.memoapp

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.zerodeg.memoapp.ui.PasswordDialog
import com.zerodeg.memoapp.ui.main.MainFragment
import com.zerodeg.memoapp.vm.NoteListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.getInstance())
                .commitNow()

//            navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
//            navController = navHostFragment.navController
        }

    }
}