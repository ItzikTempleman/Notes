package com.itzik.notes_.project.di


import android.content.Context
import android.util.Log
import androidx.room.Room
import com.itzik.notes_.project.data.AppDatabase
import com.itzik.notes_.project.data.NoteDao
import com.itzik.notes_.project.data.UserDao
import com.itzik.notes_.project.repositories.AppRepository
import com.itzik.notes_.project.repositories.AppRepositoryInterface
import com.itzik.notes_.project.requests.UsersAndNotesService
import com.itzik.notes_.project.requests.WallpaperService
import com.itzik.notes_.project.utils.Constants.BASE_URL
import com.itzik.notes_.project.utils.Constants.MY_BACKEND_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        @Named("user_dao") userDao: UserDao,
        @Named("note_dao") noteDao: NoteDao,
        @Named("wallpaper_service") wallpaperService: WallpaperService,
        @Named("my_backend_service") usersAndNotesService: UsersAndNotesService,
    ): AppRepositoryInterface {
        return AppRepository(userDao, noteDao, wallpaperService, usersAndNotesService)
    }

    @Provides
    @Named("user_dao")
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.getUserDao()
    }

    @Provides
    @Named("note_dao")
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.getNoteDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Named("wallpaper_service")
    fun provideRetrofit(): WallpaperService {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build()).build()
        return retrofit.create(WallpaperService::class.java)
    }

    @Provides
    @Named("my_backend_service")
    fun provideBackEndRetrofit(): UsersAndNotesService {
        val retrofit =
            Retrofit.Builder().baseUrl(MY_BACKEND_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build()).build()
        return retrofit.create(UsersAndNotesService::class.java)
    }
}

