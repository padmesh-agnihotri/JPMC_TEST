package com.jpmc.test.tradeprocess.model;
/*
 * Holds the result after processing individual trade
 * TO-DO add hashcode/equal/toString method if needed.
 */
import java.time.LocalDate;

public class TradeInstructionProcessResult {
	
	private final String side;
	private final String entity;
	private final LocalDate adjustedSettlementDate;
	private final double settlementAmount;

	public TradeInstructionProcessResult(String side,
			                       String entity,
			                       LocalDate adjustedSettlementDate,
			                       double settlementAmount) {
		this.entity = entity;
		this.side = side;
		this.adjustedSettlementDate = adjustedSettlementDate;
		this.settlementAmount = settlementAmount;
	}
	
	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @return the side
	 */
	public String getSide() {
		return side;
	}


	/**
	 * @return the adjustedSettlementDate
	 */
	public LocalDate getAdjustedSettlementDate() {
		return adjustedSettlementDate;
	}
	
	/**
	 * @return the settlementAmount
	 */
	public double getSettlementAmount() {
		return settlementAmount;
	}
	

}
