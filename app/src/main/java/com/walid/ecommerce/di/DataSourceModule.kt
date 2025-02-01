package com.walid.ecommerce.di

import com.walid.ecommerce.data.source.local.LocalDataSourceImpl
import com.walid.ecommerce.data.source.local.ProductFavoriteDAO
import com.walid.ecommerce.data.source.remote.ProductService
import com.walid.ecommerce.data.source.remote.RemoteDataSourceImpl
import com.walid.ecommerce.domain.datasource.local.LocalDataSource
import com.walid.ecommerce.domain.datasource.remote.RemoteDataSource
import com.walid.ecommerce.domain.repository.Authenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        productService: ProductService,
        authenticator: Authenticator
    ): RemoteDataSource = RemoteDataSourceImpl(productService, authenticator)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        productFavoriteDAO: ProductFavoriteDAO,
        ioDispatcher: CoroutineContext
    ): LocalDataSource = LocalDataSourceImpl(productFavoriteDAO, ioDispatcher)

}