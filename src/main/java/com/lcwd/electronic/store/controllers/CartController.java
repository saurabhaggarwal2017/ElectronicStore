package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dto.AddItemToCartRequest;
import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.service.CartService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    Logger logger = LoggerFactory.getLogger(CartController.class);

    // add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@Valid @RequestBody AddItemToCartRequest request, @PathVariable String userId) {
        logger.info("Request : {}",request);
        logger.info("userId : {}",userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);

    }

    // remove item from the cart
    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        CartDto cartDto = cartService.removeItemFromCart(userId, itemId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<CartDto> clearAllCartItems(@PathVariable String userId) {
        CartDto cartDto = cartService.clearCart(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // update cart
    @PutMapping("/{userId}/item/{itemId}")
    public ResponseEntity<CartDto> updateCartItem(@RequestParam("quantity") int quantity, @PathVariable String userId, @PathVariable int itemId) {

        CartDto cartDto = cartService.updateCartItemQuantity(userId, itemId,quantity);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    // get all cart items
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getAllCartItems(@PathVariable String userId) {
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser, HttpStatus.OK);
    }

}
