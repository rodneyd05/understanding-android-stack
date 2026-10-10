package com.example.androidstack.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TaskRepository(firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    private val tasksCollection = firestore.collection("tasks")

    fun collectTasks(): Flow<List<Task>> = callbackFlow {

        val registration = tasksCollection.addSnapshotListener { snapshot, exception ->

            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }

            val tasks = snapshot?.documents.orEmpty().mapNotNull { document ->
                document.toObject(Task::class.java)
                    ?.copy(id = document.id)
            }

            trySend(tasks)
        }

        awaitClose {
            registration.remove()
        }
    }

    suspend fun addTask(title: String) {
        tasksCollection.add(
            mapOf(
                "title" to title,
                "compiled" to false
            )
        ).await()
    }

    suspend fun updateTask(
        taskId: String,
        completed: Boolean
    ) {
        tasksCollection.document(taskId)
            .update("completed", completed)
            .await()
    }

    suspend fun deleteTask(taskId: String) {
        tasksCollection
            .document(taskId)
            .delete()
            .await()

    }
}