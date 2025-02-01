package com.walid.ecommerce.di
import com.walid.ecommerce.data.repository.ProductsRepositoryImpl
import com.walid.ecommerce.domain.datasource.local.LocalDataSource
import com.walid.ecommerce.domain.datasource.remote.RemoteDataSource
import com.walid.ecommerce.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {



    @Provides
    @Singleton
    fun provideProductsRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): ProductsRepository = ProductsRepositoryImpl(remoteDataSource, localDataSource)
}