package com.walid.ecommerce.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.walid.ecommerce.R
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.common.delegate.viewBinding
import com.walid.ecommerce.common.gone
import com.walid.ecommerce.common.showSnackbar
import com.walid.ecommerce.common.visible
import com.walid.ecommerce.databinding.FragmentProfileBinding
import com.walid.ecommerce.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.btnSignOut.setOnClickListener {
            profileViewModel.signOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private fun initObservers() {
        with(binding) {
            with(profileViewModel) {

                currentUser.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            user = it.data
                        }

                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }

                        Resource.Loading -> progressBar.visible()
                    }
                }
            }
        }
    }
}