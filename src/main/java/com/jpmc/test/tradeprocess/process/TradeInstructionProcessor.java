package com.jpmc.test.tradeprocess.process;
/*
 * Class to process each trade record.
 * if anyone of the price/fx/unit field is 0 or less sets settlement amount to zero.
 */
import static com.jpmc.test.tradeprocess.constants.CurrencyCodeEnum.AED;
import static com.jpmc.test.tradeprocess.constants.CurrencyCodeEnum.SAR;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jpmc.test.tradeprocess.model.TradeInstruction;
import com.jpmc.test.tradeprocess.model.TradeInstructionProcessResult;
import com.jpmc.test.tradeprocess.validator.TradeInstructionValidator;
import com.jpmc.test.tradeprocess.validator.ValidationException;

public class TradeInstructionProcessor {
	
	public TradeInstructionProcessResult processInstuction(TradeInstruction instruction) {
		try {
			TradeInstructionValidator.isValidInstruction(instruction);
		} catch (final ValidationException exception) {
			System.out.println("Invalid TradeInstruction "
					          + exception.getMessage() + " Trade details " + instruction);
			return null;
		}
		final LocalDate adjustedDate = adjustSettlementDate(instruction);
		final double settledAmountinUSD = calculateSettlementAmountInUSD(instruction.getFxRate(),
				                                                         instruction.getPricePerUnit(),
				                                                         instruction.getTradeUnit());
		
		if(settledAmountinUSD == 0) {
			System.out.println("Total settlement amount is "+settledAmountinUSD+". The Trade Instruction was "+instruction);
			//not sure if fxrate/price/tradeunit could be zero or negative but i setting total amount to zero for excercise.We can
            //add a check in TradeInstructionValidator to negative/zero value.
		}
		final TradeInstructionProcessResult instructionWrapper = new TradeInstructionProcessResult(instruction.getSide(),instruction.getEntity(), 
				                                                                                   adjustedDate,settledAmountinUSD);
		return instructionWrapper;
		
	}

	private double calculateSettlementAmountInUSD(String fxRate,String pricePerUnit,String tradeUnit) {
		if(Double.valueOf(fxRate)<=0||Double.valueOf(pricePerUnit)<=0||Double.valueOf(tradeUnit)<=0) {
			return 0;
		}
		
		return Double.valueOf(fxRate)*Double.valueOf(pricePerUnit)*Long.valueOf(tradeUnit);
	}
	
	private LocalDate adjustSettlementDate(TradeInstruction instruction) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		final LocalDate settlementDate = LocalDate.parse(instruction.getSettlementDate(), formatter);
		final String currencyCode = instruction.getCurrencyCode();
		
		if (currencyCode.equals(SAR.name())
				|| currencyCode.equals(AED.name())) {
			final LocalDate adjustedSettlementDate = checkSettlementDateForSAROrAED(instruction.getCurrencyCode(),
					                                                                settlementDate);
			return adjustedSettlementDate;
		} else {
			final LocalDate adjustedSettlementDate = checkSettlementDate(instruction.getCurrencyCode(),
					                                                     settlementDate);
			return adjustedSettlementDate;
		}		
		
	}

	private LocalDate checkSettlementDate(String currencyCode,
			LocalDate settlementDate) {
		final DayOfWeek settlementDay = settlementDate.getDayOfWeek();
		if (settlementDay == DayOfWeek.SATURDAY) {
			settlementDate = settlementDate.plusDays(2);
		} else if (settlementDay == DayOfWeek.SUNDAY) {
			settlementDate = settlementDate.plusDays(1);
		}
		return settlementDate;
	}

	private LocalDate checkSettlementDateForSAROrAED(String currencyCode,
			                                         LocalDate settlementDate) {
		final DayOfWeek settlementDay = settlementDate.getDayOfWeek();
		if (settlementDay == DayOfWeek.FRIDAY) {
			settlementDate = settlementDate.plusDays(2);
		} else if (settlementDay == DayOfWeek.SATURDAY) {
			settlementDate = settlementDate.plusDays(1);
		}
		return settlementDate;
	}
}
