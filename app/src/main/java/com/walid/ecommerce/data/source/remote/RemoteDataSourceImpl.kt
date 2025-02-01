package com.walid.ecommerce.data.source.remote


import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.data.model.Product

import com.walid.ecommerce.domain.datasource.remote.RemoteDataSource
import com.walid.ecommerce.domain.repository.Authenticator
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val productService: ProductService,
    private val authenticator: Authenticator
) : RemoteDataSource {





    override suspend fun getProducts(): List<Product> =
        productService.getProducts(authenticator.getFirebaseUserUid())


    override suspend fun getSaleProducts(): List<Product> =
        productService.getSaleProducts(authenticator.getFirebaseUserUid())

    override suspend fun addToBag(product: Product): CRUDResponse {

        return productService.addToBag(
            authenticator.getFirebaseUserUid(),
            product.title,
            product.salePrice ?: product.price,
            product.description,
            product.category,
            product.image,
            product.imageTwo,
            product.imageThree,
            product.rate,
            product.count,
            product.saleState
        )
    }

    override suspend fun getBagProducts(): List<Product> {
        return productService.getBagProductsByUser(authenticator.getFirebaseUserUid())
    }

    override suspend fun getBagProductsCount(): Int {
        return productService.getBagProductsByUser(authenticator.getFirebaseUserUid()).size
    }

    override suspend fun deleteFromBag(id: Int): CRUDResponse = productService.deleteFromBag(id)

    override suspend fun getCategories(): List<String> =
        productService.getCategories(authenticator.getFirebaseUserUid())

    override suspend fun getProductsByCategory(category: String): List<Product> =
        productService.getProductsByCategory(authenticator.getFirebaseUserUid(), category)

    override suspend fun clearBag(): CRUDResponse {
        return productService.clearBag(authenticator.getFirebaseUserUid())
    }


    override suspend fun searchProduct(query: String): List<Product> {
        return try {
            productService.searchProduct(
                authenticator.getFirebaseUserUid(),
                query
            )
        } catch (e: Exception) {
            // Handle errors (e.g., network failures, server issues)
            emptyList()  // Return an empty list in case of an error
        }
    }
}