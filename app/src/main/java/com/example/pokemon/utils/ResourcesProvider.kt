package com.example.pokemon.utils

import android.content.Context
import com.example.pokemon.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesProvider @Inject constructor(@ApplicationContext private val context: Context) {

    val genericErrorMessage by lazy { context.getString(R.string.generic_error_message) }
}