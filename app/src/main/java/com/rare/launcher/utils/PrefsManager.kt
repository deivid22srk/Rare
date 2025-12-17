package com.rare.launcher.utils

import android.content.Context
import android.content.SharedPreferences
import com.rare.launcher.model.AuthResponse

class PrefsManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREF_NAME,
        Context.MODE_PRIVATE
    )
    
    fun saveAuthData(authResponse: AuthResponse) {
        prefs.edit().apply {
            putString(Constants.KEY_ACCESS_TOKEN, authResponse.accessToken)
            putString(Constants.KEY_REFRESH_TOKEN, authResponse.refreshToken)
            putString(Constants.KEY_EXPIRES_AT, authResponse.expiresAt)
            putString(Constants.KEY_ACCOUNT_ID, authResponse.accountId)
            putString(Constants.KEY_DISPLAY_NAME, authResponse.displayName)
            apply()
        }
    }
    
    fun getAccessToken(): String? {
        return prefs.getString(Constants.KEY_ACCESS_TOKEN, null)
    }
    
    fun getRefreshToken(): String? {
        return prefs.getString(Constants.KEY_REFRESH_TOKEN, null)
    }
    
    fun getDisplayName(): String? {
        return prefs.getString(Constants.KEY_DISPLAY_NAME, null)
    }
    
    fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }
    
    fun clearAuthData() {
        prefs.edit().clear().apply()
    }
}
