package com.avenger.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.avenger.ecommerce.dao.CustomerRepository;
import com.avenger.ecommerce.dto.Purchase;
import com.avenger.ecommerce.dto.PurchaseResponse;
import com.avenger.ecommerce.entity.Customer;
import com.avenger.ecommerce.entity.Order;
import com.avenger.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	
	private CustomerRepository customerRepository;
	

	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
//		retreive the order info from dto
		Order order = purchase.getOrder();
		
//		generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
//		populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		
//		populate order with billingAddress and shippingAddress
		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());
		
//		populate customer with order
		Customer customer = purchase.getCustomer();
		customer.add(order);
		
//		save to the database
		customerRepository.save(customer);
		
//		send a response
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		
//		generate a random UUID number (UUID version-4)
//		https://en.wikipedia.org/wiki/Universally_unique_identifier
		
		return UUID.randomUUID().toString();
	}

}
