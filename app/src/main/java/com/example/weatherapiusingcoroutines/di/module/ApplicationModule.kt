package com.example.weatherapiusingcoroutines.di.module

import android.app.Application
import android.content.Context
import com.example.weatherapiusingcoroutines.di.qualifier.ApplicationContext
import com.example.weatherapiusingcoroutines.service.ApiService
import com.example.weatherapiusingcoroutines.service.ApiServiceFactory
import com.example.weatherapiusingcoroutines.service.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return app
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideService():ApiService{
        return ApiServiceFactory.makeService()
    }

    @Provides
    @Singleton
    fun provideRepository(service:ApiService):Repository{
        return Repository(service)
    }

}