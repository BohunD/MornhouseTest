package com.bohunapps.mornhousetest

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bohunapps.mornhousetest.room.NumbersEntity
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNumberScreen(navController: NavHostController, vm: MainViewModel) {
    val scope = rememberCoroutineScope()
    scope.launch {
        vm.updateFromDB()
    }
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = vm.currentNum.value,
                onValueChange = {
                    vm.setCurrentNum(it)
                },
                isError = !vm.numIsCorrect.value,
                label = { Text(text = "Enter number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

                Button(onClick = {
                    if(vm.numIsCorrect.value) {
                        vm.getNumInfo()
                        navController.navigate(Destination.SecondScreen.route)
                    }
                }) {
                    Text(text = "Get fact")
                }
            Text(text = "OR")
                Button(onClick = {
                    vm.getRandomInfo()
                    navController.navigate(Destination.SecondScreen.route)
                }) {
                    Text(text = "Get fact about random num", textAlign = TextAlign.Center)
                }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), verticalArrangement = Arrangement.Center
        ) {
            val list = remember {
                mutableStateOf(listOf<NumbersEntity?>(null))
            }
            SideEffect {
                scope.launch {
                    vm.numsFromDB.collect {
                        list.value = it.reversed()
                    }
                }
            }
            LazyRow(content = {
                items(list.value) {
                    it?.info?.let { it1 -> ListItem(info = it1, vm, navController) }
                }
            })
        }
    }
}

@Composable
fun ListItem(info: String, vm: MainViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(2.dp).clickable {
                vm.setNumberInfo(info)
                navController.navigate(Destination.SecondScreen.route)

            },
    ) {
        Text(text = info)
    }
}