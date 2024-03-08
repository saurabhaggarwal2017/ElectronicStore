package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.AddItemToCartRequest;
import com.lcwd.electronic.store.dto.CartDto;

public interface CartService {
    // add item to the cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // remove item from the cart
    CartDto removeItemFromCart(String userId, int cartItemId);

    // remove all item from the cart
    CartDto clearCart(String userId);

    // get cart

    CartDto getCartByUser(String userId);

    //update cartItem details

    CartDto updateCartItemQuantity(String userId, int cartItemId, int updatedQuantity);

}
