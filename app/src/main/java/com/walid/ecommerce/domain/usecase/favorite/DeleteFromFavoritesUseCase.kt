package com.walid.ecommerce.domain.usecase.favorite

import com.walid.ecommerce.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteFromFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = productsRepository.deleteFromFavorites(id)
}