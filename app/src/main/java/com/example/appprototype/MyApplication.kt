package com.example.appprototype

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class annotated with @HiltAndroidApp
 * This triggers Hilt's code generation and sets up dependency injection
 */
@HiltAndroidApp
class MyApplication : Application()
