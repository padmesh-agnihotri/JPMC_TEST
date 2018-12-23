package com.jpmc.test.tradeprocess.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jpmc.test.tradeprocess.model.TradeInstruction;

public class TradeInstructionValidatorTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void whenInputObjectIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("Input Instruction object is null.");
		TradeInstructionValidator.isValidInstruction(null);
	}
	
	@Test
	public void whenEntityIsBlank() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("Entity is mandatory");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("").build();
		TradeInstructionValidator.isValidInstruction(trade);
	}

	@Test
	public void whenEntityIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("Entity is mandatory");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity(null).build();
		TradeInstructionValidator.isValidInstruction(trade);
	}
	
	@Test
	public void whenSideIsNull()throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("null is not a valid side.");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide(null).build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenSideIsOutsideThePermissibleValues() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("RANDOM is not a valid side.");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("RANDOM").build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenCurrencyCodeIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("null is not a valid currencyCode.");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
				                                                                     .withSide("BUY")
				                                                                     .withCurrencyCode(null)
				                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenCurrencyCodeIsOutsideThePermissibleValues() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("RANDOM is not a valid currencyCode.");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
				                                                                     .withSide("BUY")
				                                                                     .withCurrencyCode("RANDOM")
				                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenFxIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("FXRate is not a valid double.Recieved null");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate(null)
                                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenFxIsEmpty() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("FXRate is not a valid double.Recieved ");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenFxIsNotValidDouble() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("FXRate is not a valid double.Recieved RANDOM");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("RANDOM")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenTradeUnitIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("TradeUnit is not a valid long.Recieved null");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit(null)
                                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenTradeUnitIsEmpty() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("TradeUnit is not a valid long.Recieved ");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenTradeUnitIsNotValidLong() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("TradeUnit is not a valid long.Recieved 1.2");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("1.2")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenPricePerUnitIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("PricePerUnit is not a valid double.Recieved null");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit(null)
                                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenPricePerUnitIsEmpty() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("PricePerUnit is not a valid double.Recieved ");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenPricePerUnitIsNotAValidDouble() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("PricePerUnit is not a valid double.Recieved RANDOM");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("RANDOM")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenInstructionDateIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("InstructionDate is not a valid date.Recieved null");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate(null)
                                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenInstructionDateIsEmpty() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("InstructionDate is not a valid date.Recieved ");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenInstructionDateIsNotAValidDate() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("InstructionDate is not a valid date.Recieved 20181011");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("20181011")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
  
	@Test
	public void whenSettlementDateIsNull() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("SettlementDate is not a valid date.Recieved null");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("10 Jan 2018")
                                                                                     .withSettlementDate(null)
                                                                                     .build();
		TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenSettlementDateIsEmpty() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("SettlementDate is not a valid date.Recieved ");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("10 Jan 2018")
                                                                                     .withSettlementDate("")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenSettlementDateIsNotAValidDate() throws Exception {
		exception.expect(ValidationException.class);
		exception.expectMessage("SettlementDate is not a valid date.Recieved 20181011");
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("10 Jan 2018")
                                                                                     .withSettlementDate("20181011")
                                                                                     .build();
        TradeInstructionValidator.isValidInstruction(trade);	
	}
	
	@Test
	public void whenTradeInstructioIsValid() throws Exception{
		final TradeInstruction trade = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY")
                                                                                     .withSide("BUY")
                                                                                     .withCurrencyCode("USD")
                                                                                     .withFxRate("100")
                                                                                     .withTradeUnit("100")
                                                                                     .withPricePerUnit("100")
                                                                                     .withInstructionDate("10 Jan 2018")
                                                                                     .withSettlementDate("10 Jan 2018")
                                                                                     .build();
        final boolean isValid = TradeInstructionValidator.isValidInstruction(trade);
        assertTrue(isValid);
    }



}
