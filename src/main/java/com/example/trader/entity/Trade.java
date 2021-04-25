package com.example.trader.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trade")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Trade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String tradeId;
	
	private Integer version;
	
	private String counterPartyId;
	
	private String bookId;
	
	private LocalDate maturityDate;
	
	private LocalDate createdDate;
	
	private Boolean expired;
}
