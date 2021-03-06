package com.example.encryptroom

import androidx.lifecycle.ViewModel
import com.example.encryptroom.database.Secret
import com.example.encryptroom.database.SecretDatabaseDao
import kotlinx.coroutines.*


class MainActivityViewModel(private val database: SecretDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val allSecrets = database.getAllSecrets()

    fun onInsertSecret(secret: Secret) {
        uiScope.launch {
            insertSecret(secret)
        }
    }

    private suspend fun insertSecret(secret: Secret) {
        withContext(Dispatchers.IO) {
            database.insertSecret(secret)
        }
    }

    fun onClearSecrets() {
        uiScope.launch {
            clearSecrets()
        }
    }

    private suspend fun clearSecrets() {
        withContext(Dispatchers.IO) {
            database.clearSecrets()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}