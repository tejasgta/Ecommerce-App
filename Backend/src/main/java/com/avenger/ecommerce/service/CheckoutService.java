package com.avenger.ecommerce.service;

import com.avenger.ecommerce.dto.Purchase;
import com.avenger.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

	PurchaseResponse placeOrder(Purchase purchase);
}
