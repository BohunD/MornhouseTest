package com.bohunapps.mornhousetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bohunapps.mornhousetest.ui.theme.MornhouseTestTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String){
    object FirstScreen: Destination("first_screen")
    object SecondScreen: Destination("second_screen{numId}")
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val chooseOptionVM by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MornhouseTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Destination.FirstScreen.route)  {
                        composable(Destination.FirstScreen.route) { EnterNumberScreen(navController,chooseOptionVM) }
                        composable(Destination.SecondScreen.route) { NumInfoScreen(chooseOptionVM, navController) }

                    }
                }
            }
        }
    }
}

