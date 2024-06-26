package com.whdaud.pillinTimeAndroid.di

import com.whdaud.pillinTimeAndroid.data.remote.CabinetService
import com.whdaud.pillinTimeAndroid.data.remote.FcmService
import com.whdaud.pillinTimeAndroid.data.remote.HealthService
import com.whdaud.pillinTimeAndroid.data.remote.MedicineService
import com.whdaud.pillinTimeAndroid.data.remote.PaymentService
import com.whdaud.pillinTimeAndroid.data.remote.RelationService
import com.whdaud.pillinTimeAndroid.data.remote.ScheduleService
import com.whdaud.pillinTimeAndroid.data.remote.SignInService
import com.whdaud.pillinTimeAndroid.data.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSignInService(retrofit: Retrofit): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideRelationService(retrofit: Retrofit): RelationService {
        return retrofit.create(RelationService::class.java)
    }

    @Provides
    @Singleton
    fun provideCabinetService(retrofit: Retrofit): CabinetService {
        return retrofit.create(CabinetService::class.java)
    }

    @Provides
    @Singleton
    fun provideMedicineService(retrofit: Retrofit): MedicineService {
        return retrofit.create(MedicineService::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleService(retrofit: Retrofit): ScheduleService {
        return retrofit.create(ScheduleService::class.java)
    }

    @Provides
    @Singleton
    fun provideFcmService(retrofit: Retrofit): FcmService {
        return retrofit.create(FcmService::class.java)
    }

    @Provides
    @Singleton
    fun provideHealthService(retrofit: Retrofit): HealthService {
        return retrofit.create(HealthService::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentService(retrofit: Retrofit): PaymentService {
        return retrofit.create(PaymentService::class.java)
    }
}