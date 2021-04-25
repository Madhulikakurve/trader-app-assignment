package com.example.trader.services;

import java.util.List;
import java.util.Optional;

import com.example.trader.dto.TradeCreateRequest;
import com.example.trader.dto.TradeResponse;
import com.example.trader.dto.TradeUpdateRequest;
import com.example.trader.dto.TraderFindResponse;
import com.example.trader.exceptions.BadRequestAlertException;

public interface TraderService {
	
	public TradeResponse create(TradeCreateRequest request) throws BadRequestAlertException;
	
	public List<TraderFindResponse> getAll();
	
	public Optional<TraderFindResponse> findById(Long id);
	
	public TradeResponse update(TradeUpdateRequest request);

}
