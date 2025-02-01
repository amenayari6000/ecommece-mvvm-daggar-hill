package com.walid.ecommerce.presentation.bag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.domain.usecase.bag.DeleteFromBagUseCase
import com.walid.ecommerce.domain.usecase.bag.GetBagProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val getBagProductsUseCase: GetBagProductsUseCase,
    private val deleteFromBagUseCase: DeleteFromBagUseCase,

) : ViewModel() {

    private val _bagProducts = MutableLiveData<Resource<List<Product>>>(Resource.Loading)
    val bagProducts: LiveData<Resource<List<Product>>> = _bagProducts

    private val _crudResponse = MutableLiveData<Resource<CRUDResponse>>()
    val crudResponse: LiveData<Resource<CRUDResponse>> = _crudResponse





    private val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    init {
        getBagProducts()
    }

    private fun getBagProducts() {
        viewModelScope.launch {
            _bagProducts.value = getBagProductsUseCase()
        }
    }

    fun deleteFromBag(id: Int) {
        viewModelScope.launch {
            _crudResponse.value = deleteFromBagUseCase(id)
            getBagProducts()
        }
    }

    fun increase(price: Double) {
        _totalAmount.value = _totalAmount.value?.plus(price)
    }

    fun decrease(price: Double) {
        _totalAmount.value = _totalAmount.value?.minus(price)
    }

    fun resetTotalAmount() {
        _totalAmount.value = 0.0
    }
}