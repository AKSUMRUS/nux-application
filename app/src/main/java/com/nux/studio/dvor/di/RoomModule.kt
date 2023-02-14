package com.nux.studio.dvor.di

import android.content.Context
import androidx.room.Room
import com.nux.studio.dvor.data.local.MyDatabase
import com.nux.studio.dvor.data.local.contact.ContactsDao
import com.nux.studio.dvor.data.local.game.GamesDao
import com.nux.studio.dvor.data.local.notifications.NotificationsDao
import com.nux.studio.dvor.data.local.profile.ProfileDao
import com.nux.studio.dvor.data.local.token.TokenDao
import com.nux.studio.dvor.data.local.user.UsersDao
import com.nux.studio.dvor.data.remote.RetrofitServices
import com.nux.studio.dvor.data.remote.TokenInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "http://51.250.100.21"

@Module
@InstallIn(SingletonComponent::class)
interface RoomModule {

    companion object {

        @Provides
        @Singleton
        fun provideRoomDatabase(@ApplicationContext context: Context): MyDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "my_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()


        @Provides
        @Singleton
        fun provideProfileDao(myDatabase: MyDatabase): ProfileDao = myDatabase.profileDao()

        @Provides
        @Singleton
        fun provideUsersDao(myDatabase: MyDatabase): UsersDao = myDatabase.usersDao()

        @Provides
        @Singleton
        fun provideGamesDao(myDatabase: MyDatabase): GamesDao = myDatabase.gamesDao()

        @Provides
        @Singleton
        fun provideContactsDao(myDatabase: MyDatabase): ContactsDao = myDatabase.contactsDao()

        @Provides
        @Singleton
        fun provideNotificationsDao(myDatabase: MyDatabase): NotificationsDao =
            myDatabase.notificationDao()

        @Provides
        @Singleton
        fun provideTokenDao(myDatabase: MyDatabase): TokenDao =
            myDatabase.tokenDao()

        @Provides
        @Singleton
        fun provideRetrofitServices(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build()

        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            logging: HttpLoggingInterceptor,
            tokenInterceptor: Interceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS) // connect timeout
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        @Singleton
        fun provideRetrofitServices2(retrofit: Retrofit): RetrofitServices {
            return retrofit.create(RetrofitServices::class.java)
        }
    }

    @Binds
    @Singleton
    fun provideTokenInterceptor(tokenInterceptor: TokenInterceptor): Interceptor

}