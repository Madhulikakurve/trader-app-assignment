package com.example.trader.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trader.dto.TradeCreateRequest;
import com.example.trader.dto.TradeResponse;
import com.example.trader.dto.TradeUpdateRequest;
import com.example.trader.dto.TraderFindResponse;
import com.example.trader.exceptions.BadRequestAlertException;
import com.example.trader.services.TraderService;

@RestController
@RequestMapping("/rest/api/v1/trade")
public class TradeController {
	
	private TraderService tradeService;
	
	
	@Autowired
	public TradeController(TraderService tradeService) {
		this.tradeService = tradeService;
	}

	@PostMapping
	public ResponseEntity<TradeResponse> create(@RequestBody TradeCreateRequest request) throws BadRequestAlertException {
		TradeResponse response = tradeService.create(request);
		return new ResponseEntity<TradeResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<TraderFindResponse>> findAll() {
		List<TraderFindResponse> response = tradeService.getAll();
		return new ResponseEntity<List<TraderFindResponse>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TraderFindResponse> findById(@PathVariable("id") Long id) {
		Optional<TraderFindResponse> response = tradeService.findById(id);
		if(!response.isPresent()) {
			return new ResponseEntity<TraderFindResponse>(HttpStatus.NOT_FOUND);	
		} else {
			return new ResponseEntity<TraderFindResponse>(response.get(), HttpStatus.OK);
		}
		
	}
	
	@PutMapping
	public ResponseEntity<TradeResponse> update(TradeUpdateRequest request) {
		TradeResponse response = tradeService.update(request);
		return new ResponseEntity<TradeResponse>(response, HttpStatus.OK);
	}
}
