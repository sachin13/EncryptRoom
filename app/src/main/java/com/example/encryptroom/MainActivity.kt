package com.example.encryptroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.encryptroom.database.Secret
import com.example.encryptroom.database.SecretDatabase
import com.example.encryptroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val secretDatabaseDao = SecretDatabase.getInstance(application).secretDatabaseDao
        val viewModelFactory = MainActivityViewModelFactory(secretDatabaseDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        binding.saveButton.setOnClickListener {
            val editable = binding.inputEditText.text
            val secret = Secret(secretValue = editable.toString())
            viewModel.onInsertSecret(secret)
            editable.clear()
        }

        binding.clearButton.setOnClickListener {
            viewModel.onClearSecrets()
        }

        viewModel.allSecrets.observe(this) { secrets ->
            binding.valuesTextView.text = secrets.map { it.secretValue }.toString()
        }
    }
}