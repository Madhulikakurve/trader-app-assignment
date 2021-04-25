package com.example.trader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.trader.dto.TradeCreateRequest;
import com.example.trader.dto.TradeResponse;
import com.example.trader.entity.Trade;
import com.example.trader.exceptions.BadRequestAlertException;
import com.example.trader.repository.TradeRepository;
import com.example.trader.services.TraderService;

@SpringBootTest
public class TradeServiceTest {
	
	@Autowired
    private TraderService service;

    /**
     * Create a mock implementation of the WidgetRepository
     */
    @MockBean
    private TradeRepository repository;
    
    @Test
    @DisplayName("Throw exception when maturity date is ")
    public void Should_ThrowException_When_MaturityDateIsLessThanCurrentDate() throws BadRequestAlertException {
    	
    	TradeCreateRequest request = new TradeCreateRequest();
    	request.setBookId("B1");
    	request.setCounterPartyId("CP-1");
    	request.setMaturityDate(LocalDate.now().minusDays(1));
    	request.setTradeId("t1");
    	request.setVersion(1);
    	
    	assertThatExceptionOfType(BadRequestAlertException.class).isThrownBy(() -> service.create(request));
    }
    
    
    @Test
    @DisplayName("Throw exception when lower version of trade is received")
    public void Should_ThrowException_When_LowerVersionOfTradeIsReceived() throws BadRequestAlertException {
    	
		
    	Trade data1 = new Trade();
    	data1.setBookId("B1");
    	data1.setCounterPartyId("CP-1");
    	data1.setMaturityDate(LocalDate.now().plusYears(5));
    	data1.setTradeId("t1");
    	data1.setVersion(1);
    	
    	Trade data2 = new Trade();
    	data2.setBookId("B1");
    	data2.setCounterPartyId("CP-1");
    	data2.setMaturityDate(LocalDate.now().plusYears(5));
    	data2.setTradeId("t1");
    	data2.setVersion(2);
    	
    	List<Trade> foundTrades = new ArrayList<>();
    	foundTrades.add(data1);
    	foundTrades.add(data2);
    	
    	when(repository.findByTradeId(Mockito.anyString())).thenReturn(foundTrades);
    	
    	
    	TradeCreateRequest request = new TradeCreateRequest();
    	request.setBookId("B1");
    	request.setCounterPartyId("CP-1");
    	request.setMaturityDate(LocalDate.now().plusYears(5));
    	request.setTradeId("t1");
    	request.setVersion(1);
    	assertThatExceptionOfType(BadRequestAlertException.class).isThrownBy(() -> service.create(request));
    }
    
    
    @Test
    @DisplayName("Update existing recored when trade version is same")
    public void Should_UpdateExistingRecord_When_TradeVersionIsSame() throws BadRequestAlertException {
    	
		
    	Trade data1 = new Trade();
    	data1.setId(1l);
    	data1.setBookId("B1");
    	data1.setCounterPartyId("CP-1");
    	data1.setMaturityDate(LocalDate.now().plusYears(5));
    	data1.setTradeId("t1");
    	data1.setVersion(1);
    	
    	Trade data2 = new Trade();
    	data2.setId(1l);
    	data2.setBookId("B1");
    	data2.setCounterPartyId("CP-1");
    	data2.setMaturityDate(LocalDate.now().plusYears(5));
    	data2.setTradeId("t1");
    	data2.setVersion(2);
    	
    	List<Trade> foundTrades = new ArrayList<>();
    	foundTrades.add(data1);
    	foundTrades.add(data2);
    	
    	when(repository.findByTradeId(Mockito.anyString())).thenReturn(foundTrades);
    	
    	
    	TradeCreateRequest request = new TradeCreateRequest();
    	request.setBookId("B1");
    	request.setCounterPartyId("CP-1");
    	request.setMaturityDate(LocalDate.now().plusYears(5));
    	request.setTradeId("t1");
    	request.setVersion(2);
    	
    	when(repository.save(Mockito.any(Trade.class))).thenReturn(data2);
    	
    	TradeResponse response = service.create(request);
    	assertThat(response).isNotNull();
    	assertThat(response.getId()).isNotNull();
    	assertThat(response.getMessage()).isEqualTo("Trade data added.");
    }
    
}
