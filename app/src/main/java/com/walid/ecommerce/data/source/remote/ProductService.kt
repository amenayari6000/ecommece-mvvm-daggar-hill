package com.walid.ecommerce.data.source.remote

import com.walid.ecommerce.data.model.CRUDResponse
import com.walid.ecommerce.data.model.Product
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProductService {









    @POST("get_products_by_user.php")
    @FormUrlEncoded
    suspend fun getProducts(@Field("userId") userId: String): List<Product>

    @POST("get_sale_products_by_user.php")
    @FormUrlEncoded
    suspend fun getSaleProducts(@Field("userId") userId: String): List<Product>

    @POST("get_products_by_user_and_category.php")
    @FormUrlEncoded
    suspend fun getProductsByCategory(
        @Field("userId") user: String,
        @Field("category") category: String
    ): List<Product>

    @POST("get_categories_by_user.php")
    @FormUrlEncoded
    suspend fun getCategories(@Field("userId") userId: String): List<String>

    @POST("get_bag_products_by_user.php")
    @FormUrlEncoded
    suspend fun getBagProductsByUser(@Field("userId") userId: String): List<Product>

    @POST("add_to_bag.php")
    @FormUrlEncoded
    suspend fun addToBag(
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("image") image: String,
        @Field("image_two") imageTwo: String,
        @Field("image_three") imageThree: String,
        @Field("rate") rate: Double,
        @Field("count") count: Int,
        @Field("sale_state") saleState: Int
    ): CRUDResponse



    @POST("delete_from_bag.php")
    @FormUrlEncoded
    suspend fun deleteFromBag(@Field("id") id: Int): CRUDResponse

    @POST("clear_bag.php")
    @FormUrlEncoded
    suspend fun clearBag(@Field("userId") userId: String): CRUDResponse

    @POST("search_product_by_user.php")
    @FormUrlEncoded
    suspend fun searchProduct(
        @Field("userId") userId: String,
        @Field("query") query: String
    ): List<Product>
}