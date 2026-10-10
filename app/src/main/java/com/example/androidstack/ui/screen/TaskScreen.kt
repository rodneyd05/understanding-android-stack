package com.example.androidstack.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstack.data.Task

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = viewModel()
) {

    val task by viewModel.tasks.collectAsStateWithLifecycle()
    val newTaskTitle by viewModel.newTaskTitle.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.padding(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = newTaskTitle,
                onValueChange = viewModel::onTitleChange,
                placeholder = {
                    Text("Enter a new task")
                },
                singleLine = true
            )

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = viewModel::addTask
            ) {
                Text("Add")
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "Error",
            color = MaterialTheme.colorScheme.error
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(emptyList<Task>()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {

                        }
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Learn Jetpack Compose",

                    )

                    TextButton(
                        onClick = viewModel::addTask
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}