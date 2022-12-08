package com.tokorestapi.angular.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokorestapi.angular.domain.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
