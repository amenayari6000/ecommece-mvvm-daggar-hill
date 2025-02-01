package com.walid.ecommerce.presentation.home.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.walid.ecommerce.R
import com.walid.ecommerce.common.Constants.CATEGORY
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.common.delegate.viewBinding
import com.walid.ecommerce.common.gone
import com.walid.ecommerce.common.showSnackbar
import com.walid.ecommerce.common.visible
import com.walid.ecommerce.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val binding by viewBinding(FragmentCategoryBinding::bind)

    private val categoryProductsViewModel: CategoryProductsViewModel by viewModels()

    private val categoryProductsAdapter by lazy { CategoryProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.takeIf { it.containsKey(CATEGORY) }?.apply {
            getString(CATEGORY)?.let {
                if (it == "All") categoryProductsViewModel.getProducts()
                else categoryProductsViewModel.getProductsByCategory(it)
            }
        }

        categoryProductsViewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.gone()
                    categoryProductsAdapter.updateList(it.data.shuffled())
                    binding.rvCategory.adapter = categoryProductsAdapter
                }

                is Resource.Error -> {
                    binding.progressBar.gone()
                    requireView().showSnackbar(it.throwable.message.toString())
                }

                Resource.Loading -> binding.progressBar.visible()
            }
        }
    }
}