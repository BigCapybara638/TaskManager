package com.example.testkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testkmp.di.sharedModule
import com.example.testkmp.di.viewModelModule
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.TaskItem
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(modifier: Modifier) {
    KoinApplication(
        application = {
            modules(sharedModule, viewModelModule)

        }

    ) {

        MaterialTheme {

            var showContent by remember { mutableStateOf(false) }
            val viewModel: HomeViewModel = koinViewModel()


            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    modifier = modifier,
                    userScrollEnabled = true,
                ) {

                    val itemList = viewModel.loadData()

                    items(itemList) { item ->
                        TaskItem(item)
                    }
                }
            }
        }
    }
}