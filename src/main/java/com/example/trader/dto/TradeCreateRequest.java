package com.example.trader.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TradeCreateRequest {
	
	private String tradeId;
	
	private Integer version;
	
	private String counterPartyId;
	
	private String bookId;
	
	private LocalDate maturityDate;
	
}
