package com.example.testkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.di.appModule
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.presentation.CategoriesItem
import com.example.testkmp.presentation.DataState
import com.example.testkmp.presentation.HomeViewModel
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

            val viewModel: HomeViewModel = koinViewModel()
            val dataState by viewModel.dataState.collectAsState()

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.addCategory(Categories(name = "1", description = "desc"))
                        },
                        containerColor = ActionButtonColor,
                    ) {
                        Text("+")
                    }
                },

                floatingActionButtonPosition = FabPosition.End // или FabPosition.Center
            ) {
                //var showContent by remember { mutableStateOf(false) }

                when(val state = dataState) {
                    is DataState.Error -> {

                    }

                    DataState.Loading -> {
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
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth(0.85F)
                                    .height(2.dp)
                                    .background(Color.Gray)
                            )

                            CircularProgressIndicator()
                        }
                    }

                    is DataState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(300.dp),
                            modifier = modifier.padding(horizontal = 6.dp),
                            userScrollEnabled = true,
                        ) {

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
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth(0.85F)
                                            .height(2.dp)
                                            .background(Color.Gray)
                                    )
                                }

                            }
                            items(state.data) { item ->
                                CategoriesItem(
                                    item,
                                    {

                                    })
                            }
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {
                viewModel.loadCatsData()
            }

        }
    }
}