package com.walid.ecommerce.presentation.paymentsuccess

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.walid.ecommerce.R
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.common.delegate.viewBinding
import com.walid.ecommerce.common.showSnackbar
import com.walid.ecommerce.databinding.FragmentPaymentSuccessBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccessFragment : Fragment(R.layout.fragment_payment_success) {

    private val binding by viewBinding(FragmentPaymentSuccessBinding::bind)

    private val paymentSuccessViewModel: PaymentSuccessViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            btnContinueShopping.setOnClickListener { paymentSuccessViewModel.clearBag() }
        }
    }

    private fun initObservers() {

        paymentSuccessViewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> findNavController().navigate(R.id.action_paymentSuccessFragment_to_homeFragment)
                is Resource.Error -> requireView().showSnackbar(getString(R.string.something_went_wrong))
                Resource.Loading -> Unit
            }
        }
    }
}