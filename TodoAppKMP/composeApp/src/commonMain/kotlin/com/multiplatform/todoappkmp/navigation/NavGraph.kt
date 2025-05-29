package com.multiplatform.todoappkmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import com.multiplatform.todoappkmp.domain.MongoRepository
import com.multiplatform.todoappkmp.ui.screen.home.HomeScreen
import com.multiplatform.todoappkmp.ui.screen.home.HomeViewModel
import com.multiplatform.todoappkmp.ui.screen.task.TaskScreen
import com.multiplatform.todoappkmp.ui.screen.task.TaskViewModel
import com.multiplatform.todoappkmp.util.Constants.DEFAULT_DESCRIPTION
import com.multiplatform.todoappkmp.util.Constants.DEFAULT_TITLE

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Destination = Destination.Home,
    mongoDB: MongoRepository = koinInject<MongoRepository>()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Home> {
            val viewModel = viewModel { HomeViewModel(mongoDB) }
            val activeTasks by viewModel.activeTasks
            val completedTasks by viewModel.completedTasks
            HomeScreen(
                activeTasks = activeTasks,
                completedTasks = completedTasks,
                setAction = { viewModel.setAction(it) },
                navigateToTask = { taskId ->
                    navController.navigate(Destination.Task(taskId))
                }
            )
        }
        composable<Destination.Task> {
            val viewModel = viewModel { TaskViewModel(mongoDB) }
            val taskId = remember { it.arguments?.getString(TASK_ID) }
            val selectedTask = remember { taskId?.let { id -> viewModel.getSelectedTask(id) } }
            var currentTitle by remember {
                mutableStateOf(selectedTask?.title ?: DEFAULT_TITLE)
            }
            var currentDescription by remember {
                mutableStateOf(selectedTask?.description ?: DEFAULT_DESCRIPTION)
            }
            TaskScreen(
                task = selectedTask,
                title = currentTitle,
                onTitleChange = { currentTitle = it },
                description = currentDescription,
                onDescriptionChange = { currentDescription = it },
                setAction = {
                    viewModel.setAction(it)
                },
                navigateBack = {
                    navController.navigate(Destination.Home){
                        launchSingleTop = true
                        popUpTo(Destination.Home) {
                            inclusive = false
                        }
                    }
                }
            )
        }
    }
}