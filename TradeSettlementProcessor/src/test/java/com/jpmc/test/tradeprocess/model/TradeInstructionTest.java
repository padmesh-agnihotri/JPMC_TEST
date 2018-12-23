package com.jpmc.test.tradeprocess.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TradeInstructionTest {
	private final TradeInstruction tradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
	                                                                           .withEntity("ENTITY").withFxRate("100")
	                                                                           .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
	                                                                           .withSettlementDate("12 DEC 2018").withSide("BUY")
	                                                                           .withTradeUnit("1000").build();
	
	
	@Test
	public void nullObjectIsNotEqual(){
		assertFalse(tradeInstruction.equals(null));
	}
	
	@Test
	public void objectOfDifferentClassIsNotEqual() {
		assertFalse(tradeInstruction.equals("INVALID_OBJECT"));
	}
	
	@Test
	public void referenceToSameObjectIsEqual() {
		final TradeInstruction anotherTradeInstruction = tradeInstruction;
		assertTrue(tradeInstruction.equals(anotherTradeInstruction));
		assertTrue(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoCurrencyCodeNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withFxRate("100")
                                                                       .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                       .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                       .withTradeUnit("1000").build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoEntityNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                       .withFxRate("100").withInstructionDate("12 DEC 2018")
                                                                       .withPricePerUnit("100.00").withSettlementDate("12 DEC 2018")
                                                                       .withSide("BUY").withTradeUnit("1000").build();
		
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoFxRateNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                       .withEntity("ENTITY").withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                       .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                       .withTradeUnit("1000").build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoInstructionDateNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                       .withEntity("ENTITY").withFxRate("100")
                                                                       .withPricePerUnit("100.00")
                                                                       .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                       .withTradeUnit("1000").build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoPricePerUnitNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                .withEntity("ENTITY").withFxRate("100")
                                                                .withInstructionDate("12 DEC 2018").withSettlementDate("12 DEC 2018")
                                                                .withSide("BUY").withTradeUnit("1000").build();
		
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoSettlementDateNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                .withEntity("ENTITY").withFxRate("100")
                                                                .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                .withSide("BUY").withTradeUnit("1000").build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoSideNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                .withEntity("ENTITY").withFxRate("100")
                                                                .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                .withSettlementDate("12 DEC 2018").withTradeUnit("1000").build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithNoTradeUnitNotEqual() {
		final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                .withEntity("ENTITY").withFxRate("100")
                                                                .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                .build();
		assertFalse(tradeInstruction.equals(anotherTradeInstruction));
		assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithSameDataAreEqual() {
		 final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                        .withEntity("ENTITY").withFxRate("100")
                                                        .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                        .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                        .withTradeUnit("1000").build();
		 assertTrue(tradeInstruction.equals(anotherTradeInstruction));
		 assertTrue(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithSameDataReturnsSameHashCode() {
		 final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                        .withEntity("ENTITY").withFxRate("100")
                                                                        .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                        .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                        .withTradeUnit("1000").build();
         assertTrue(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}
	
	@Test
	public void objectWithDifferentDataReturnsSameHashCode() {
		 final TradeInstruction anotherTradeInstruction = new TradeInstruction.TradeInstructionBuilder().withCurrencyCode("EURO")
                                                                        .withEntity("DIFFERENT_ENTITY").withFxRate("100")
                                                                        .withInstructionDate("12 DEC 2018").withPricePerUnit("100.00")
                                                                        .withSettlementDate("12 DEC 2018").withSide("BUY")
                                                                        .withTradeUnit("1000").build();
         assertFalse(tradeInstruction.hashCode() == anotherTradeInstruction.hashCode());
	}

}
