package com.example.trader.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trader.dto.TradeCreateRequest;
import com.example.trader.dto.TradeResponse;
import com.example.trader.dto.TradeUpdateRequest;
import com.example.trader.dto.TraderFindResponse;
import com.example.trader.entity.Trade;
import com.example.trader.exceptions.BadRequestAlertException;
import com.example.trader.repository.TradeRepository;

@Service
public class TradeServiceImpl implements TraderService {
	
	private TradeRepository tradeRepository;
	
	@Autowired
	public TradeServiceImpl(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}
	
	@Override
	public TradeResponse create(TradeCreateRequest request) throws BadRequestAlertException {
		if(request.getMaturityDate().isBefore(LocalDate.now())) {
			throw new BadRequestAlertException("Maturity Date should not be less than today's date");
			
		}
		List<Trade> foundTrades = tradeRepository.findByTradeId(request.getTradeId());
		if(foundTrades.size() != 0 && foundTrades != null) {
			for (Trade trade : foundTrades) {
				if(trade.getVersion() > request.getVersion()) {
					throw new BadRequestAlertException("lower version of trade detected");
				}
				
			}
		}
		
		Trade trade = new Trade();
		Trade foundMatchingVersion = foundTrades.stream().filter((t) -> t.getVersion().equals(request.getVersion())).findFirst().orElse(null);
		
		if(foundMatchingVersion != null) {
			trade.setId(foundMatchingVersion.getId());
			trade.setBookId(request.getBookId());
			trade.setCounterPartyId(request.getCounterPartyId());
			trade.setCreatedDate(LocalDate.now());
			trade.setMaturityDate(request.getMaturityDate());
			trade.setTradeId(foundMatchingVersion.getTradeId());
			trade.setVersion(foundMatchingVersion.getVersion());
			trade.setExpired(false);
			
		} else {
			trade.setId(null);
			trade.setBookId(request.getBookId());
			trade.setCounterPartyId(request.getCounterPartyId());
			trade.setCreatedDate(LocalDate.now());
			trade.setMaturityDate(request.getMaturityDate());
			trade.setTradeId(request.getTradeId());
			trade.setVersion(request.getVersion());
			trade.setExpired(false);
		}
		
		
		
		Trade savedTrade = tradeRepository.save(trade);
		
		TradeResponse response = new TradeResponse();
		response.setId(savedTrade.getId());
		response.setMessage("Trade data added.");
		return response;
	}

	

	@Override
	public List<TraderFindResponse> getAll() {
		List<Trade> response = tradeRepository.findAll();
		return response.stream().map(t -> {
			TraderFindResponse resp = new TraderFindResponse();
			resp.setBookId(t.getBookId());
			resp.setCounterPartyId(t.getCounterPartyId());
			resp.setCreatedDate(t.getCreatedDate());
			resp.setExpired(t.getExpired());
			resp.setId(t.getId());
			resp.setMaturityDate(t.getMaturityDate());
			resp.setVersion(t.getVersion());
			return resp;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<TraderFindResponse> findById(Long id) {
		Optional<Trade> response = tradeRepository.findById(id);
		return response.map(t -> {
			TraderFindResponse resp = new TraderFindResponse();
			resp.setBookId(t.getBookId());
			resp.setCounterPartyId(t.getCounterPartyId());
			resp.setCreatedDate(t.getCreatedDate());
			resp.setExpired(t.getExpired());
			resp.setId(t.getId());
			resp.setMaturityDate(t.getMaturityDate());
			resp.setVersion(t.getVersion());
			return resp;
		});
		
	}

	@Override
	public TradeResponse update(TradeUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
