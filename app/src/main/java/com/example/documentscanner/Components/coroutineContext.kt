package com.example.documentscanner.Components

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object coroutineContext {

    @Provides
    fun coroutineContext(): CoroutineContext = Dispatchers.IO
}