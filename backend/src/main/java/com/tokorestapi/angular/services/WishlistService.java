package com.tokorestapi.angular.services;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokorestapi.angular.domain.Product;
import com.tokorestapi.angular.domain.users.Client;
import com.tokorestapi.angular.exceptions.ProductHasAlreadyBeenSold;
import com.tokorestapi.angular.exceptions.YouHaveAlreadyAddThisProductInYourWishlistException;
import com.tokorestapi.angular.repositories.ClientRepository;
import com.tokorestapi.angular.repositories.ProductRepository;
import com.tokorestapi.angular.security.ClientSS;

@Service
public class WishlistService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private ProductRepository productRepo;

	private Client findClientById(Integer id) {
		return clientService.findById(id);
	}

	private Product findProductById(Integer id) {
		return productService.findById(id);
	}

	public Set<Product> findAll() {
		ClientSS user = UserService.clientAuthenticated();
		Client cli = findClientById(user.getId());

		return cli.getProductsWished();

	}

	public void markProductAsWished(Integer productId) {
		Product product = findProductById(productId);
		ClientSS user = UserService.clientAuthenticated();
		Client client = findClientById(user.getId());

		if (Product.isSold(product)) {
			throw new ProductHasAlreadyBeenSold();
		}

		if (client.getProductsWished().contains(product)) {
			throw new YouHaveAlreadyAddThisProductInYourWishlistException();
		}

		client.getProductsWished().add(product);
		product.getWhoWhishesThisProduct().add(client);

		clientRepo.save(client);
		productRepo.save(product);
	}

	@Transactional
	public void delete(Integer productId) {
		ClientSS user = UserService.clientAuthenticated();

		productRepo.removeFromClientWishlist(productId, user.getId());
	}
	
	@Transactional
	public void removeProductFromWishlistWhenIsSold(Integer productId) {
		productRepo.removeFromWishListWhenIsSold(productId);
	}
}
