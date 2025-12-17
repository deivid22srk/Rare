package com.rare.launcher.utils

object Constants {
    const val EPIC_CLIENT_ID = "34a02cf8f4414e29b15921876da36f9a"
    const val EPIC_CLIENT_SECRET = "daafbccc737745039dffe53d94fc76cf"
    
    const val PREF_NAME = "rare_prefs"
    const val KEY_ACCESS_TOKEN = "access_token"
    const val KEY_REFRESH_TOKEN = "refresh_token"
    const val KEY_EXPIRES_AT = "expires_at"
    const val KEY_ACCOUNT_ID = "account_id"
    const val KEY_DISPLAY_NAME = "display_name"
    
    const val LOGIN_URL = "https://www.epicgames.com/id/login"
    const val REDIRECT_URL = "https://www.epicgames.com/id/api/redirect"
    
    fun getAuthUrl(): String {
        return "$LOGIN_URL?redirectUrl=$REDIRECT_URL?clientId=$EPIC_CLIENT_ID&responseType=code"
    }
}
