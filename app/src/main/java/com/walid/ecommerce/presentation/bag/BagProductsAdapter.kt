package com.walid.ecommerce.presentation.bag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walid.ecommerce.data.model.Product
import com.walid.ecommerce.databinding.ItemBagProductBinding

class BagProductsAdapter : RecyclerView.Adapter<BagProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onIncreaseClick: (Double) -> Unit = {}
    var onDecreaseClick: (Double) -> Unit = {}
    var onDeleteClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemBagProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ProductsViewHolder(private var binding: ItemBagProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            with(binding) {

                var productCount = 1

                product = item

                if (productCount == 1) {
                    count = productCount.toString()
                }

                imgIncrease.setOnClickListener {
                    onIncreaseClick(item.price)
                    productCount++
                    count = productCount.toString()
                }

                imgDecrease.setOnClickListener {
                    if (productCount != 1) {
                        onDecreaseClick(item.price)
                        productCount--
                        count = productCount.toString()
                    } else {
                        onDeleteClick(item.id)
                    }
                }

                imgDelete.setOnClickListener { onDeleteClick(item.id) }
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateList(updatedList: List<Product>) {
        list.clear()
        list.addAll(updatedList)
        notifyItemRangeInserted(0, updatedList.size)
    }
}