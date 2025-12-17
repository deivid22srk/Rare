package com.rare.launcher.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rare.launcher.R
import com.rare.launcher.api.RetrofitClient
import com.rare.launcher.databinding.ActivityLibraryBinding
import com.rare.launcher.model.GameRecord
import com.rare.launcher.utils.PrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLibraryBinding
    private lateinit var prefsManager: PrefsManager
    private lateinit var adapter: LibraryAdapter
    private val games = mutableListOf<GameRecord>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefsManager = PrefsManager(this)
        setupToolbar()
        setupRecyclerView()
        setupSwipeRefresh()
        loadLibrary()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }
    
    private fun setupRecyclerView() {
        adapter = LibraryAdapter(games)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LibraryActivity)
            adapter = this@LibraryActivity.adapter
        }
    }
    
    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadLibrary()
        }
    }
    
    private fun loadLibrary() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
        
        lifecycleScope.launch {
            try {
                val accessToken = prefsManager.getAccessToken()
                if (accessToken.isNullOrEmpty()) {
                    navigateToLogin()
                    return@launch
                }
                
                val allRecords = mutableListOf<GameRecord>()
                var cursor: String? = null
                
                do {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.libraryApi.getLibrary(
                            authorization = "bearer $accessToken",
                            includeMetadata = true,
                            cursor = cursor
                        )
                    }
                    
                    if (response.isSuccessful && response.body() != null) {
                        val libraryResponse = response.body()!!
                        allRecords.addAll(libraryResponse.records)
                        cursor = libraryResponse.responseMetadata.nextCursor
                    } else {
                        throw Exception("Failed to load library: ${response.code()}")
                    }
                } while (!cursor.isNullOrEmpty())
                
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    games.clear()
                    games.addAll(allRecords)
                    adapter.notifyDataSetChanged()
                    
                    if (games.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@LibraryActivity,
                        "${getString(R.string.library_error)}: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_library, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun logout() {
        prefsManager.clearAuthData()
        navigateToLogin()
    }
    
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
