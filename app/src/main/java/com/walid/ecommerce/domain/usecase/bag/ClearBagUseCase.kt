package com.walid.ecommerce.domain.usecase.bag

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ClearBagUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(): Resource<CRUDResponse> {
        return try {
            Resource.Loading
            Resource.Success(productsRepository.clearBag())
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}