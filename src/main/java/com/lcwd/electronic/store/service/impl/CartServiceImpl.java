package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.AddItemToCartRequest;
import com.lcwd.electronic.store.dto.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.service.CartService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!!"));

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        long cartItemPrice = request.getQuantity() * product.getPrice();


        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            cart.setTotalCartItem(cart.getTotalCartItem() + request.getQuantity());
            cart.setTotalCartPrice(cart.getTotalCartPrice() + cartItemPrice);
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartId(UUID.randomUUID().toString());
            cart.setTotalCartItem(request.getQuantity());
            cart.setTotalCartPrice(cartItemPrice);
        }
        cart.setLastUpdatedAt(new Date());

        CartItem cartItem = CartItem.builder().quantity(request.getQuantity()).product(product).cart(cart).totalPrice(cartItemPrice).build();

        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);
        Cart savedCart = cartRepository.save(cart);

        return mapper.map(savedCart, CartDto.class);
    }

    @Override
    public CartDto removeItemFromCart(String userId, int cartItemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user cart not found!!"));
        cart.setLastUpdatedAt(new Date());

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = cartItems.stream().filter(item -> item.getCardItemId() == cartItemId).findAny().orElseThrow(() -> new ResourceNotFoundException("Cart Item not found!!"));

        cart.setTotalCartItem(cart.getTotalCartItem() - cartItem.getQuantity());
        cart.setTotalCartPrice(cart.getTotalCartPrice() - cartItem.getTotalPrice());

        cartItems.remove(cartItem);

        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user cart not found!!"));
        cart.setLastUpdatedAt(new Date());

        cart.setTotalCartPrice(0);
        cart.setTotalCartItem(0);
        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.clear();


        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user cart not found!!"));
        if(cart.getCartItems().size()==0){
            cart.setTotalCartPrice(0);
            cart.setTotalCartItem(0);
            Cart savecart = cartRepository.save(cart);
            return mapper.map(savecart, CartDto.class);
        }
        return mapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto updateCartItemQuantity(String userId, int cartItemId, int updatedQuantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user cart not found!!"));

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found!!"));
        int oldQuantity = cartItem.getQuantity();
        long oldPrice = cartItem.getTotalPrice();

        cartItem.setQuantity(updatedQuantity);
        long newPrice = cartItem.getProduct().getPrice() * updatedQuantity;
        cartItem.setTotalPrice(newPrice);
        cartItemRepository.save(cartItem);

        cart.setTotalCartItem((cart.getTotalCartItem() - oldQuantity) + updatedQuantity);
        cart.setTotalCartPrice((cart.getTotalCartPrice() - oldPrice) + newPrice);

        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);


    }

}
