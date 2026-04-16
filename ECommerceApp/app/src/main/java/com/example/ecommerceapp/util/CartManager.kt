package com.example.ecommerceapp.util

import com.example.ecommerceapp.model.Product

object CartManager {

    private val cartItems = mutableListOf<Product>()

    fun add(product: Product) {
        if (!cartItems.contains(product)) cartItems.add(product)
    }

    fun remove(product: Product) {
        cartItems.remove(product)
    }

    fun getItems(): List<Product> = cartItems

    fun clear() {
        cartItems.forEach { it.inCart = false }
        cartItems.clear()
    }

    fun total(): Double = cartItems.sumOf { it.price }

    fun count(): Int = cartItems.size
}