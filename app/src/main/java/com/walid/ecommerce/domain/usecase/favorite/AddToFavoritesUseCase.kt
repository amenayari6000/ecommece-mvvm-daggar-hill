package com.walid.ecommerce.domain.usecase.favorite

import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.domain.repository.ProductsRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) = productsRepository.addToFavorites(product)
}