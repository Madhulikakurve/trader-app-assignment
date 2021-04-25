package com.example.trader.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TraderFindResponse {
	
	private Long id;
	
	private String tradeId;
	
	private Integer version;
	
	private String counterPartyId;
	
	private String bookId;
	
	private LocalDate maturityDate;
	
	private LocalDate createdDate;
	
	private Boolean expired;
}
