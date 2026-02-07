package com.ivansosa.recetingapp.di

import com.ivansosa.recetingapp.data.repository.MealsRepositoryImpl
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMealsRepository(
        mealsRepositoryImpl: MealsRepositoryImpl
    ): MealsRepository
}
