package com.example.testkmp.presentation.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.ActionButtonColor
import com.example.testkmp.EnabledActionButtonColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.AuthViewModel
import com.example.testkmp.presentation.DataState
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.dialogs.AddCategoryDialog
import com.example.testkmp.presentation.components.items.CategoriesItem
import com.example.testkmp.presentation.components.GigachatIsland
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
        val tasksState by viewModel.tasksState.collectAsState()
        val floatingButtonState by viewModel.floatingButtonState.collectAsState()
        var showAddCategoryDialog by remember { mutableStateOf(false) }

        val userId = supabase.auth.currentSessionOrNull()!!.user!!.id

        LaunchedEffect(Unit) {
            viewModel.loadCatsData(userId)
        }

        Scaffold(
            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "↻",
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (floatingButtonState) ActionButtonColor else EnabledActionButtonColor)
                            .clickable {
                                if (floatingButtonState) {
                                    viewModel.loadTasksData(userId)
                                    viewModel.updateFloatingButtonStateFalse()
                                }
                            }
                            .padding(bottom = 4.dp)
                    )


                    FloatingActionButton(
                        onClick = {
                            showAddCategoryDialog = true
                        },
                        containerColor = ActionButtonColor,
                    ) {
                        Text("+")
                    }
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
                        HeaderColumn()

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
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
                        HeaderColumn()

                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(top = 60.dp)
                                .size(90.dp)
                        )
                    }
                }

                is DataState.Success -> {
                    val tasksByCategory = remember(tasksState, state.data) {
                        derivedStateOf {
                            when (tasksState) {
                                is DataState.Error -> emptyMap()
                                is DataState.Loading -> emptyMap()
                                is DataState.Success -> {
                                    (tasksState as DataState.Success<List<Task>>).data
                                        .groupBy { it.category_id }
                                }
                            }
                        }
                    }

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
                                HeaderColumn()

                                GigachatIsland()

                                Text(
                                    text = "Выйти",
                                    fontSize = 24.sp,
                                    modifier = Modifier.clickable {
                                        authViewModel.signOut()
                                        onNavigateToSignIn()
                                    }
                                )
                            }
                        }

                        items(
                            items = state.data,
                            key = { it.id!! },
                            ) { item ->
                            val categoryTasks = tasksByCategory.value[item.id] ?: emptyList()

                            CategoriesItem(
                                viewModel = viewModel,
                                category = item,
                                tasksList = categoryTasks,
                                modifier = Modifier.animateItem(),
                                onConfirm = { title, description ->
                                    viewModel.addTask(
                                    Task(
                                        name = title,
                                        description = description,
                                        category_id = item.id!!,
                                        user_id = userId
                                    )
                                )}
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

@Composable
fun HeaderColumn() {
    Text(
        text = "Категории",
        fontSize = 24.sp,
        modifier = Modifier.padding(top = 18.dp, bottom = 6.dp)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth(0.85F)
            .height(2.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(PrimaryTextColor)
    )
}