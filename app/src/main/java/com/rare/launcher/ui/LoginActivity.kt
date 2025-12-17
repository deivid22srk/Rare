package com.rare.launcher.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rare.launcher.R
import com.rare.launcher.api.RetrofitClient
import com.rare.launcher.databinding.ActivityLoginBinding
import com.rare.launcher.utils.Constants
import com.rare.launcher.utils.PrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefsManager: PrefsManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefsManager = PrefsManager(this)
        setupToolbar()
        setupWebView()
        loadLoginPage()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportZoom(true)
            builtInZoomControls = false
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                
                if (url.contains("${Constants.REDIRECT_URL}?code=")) {
                    val code = Uri.parse(url).getQueryParameter("code")
                    if (!code.isNullOrEmpty()) {
                        handleAuthorizationCode(code)
                        return true
                    }
                }
                
                return false
            }
            
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    
    private fun loadLoginPage() {
        binding.progressBar.visibility = View.VISIBLE
        binding.webView.loadUrl(Constants.getAuthUrl())
    }
    
    private fun handleAuthorizationCode(code: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
        
        lifecycleScope.launch {
            try {
                val credentials = "${Constants.EPIC_CLIENT_ID}:${Constants.EPIC_CLIENT_SECRET}"
                val encodedCredentials = Base64.encodeToString(
                    credentials.toByteArray(),
                    Base64.NO_WRAP
                )
                
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.oauthApi.authenticate(
                        authorization = "Basic $encodedCredentials",
                        grantType = "authorization_code",
                        code = code
                    )
                }
                
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    prefsManager.saveAuthData(authResponse)
                    
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.login_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        
                        startActivity(Intent(this@LoginActivity, LibraryActivity::class.java))
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                        binding.webView.visibility = View.VISIBLE
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.login_failed),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.webView.visibility = View.VISIBLE
                    Toast.makeText(
                        this@LoginActivity,
                        "${getString(R.string.login_failed)}: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
