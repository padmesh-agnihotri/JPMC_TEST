package com.jpmc.test.tradeprocess.process;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.jpmc.test.tradeprocess.model.TradeInstruction;
import com.jpmc.test.tradeprocess.model.TradeInstructionProcessResult;

public class TradeInstructionProcessorTest {
	private static final TradeInstructionProcessor processor = new TradeInstructionProcessor();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
	
	@Test
	public void whenInstructionIsInvalid(){
		final TradeInstruction invalidInstruction = new TradeInstruction.TradeInstructionBuilder().build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(invalidInstruction);
		assertNull(wrapper);
	}
	
	@Test
	public void whenCurrencyIsSARAndSettlementDayIsFriday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("SAR").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("23 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenCurrencyIsSARAndSettlementDayIsSaturday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("SAR").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("22 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("23 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenCurrencyIsAEDAndSettlementDayIsFriday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("AED").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("23 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenCurrencyIsAEDAndSettlementDayIsSaturday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("AED").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("22 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(1000000 == wrapper.getSettlementAmount());
		assertEquals("23 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
	}
	
	@Test
	public void whenSettlementDayIsSaturday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("22 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("24 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenSettlementDayIsSunday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("23 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("24 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	 
	
	@Test
	public void whenSettlementDayIsWeekday(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertEquals("21 Dec 2018", wrapper.getAdjustedSettlementDate().format(formatter));
		assertTrue(1000000 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenFxRateIsZero(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("0")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenFxRateIsNegative(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("-1")
                                                                                     .withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenPriceIsZero(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("0")
                                                                                     .withTradeUnit("100").withPricePerUnit("0")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenPriceIsNegative(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("100").withPricePerUnit("-100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenTradeUnitIsZero(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("0").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}
	
	@Test
	public void whenTradeUnitIsNegative(){
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY")
                                                                                     .withCurrencyCode("USD").withFxRate("100")
                                                                                     .withTradeUnit("-1").withPricePerUnit("100")
                                                                                     .withInstructionDate("21 Jan 2018").withSettlementDate("21 Dec 2018")
                                                                                     .build();
		final TradeInstructionProcessResult wrapper = processor.processInstuction(trade);
		assertTrue(0 == wrapper.getSettlementAmount());
	}

	


}
