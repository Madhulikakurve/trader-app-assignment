package com.example.trader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trader.entity.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
	
	List<Trade> findByTradeId(String tradeId);

}
