package com.walid.ecommerce.presentation.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.common.gone
import com.walid.ecommerce.common.showSnackbar
import com.walid.ecommerce.common.visible
import com.walid.ecommerce.databinding.FragmentSearchProductBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: BottomSheetDialog

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private val searchViewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        initObservers()

        with(binding) {

            with(searchViewModel) {

                bsLayout.minimumHeight = resources.displayMetrics.heightPixels

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            searchProduct(it)
                        }
                        return false
                    }
                })

                searchAdapter.onProductClick = {
                    val action = SearchFragmentDirections.searchToDetail(it)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initObservers() {

        with(binding) {

            with(searchViewModel) {

                products.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            searchAdapter.updateList(it.data)
                            rvSearchProducts.adapter = searchAdapter
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}