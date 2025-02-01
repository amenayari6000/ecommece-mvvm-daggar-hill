package com.walid.ecommerce.presentation.home.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.domain.usecase.product.GetProductsByCategoryUseCase
import com.walid.ecommerce.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>(Resource.Loading)
    val products: LiveData<Resource<List<Product>>> = _products

    fun getProducts() {
        viewModelScope.launch {
            _products.value = Resource.Loading
            _products.value = getProductsUseCase()
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            _products.value = Resource.Loading
            _products.value = getProductsByCategoryUseCase(category)
        }
    }
}