package com.example.appprototype.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appprototype.R
import com.example.appprototype.data.model.Resource
import com.example.appprototype.databinding.ActivityMainBinding
import com.example.appprototype.ui.adapter.UserAdapter
import com.example.appprototype.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity - The View layer in MVVM architecture
 * Observes ViewModel's LiveData and updates UI accordingly
 * No business logic here - only UI updates
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        userAdapter = UserAdapter(
            onDeleteClick = { user ->
                viewModel.deleteUser(user)
            }
        )
        
        binding.recyclerViewUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
    
    private fun setupObservers() {
        // Observe users list
        viewModel.users.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { users ->
                        userAdapter.submitList(users)
                        binding.textViewEmpty.visibility = 
                            if (users.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        // Observe user operations (insert, update, delete)
        viewModel.userOperation.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.buttonAdd.isEnabled = false
                }
                is Resource.Success -> {
                    binding.buttonAdd.isEnabled = true
                    Toast.makeText(this, resource.data, Toast.LENGTH_SHORT).show()
                    clearInputFields()
                }
                is Resource.Error -> {
                    binding.buttonAdd.isEnabled = true
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.buttonAdd.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val ageText = binding.editTextAge.text.toString()
            
            val age = ageText.toIntOrNull() ?: 0
            
            viewModel.insertUser(name, email, age)
        }
    }
    
    private fun clearInputFields() {
        binding.editTextName.text?.clear()
        binding.editTextEmail.text?.clear()
        binding.editTextAge.text?.clear()
    }
}
