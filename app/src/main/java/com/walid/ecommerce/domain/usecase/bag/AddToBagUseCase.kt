package com.walid.ecommerce.domain.usecase.bag

import android.util.Log

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.domain.repository.Authenticator
import com.walid.ecommerce.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToBagUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,

    private val authenticator: Authenticator
) {
    suspend operator fun invoke(product: Product): Resource<CRUDResponse> {
        return try {
          Log.d("AddToBagUseCase", "Adding product to bag: userId=${authenticator.getFirebaseUserUid()}, title=${product.title}, price=${product.salePrice ?: product.price}, description=${product.description}, category=${product.category}, image=${product.image}, imageTwo=${product.imageTwo}, imageThree=${product.imageThree}, rate=${product.rate}, count=${product.count}, saleState=${product.saleState}")
            Resource.Loading
            val response = productsRepository.addToBag(product)
            Log.d("AddToBagUseCase", "Response: $response")


            Resource.Success(productsRepository.addToBag(product))
        } catch (e: HttpException) {
            Log.e("AddToBagUseCase", "HttpException: ${e.message}", e)
            Resource.Error(e)
        } catch (e: IOException) {
            Log.e("AddToBagUseCase", "IOException: ${e.message}", e)
            Resource.Error(e)
        }
    }
}
