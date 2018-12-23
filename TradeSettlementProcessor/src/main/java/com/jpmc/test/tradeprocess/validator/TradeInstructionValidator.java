package com.jpmc.test.tradeprocess.validator;
/*
 * Validator class to check each trade record
 * assumes the data type and date format.
 * all fiels are treated as mandatory. 
 * This could have been done using Bean-validation API or LOMBOK
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jpmc.test.tradeprocess.constants.CurrencyCodeEnum;
import com.jpmc.test.tradeprocess.constants.SideEnum;
import com.jpmc.test.tradeprocess.model.TradeInstruction;

public class TradeInstructionValidator {

	private static boolean isValidString(String fieldName, String fieldValue) throws ValidationException {
		if (fieldValue == null || fieldValue.trim() == "") {
			throw new ValidationException(fieldName + " is mandatory");
		}
		return true;
	}

	private static boolean isValidDouble(String fieldName, String fieldValue) throws ValidationException {
		try {
			Double.parseDouble(fieldValue);
		} catch (final Exception e) {
			throw new ValidationException(fieldName + " is not a valid double.Recieved "+fieldValue);
		}
		return true;
	}

	private static boolean isValidLong(String fieldName, String fieldValue) throws ValidationException {
		try {
			Long.parseLong(fieldValue);
		} catch (final Exception e) {
			throw new ValidationException(fieldName + " is not a valid long.Recieved "+fieldValue);
		}
		return true;
	}

	private static boolean isValidDate(String fieldName, String fieldValue) throws ValidationException {
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
			LocalDate.parse(fieldValue, formatter);
		} catch (final Exception e) {
			throw new ValidationException(fieldName + " is not a valid date.Recieved "+fieldValue);
		}
		return true;
	}
	private static boolean isValidCurrencyCode(final String fieldValue) throws ValidationException {
		try {
			CurrencyCodeEnum.valueOf(fieldValue);
		} catch(final Exception e) {
			throw new ValidationException(fieldValue+" is not a valid currencyCode.");
		}
		return true;
	}

	private static boolean isValidSide(final String fieldValue) throws ValidationException {
		try {
			SideEnum.valueOf(fieldValue);
		} catch(final Exception e) {
			throw new ValidationException(fieldValue+" is not a valid side.");
		}
		return true;
	}

	public static final boolean isValidInstruction(TradeInstruction tradeInstruction) throws ValidationException {
		
		if (tradeInstruction == null) {
			throw new ValidationException("Input Instruction object is null.");
		}
		isValidString("Entity", tradeInstruction.getEntity());
		isValidSide(tradeInstruction.getSide());
		isValidCurrencyCode(tradeInstruction.getCurrencyCode());
		isValidDouble("FXRate", tradeInstruction.getFxRate());
		isValidLong("TradeUnit", tradeInstruction.getTradeUnit());
		isValidDouble("PricePerUnit", tradeInstruction.getPricePerUnit());
		isValidDate("InstructionDate", tradeInstruction.getInstructionDate());
		isValidDate("SettlementDate", tradeInstruction.getSettlementDate());
		return true;
	}
}
