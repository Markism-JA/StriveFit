package com.marky.strivefit.di

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyManager @Inject constructor(private val context: Context) {

    private val apiKeys = listOf(
        "172aebe54amshfdaa6eb3183bfd0p16dcd1jsn6dca19e7e9c3"
    )

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        storeApiKeys()
    }

    private fun storeApiKeys() {
        val apiKeysString = apiKeys.joinToString(separator = ",")
        sharedPreferences.edit() { putString("API_KEYS", apiKeysString) }
    }

    fun getApiKeys(): List<String> {
        val apiKeysString = sharedPreferences.getString("API_KEYS", "")
        return apiKeysString?.split(",") ?: emptyList()
    }
}
