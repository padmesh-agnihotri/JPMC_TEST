package com.jpmc.test.tradeprocess.model;


/*
 * Bean to hold each trade instruction data.
 * used builder pattern for immutability,better readability and avoid having too many arguments with constructor   
 * kept the data type as String and validation in separate class
 * LOMBOK or bean-validation apis could have made things easier
 */
public class TradeInstruction {
	private final String entity;
	private final String side;
	private final String fxRate;
	private final String currencyCode;
	private final String instructionDate;
	private final String settlementDate;
	private final String tradeUnit;
	private final String pricePerUnit;

	private TradeInstruction(TradeInstructionBuilder builder) {
		entity = builder.entity;
		side = builder.side;
		fxRate = builder.fxRate;
		currencyCode = builder.currencyCode;
		instructionDate = builder.instructionDate;
		settlementDate = builder.settlementDate;
		tradeUnit = builder.tradeUnit;
		pricePerUnit = builder.pricePerUnit;
	}

	public String getEntity() {
		return entity;
	}

	public String getSide() {
		return side;
	}

	public String getFxRate() {
		return fxRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getInstructionDate() {
		return instructionDate;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public String getTradeUnit() {
		return tradeUnit;
	}

	public String getPricePerUnit() {
		return pricePerUnit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (currencyCode == null ? 0 : currencyCode.hashCode());
		result = prime * result + (entity == null ? 0 : entity.hashCode());
		result = prime * result + (fxRate == null ? 0 : fxRate.hashCode());
		result = prime * result
				+ (instructionDate == null ? 0 : instructionDate.hashCode());
		result = prime * result
				+ (pricePerUnit == null ? 0 : pricePerUnit.hashCode());
		result = prime * result
				+ (settlementDate == null ? 0 : settlementDate.hashCode());
		result = prime * result + (side == null ? 0 : side.hashCode());
		result = prime * result
				+ (tradeUnit == null ? 0 : tradeUnit.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TradeInstruction [entity=" + entity + ", side=" + side
				+ ", fxRate=" + fxRate + ", currencyCode=" + currencyCode
				+ ", instructionDate=" + instructionDate + ", settlementDate="
				+ settlementDate + ", tradeUnit=" + tradeUnit
				+ ", pricePerUnit=" + pricePerUnit + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TradeInstruction)) {
			return false;
		}
		final TradeInstruction other = (TradeInstruction) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null) {
				return false;
			}
		} else if (!currencyCode.equals(other.currencyCode)) {
			return false;
		}
		if (entity == null) {
			if (other.entity != null) {
				return false;
			}
		} else if (!entity.equals(other.entity)) {
			return false;
		}
		if (fxRate == null) {
			if (other.fxRate != null) {
				return false;
			}
		} else if (!fxRate.equals(other.fxRate)) {
			return false;
		}
		if (instructionDate == null) {
			if (other.instructionDate != null) {
				return false;
			}
		} else if (!instructionDate.equals(other.instructionDate)) {
			return false;
		}
		if (pricePerUnit == null) {
			if (other.pricePerUnit != null) {
				return false;
			}
		} else if (!pricePerUnit.equals(other.pricePerUnit)) {
			return false;
		}
		if (settlementDate == null) {
			if (other.settlementDate != null) {
				return false;
			}
		} else if (!settlementDate.equals(other.settlementDate)) {
			return false;
		}
		if (side == null) {
			if (other.side != null) {
				return false;
			}
		} else if (!side.equals(other.side)) {
			return false;
		}
		if (tradeUnit == null) {
			if (other.tradeUnit != null) {
				return false;
			}
		} else if (!tradeUnit.equals(other.tradeUnit)) {
			return false;
		}
		return true;
	}

	public static class TradeInstructionBuilder {
		private String entity;
		private String side;
		private String fxRate;
		private String currencyCode;
		private String instructionDate;
		private String settlementDate;
		private String tradeUnit;
		private String pricePerUnit;

		public TradeInstructionBuilder withEntity(String entity) {
			this.entity = entity;
			return this;
		}

		public TradeInstructionBuilder withSide(String side) {
			this.side = side;
			return this;
		}

		public TradeInstructionBuilder withFxRate(String fxRate) {
			this.fxRate = fxRate;
			return this;
		}

		public TradeInstructionBuilder withCurrencyCode(
				String currencyCode) {
			this.currencyCode = currencyCode;
			return this;
		}

		public TradeInstructionBuilder withInstructionDate(
				String instructionDate) {
			this.instructionDate = instructionDate;
			return this;
		}

		public TradeInstructionBuilder withSettlementDate(
				String settlementDate) {
			this.settlementDate = settlementDate;
			return this;
		}

		public TradeInstructionBuilder withTradeUnit(String tradeUnit) {
			this.tradeUnit = tradeUnit;
			return this;
		}

		public TradeInstructionBuilder withPricePerUnit(String pricePerUnit) {
			this.pricePerUnit = pricePerUnit;
			return this;
		}

		public TradeInstruction build() {
			return new TradeInstruction(this);
		}

	}

}
