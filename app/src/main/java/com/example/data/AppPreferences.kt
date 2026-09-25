package com.example.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("crepe_algeria_prefs", Context.MODE_PRIVATE)

    var customerName: String
        get() = prefs.getString("customer_name", "") ?: ""
        set(value) = prefs.edit().putString("customer_name", value).apply()

    var customerPhone: String
        get() = prefs.getString("customer_phone", "") ?: ""
        set(value) = prefs.edit().putString("customer_phone", value).apply()

    var customerWilaya: String
        get() = prefs.getString("customer_wilaya", "16 - Alger") ?: "16 - Alger"
        set(value) = prefs.edit().putString("customer_wilaya", value).apply()

    var customerCommune: String
        get() = prefs.getString("customer_commune", "Alger Centre") ?: "Alger Centre"
        set(value) = prefs.edit().putString("customer_commune", value).apply()

    var customerAddress: String
        get() = prefs.getString("customer_address", "") ?: ""
        set(value) = prefs.edit().putString("customer_address", value).apply()

    var selectedBranchId: String
        get() = prefs.getString("selected_branch", "alger_centre") ?: "alger_centre"
        set(value) = prefs.edit().putString("selected_branch", value).apply()

    fun getFavoriteIds(): Set<String> {
        return prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
    }

    fun toggleFavorite(productId: String) {
        val current = getFavoriteIds().toMutableSet()
        if (current.contains(productId)) {
            current.remove(productId)
        } else {
            current.add(productId)
        }
        prefs.edit().putStringSet("favorite_ids", current).apply()
    }

    fun isFavorite(productId: String): Boolean {
        return getFavoriteIds().contains(productId)
    }
}
