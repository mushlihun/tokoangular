package com.tokorestapi.angular.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokorestapi.angular.domain.dto.ranking.ClientRankingDTO;
import com.tokorestapi.angular.domain.dto.ranking.SellerRankingDTO;
import com.tokorestapi.angular.domain.users.Client;
import com.tokorestapi.angular.domain.users.Seller;
import com.tokorestapi.angular.repositories.ClientRepository;
import com.tokorestapi.angular.repositories.SellerRepository;

@Service
public class RankingService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private SellerRepository sellerRepo;

	public List<ClientRankingDTO> returnRankingClient() {

		List<Client> clients = clientRepo.returnRankingClient();
		List<ClientRankingDTO> rankingDTO = new ArrayList<>();

		clients.stream().forEach(x -> rankingDTO.add(new ClientRankingDTO(x.getId(), x.getName(), x.getNumberOfBuys(),
				x.getHowMuchMoneyThisClientHasSpent())));

		return rankingDTO;
	}

	public List<SellerRankingDTO> returnRankingSeller() {

		List<Seller> sellers = sellerRepo.returnRankingSeller();
		List<SellerRankingDTO> rankingDTO = new ArrayList<>();

		sellers.stream().forEach(x -> rankingDTO.add(new SellerRankingDTO(x.getId(), x.getName(), x.getNumberOfSells(),
				x.getHowMuchMoneyThisSellerHasSold())));

		return rankingDTO;
	}

}
