package com.example.testkmp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.ActionButtonColor
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.presentation.AuthViewModel
import com.example.testkmp.presentation.DataState
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.AddCategoryDialog
import com.example.testkmp.presentation.components.CategoriesItem
import io.github.jan.supabase.auth.auth
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    onNavigateToSignIn: () -> Unit,
    ) {
    TaskManagerTheme {

        val viewModel: HomeViewModel = koinViewModel()
        val authViewModel: AuthViewModel = koinViewModel()


        // collectAsState - не привязан к жц, collectAsStateWithLifecycle - привязан, актулально только для Android
        val dataState by viewModel.dataState.collectAsState()
        var showAddCategoryDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel.loadCatsData(supabase.auth.currentSessionOrNull()!!.user!!.id)
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showAddCategoryDialog = true
                    },
                    containerColor = ActionButtonColor,
                ) {
                    Text("+")
                }
            },

            floatingActionButtonPosition = FabPosition.End
        ) {
            when(val state = dataState) {
                is DataState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 14.dp)

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
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Не удалось загрузить",
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

                DataState.Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp)

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

                        CircularProgressIndicator(
                            strokeWidth = 10.dp,
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .size(70.dp)

                        )
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

                                Text(
                                    text = "Выйти",
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .clickable{
                                            authViewModel.signOut()
                                            onNavigateToSignIn()
                                        }
                                )
                            }

                        }
                        items(state.data) { item ->
                            CategoriesItem(
                                item,
                                {

                                }
                            )
                        }
                    }
                }
            }
        }

        if (showAddCategoryDialog) {
            AddCategoryDialog(
                onDismiss = { showAddCategoryDialog = false },
                onConfirm = { title, description ->
                    viewModel.addCategory(Categories(name = title, description = description!!, userId = supabase.auth.currentSessionOrNull()!!.user!!.id))
                    showAddCategoryDialog = false
                }
            )
        }
    }
}