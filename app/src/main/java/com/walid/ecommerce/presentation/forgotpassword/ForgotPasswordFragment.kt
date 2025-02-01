package com.walid.ecommerce.presentation.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.walid.ecommerce.R
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.common.delegate.viewBinding
import com.walid.ecommerce.common.gone
import com.walid.ecommerce.common.isValidEmail
import com.walid.ecommerce.common.showSnackbar
import com.walid.ecommerce.common.visible
import com.walid.ecommerce.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private val binding by viewBinding(FragmentForgotPasswordBinding::bind)

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSend.setOnClickListener {
                if (etEmail.isValidEmail(getString(R.string.invalid_mail)))
                    viewModel.sendPasswordResetEmail(etEmail.text.toString())
            }

            tvAlreadyAccount.setOnClickListener {
                it.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }

            viewModel.result.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        requireView().showSnackbar(getString(R.string.email_sent))
                        progressBar.gone()
                    }

                    is Resource.Error -> {
                        progressBar.gone()
                        requireView().showSnackbar(getString(R.string.something_went_wrong))
                    }

                    Resource.Loading -> progressBar.visible()
                }
            }
        }
    }
}