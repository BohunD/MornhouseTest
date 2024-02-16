package com.bohunapps.mornhousetest.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bohunapps.mornhousetest.presentation.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun NumInfoScreen(vm: MainViewModel, navController: NavController) {
    val scope = rememberCoroutineScope()
    BackHandler() {
        navController.popBackStack()
        vm.clear()
        scope.launch{
            vm.updateFromDB()
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        vm.numInfo.value?.let {
            if(it.isNotEmpty()) {
                Text(
                    text = it.substring(0, it.indexOf(' ')),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(text = it)
        }
        
        Button(modifier = Modifier.padding(top = 30.dp),
            onClick = {
                navController.popBackStack()
            vm.clear()
            scope.launch{
                vm.updateFromDB()
            }
             }) {
            Text(text = "To home screen")
        }
    }
}