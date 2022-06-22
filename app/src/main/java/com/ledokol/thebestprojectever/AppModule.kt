package com.ledokol.thebestprojectever

import android.content.Context
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import com.ledokol.thebestprojectever.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): StockApplication {
        return app as StockApplication
    }

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}