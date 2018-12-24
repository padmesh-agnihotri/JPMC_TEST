package com.jpmc.test.tradeprocess;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for TradeSettlementApplication.
 */

public class TradeSettlementApplicationIntegrationTest {
	private final String FILE_PATH = "target\\TradeInstructions.txt";
	private final String LOG_FILE_PATH = "target\\Logs.txt";
	private final String[] EMPTY_ARRAY = {""};
	
	@Before
	public void setup() throws FileNotFoundException {
		new File(FILE_PATH).delete();
		new File(LOG_FILE_PATH).delete();
		System.setOut(new PrintStream(new File(LOG_FILE_PATH)));
	}

	@Test
	public void whenFileIsNotPresent() throws Exception {
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Unable to read instructions from file at target\\TradeInstructions.txt "
	               + "Terminating the process.. target\\TradeInstructions.txt");
	}

	@Test
	public void whenFileContainsUnparsableRecords() throws Exception {
		writeDataToFile("INVALID TRADE NO DATA");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Unparsable Instruction : INVALID TRADE NO DATA");
	}

	
	@Test
	public void whenFileContainsInvalidRecords() throws Exception {
		writeDataToFile("ENTITY,INVALID_SIDE,10,USD,23 Dec 2018,23 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Invalid TradeInstruction INVALID_SIDE is not a valid side. Trade details TradeInstruction [entity=ENTITY, side=INVALID_SIDE, fxRate=10, currencyCode=USD, instructionDate=23 Dec 2018, settlementDate=23 Dec 2018, tradeUnit=10, pricePerUnit=100]",
				     "########### Following instructions failed during validation ################",
				     "TradeInstruction [entity=ENTITY, side=INVALID_SIDE, fxRate=10, currencyCode=USD, instructionDate=23 Dec 2018, settlementDate=23 Dec 2018, tradeUnit=10, pricePerUnit=100]");
	}
	
	

	@Test
	public void whenSettlementDateFallsOnWeekdays() throws Exception {
	    writeDataToFile("ENTITY,BUY,10,USD,20 Dec 2018,20 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-20 Total Amount settled: 10000.0",
				     " Ranking: 1 ## Entities : [ENTITY] ## Total Amount settled: 10000.0",
				     "No instructions to report for side : INCOMING");
	}

	@Test
	public void whenSettlementDateFallsOnWeekends() throws Exception {
		writeDataToFile("ENTITY,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-24 Total Amount settled: 10000.0",
				     " Ranking: 1 ## Entities : [ENTITY] ## Total Amount settled: 10000.0",
				     "No instructions to report for side : INCOMING");
	}

	@Test
	public void whenCurrencyTypeIsAEDAndSettlementDayIsFriday() throws Exception {
		writeDataToFile("ENTITY,BUY,10,AED,21 Dec 2018,21 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-23 Total Amount settled: 10000.0",
				     " Ranking: 1 ## Entities : [ENTITY] ## Total Amount settled: 10000.0",
				     "No instructions to report for side : INCOMING");
	}

	@Test
	public void whenCurrencyTypeIsAEDAndSettlementDayIsSunday() throws Exception {
		writeDataToFile("ENTITY,BUY,10,AED,23 Dec 2018,23 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-23 Total Amount settled: 10000.0",
				     " Ranking: 1 ## Entities : [ENTITY] ## Total Amount settled: 10000.0",
				     "No instructions to report for side : INCOMING");
	}
	@Test
	public void whenSettlementDayIsSaturdayAndCurrencyIsNotAEDOrSAR() throws Exception {
		writeDataToFile("ENTITY,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-24 Total Amount settled: 10000.0",
				     " Ranking: 1 ## Entities : [ENTITY] ## Total Amount settled: 10000.0",
				     "No instructions to report for side : INCOMING");
	}

	@Test
	public void whenMultipleEntitiesHaveSameSettledAmountAndMultipleTrades() throws Exception{
		writeDataToFile("ENTITY,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY1,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY1,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY2,BUY,2,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY3,BUY,2,USD,22 Dec 2018,22 Dec 2018,10,100",
				        "\n",
				        "ENTITY4,BUY,1,USD,22 Dec 2018,22 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-24 Total Amount settled: 45000.0",
				     " Ranking: 1 ## Entities : [ENTITY, ENTITY1] ## Total Amount settled: 20000.0",
				     " Ranking: 2 ## Entities : [ENTITY2, ENTITY3] ## Total Amount settled: 2000.0",
				     " Ranking: 3 ## Entities : [ENTITY4] ## Total Amount settled: 1000.0",
				     "No instructions to report for side : INCOMING");
	}
	@Test
	public void whenMultipleEntitiesHaveDifferentSettlementDay() throws Exception{
		writeDataToFile("ENTITY,BUY,10,USD,22 Dec 2018,22 Dec 2018,10,100",
		                "\n",
		                "ENTITY1,BUY,10,AED,22 Dec 2018,22 Dec 2018,10,100",
		                "\n",
		                "ENTITY2,BUY,2,USD,21 Dec 2018,22 Dec 2018,10,100",
		                "\n",
		                "ENTITY3,BUY,2,USD,23 Dec 2018,22 Dec 2018,10,100",
		                "\n",
		                "ENTITY4,BUY,1,USD,22 Dec 2018,22 Dec 2018,10,100");
		TradeSettlementApplication.main(EMPTY_ARRAY);
		validateLogs("Report for OUTGOING instructions",
				     " SettlementDate: 2018-12-23 Total Amount settled: 10000.0",
				     " SettlementDate: 2018-12-24 Total Amount settled: 15000.0",
				     " Ranking: 1 ## Entities : [ENTITY, ENTITY1] ## Total Amount settled: 10000.0",
				     " Ranking: 2 ## Entities : [ENTITY2, ENTITY3] ## Total Amount settled: 2000.0",
				     " Ranking: 3 ## Entities : [ENTITY4] ## Total Amount settled: 1000.0",
				     "No instructions to report for side : INCOMING");
	}
	
	private void validateLogs(String ... logs) throws IOException {
		final List<String> logList =  Files.readAllLines(Paths.get(LOG_FILE_PATH));
		assertTrue(logList.containsAll(Arrays.asList(logs)));
	}
	private void writeDataToFile(String ... rows) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_PATH))))
		{
			for (final String row : rows) {
		    writer.write(row);
			}
		}
	}
}
