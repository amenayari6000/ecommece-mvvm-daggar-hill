package com.walid.ecommerce.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.domain.repository.Authenticator
import com.walid.ecommerce.domain.usecase.bag.AddToBagUseCase
import com.walid.ecommerce.domain.usecase.favorite.AddToFavoritesUseCase
import com.walid.ecommerce.domain.usecase.favorite.DeleteFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addToBagUseCase: AddToBagUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val authenticator: Authenticator,

    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _crudResponse = MutableLiveData<Resource<CRUDResponse>>()
    val crudResponse: LiveData<Resource<CRUDResponse>> = _crudResponse

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val productModel = savedStateHandle.get<Product>("product")

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        getProduct()
        _isFavorite.value = productModel?.isFavorite
    }

    fun addToBag() {
        viewModelScope.launch {
            productModel?.let {
        Log.d("DetailViewModel", "addToBag called with: userId=${authenticator.getFirebaseUserUid()}, title=${it.title}, price=${it.salePrice ?: it.price}, description=${it.description}, category=${it.category}, image=${it.image}, imageTwo=${it.imageTwo}, imageThree=${it.imageThree}, rate=${it.rate}, count=${it.count}, saleState=${it.saleState}")
                _crudResponse.value = Resource.Loading
                _crudResponse.value = addToBagUseCase(it)
            }
        }
    }

    private fun addToFavorite(product: Product) {
        viewModelScope.launch {
            addToFavoritesUseCase(product)
        }
    }

    private fun deleteFromFavorites(id: Int) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(id)
        }
    }

    private fun getProduct() {
        productModel?.let {
            _product.value = it
        }
    }

    fun setFavoriteState() {
        productModel?.let {
            if (_isFavorite.value == true) {
                deleteFromFavorites(it.id)
                _isFavorite.value = false
            } else {
                addToFavorite(it)
                _isFavorite.value = true
            }
        }
    }
}