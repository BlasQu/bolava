package com.example.bolava.dagger_hilt

import android.content.Context
import com.example.bolava.feature.user.UserActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object HiltModule {

    @Provides
    @ActivityScoped
    fun providesContext(@ActivityContext context: Context) : Context {
        return context
    }

}