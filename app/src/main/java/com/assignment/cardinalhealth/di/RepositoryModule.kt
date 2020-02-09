package com.assignment.cardinalhealth.di

import dagger.Module
import dagger.Provides
import com.assignment.cardinalhealth.api.FlickrApiService
import com.assignment.cardinalhealth.repository.FlickrRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(flickrApiService: FlickrApiService) = FlickrRepository(flickrApiService)

}