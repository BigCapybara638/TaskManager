package com.example.testkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.di.appModule
import com.example.testkmp.di.sharedModule
import com.example.testkmp.di.viewModelModule
import com.example.testkmp.presentation.CategoriesItem
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.TaskItem
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(modifier: Modifier) {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        TaskManagerTheme {

            var showContent by remember { mutableStateOf(false) }
            val viewModel: HomeViewModel = koinViewModel()

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = modifier.padding(horizontal = 6.dp),
                userScrollEnabled = true,
            ) {

                val itemList = viewModel.loadCatsData()

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)

                    ) {
                        Text(
                            text = "Категории",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(top = 18.dp, bottom = 6.dp)
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth(0.85F)
                            .height(2.dp)
                            .background(Color.Gray)
                        )
                    }

                }
                items(itemList) { item ->
                    CategoriesItem(
                        item,
                        {

                    })
                }
            }
        }
    }
}