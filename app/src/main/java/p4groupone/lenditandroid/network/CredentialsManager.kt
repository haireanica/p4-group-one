package p4groupone.lenditandroid.network

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialsManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences = EncryptedSharedPreferences.create(
        context,
        "lendit_credentials",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCredentials(username: String, password: String) {
        preferences.edit()
            .putString("username", username)
            .putString("password", password)
            .putBoolean("remember_me", true)
            .apply()
    }

    fun getUsername(): String {
        return preferences.getString("username", "") ?: ""
    }

    fun getPassword(): String {
        return preferences.getString("password", "") ?: ""
    }

    fun isRememberMeEnabled(): Boolean {
        return preferences.getBoolean("remember_me", false)
    }

    fun clearCredentials() {
        preferences.edit().clear().apply()
    }
}