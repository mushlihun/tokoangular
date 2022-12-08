package com.tokorestapi.angular.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokorestapi.angular.domain.Order;
import com.tokorestapi.angular.domain.users.Client;
import com.tokorestapi.angular.domain.users.Seller;
import com.tokorestapi.angular.exceptions.AuthorizationException;
import com.tokorestapi.angular.exceptions.ObjectNotFoundException;
import com.tokorestapi.angular.repositories.OrderRepository;
import com.tokorestapi.angular.security.ClientSS;
import com.tokorestapi.angular.security.SellerSS;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ClientService clientService;

	@Autowired
	private SellerService sellerService;

	private Client findClientById(Integer id) {
		return clientService.findById(id);
	}

	private Seller findSellerById(Integer id) {
		return sellerService.findById(id);
	}

	public Order findById(Integer id, boolean isClient) {
		Optional<Order> obj = orderRepo.findById(id);

		if (isClient) {
			return findByIdAsClient(id, obj);
		}
		return findByIdAsSeller(id, obj);

	}

	public List<Order> findAll(boolean isClient) {
		
		if(isClient) {
			return findAllAsClient();
		}
		return findAllAsSeller();
		

	}

	private Order findByIdAsSeller(Integer id, Optional<Order> obj) {
		SellerSS user = UserService.sellerAuthenticated();
		Seller sel = findSellerById(user.getId());

		if (!sel.getOrders().contains(obj.get())) {
			throw new AuthorizationException();

		} else {

			try {
				return obj.get();

			} catch (NoSuchElementException e) {
				throw new ObjectNotFoundException();
			}
		}
	}

	private Order findByIdAsClient(Integer id, Optional<Order> obj) {
		ClientSS user = UserService.clientAuthenticated();
		Client cli = findClientById(user.getId());

		if (!cli.getOrders().contains(obj.get())) {
			throw new AuthorizationException();

		} else {

			try {
				return obj.get();

			} catch (NoSuchElementException e) {
				throw new ObjectNotFoundException();
			}
		}
	}
	
	private List<Order> findAllAsClient(){
		ClientSS user = UserService.clientAuthenticated();
		Client cli = findClientById(user.getId());

		return cli.getOrders();
	}
	
	private List<Order> findAllAsSeller(){
		SellerSS user = UserService.sellerAuthenticated();
		Seller sel = findSellerById(user.getId());

		return sel.getOrders();
	}
}
