package com.jpmc.test.tradeprocess.process;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.test.tradeprocess.model.TradeInstruction;


public class ReportGeneratorTest {
	
	private final String LOG_FILE_PATH = "target\\Logs.txt";
	
	@Before
	public void setup() throws FileNotFoundException {
		new File(LOG_FILE_PATH).delete();
		System.setOut(new PrintStream(new File(LOG_FILE_PATH)));
	}
	
	@Test
	public void validateSettlementByDateAndEntityReport(){
		final TreeMap<String,Double> settledAmountByDate = getSettledAmountByDate();
		final HashMap<String,Double> entitiesSettlementAmountMap = getEntitiesSettlementAmountMap();
		new ReportGenerator().generateReports(settledAmountByDate,entitiesSettlementAmountMap, "OUTGOING");
		validateLogs(" SettlementDate: 2018-12-22 Total Amount settled: 110.0",
				     " SettlementDate: 2018-12-23 Total Amount settled: 190.0",
				     " Ranking: 1 ## Entities : [entity1, entity2] ## Total Amount settled: 100.0",
				     " Ranking: 2 ## Entities : [entity] ## Total Amount settled: 90.0",
				     " Ranking: 3 ## Entities : [entity3] ## Total Amount settled: 10.0");
	}
	
	private HashMap<String,Double> getEntitiesSettlementAmountMap() {
		final HashMap<String,Double> entitiesSettlementAmountMap = new HashMap<String,Double>();
		entitiesSettlementAmountMap.put("entity1",100.0);
		entitiesSettlementAmountMap.put("entity2",100.0);
		entitiesSettlementAmountMap.put("entity", 90.0);
		entitiesSettlementAmountMap.put("entity3",10.0);
		return entitiesSettlementAmountMap;
	}

	private TreeMap<String, Double> getSettledAmountByDate() {
		final TreeMap<String,Double> settledAmountByDate = new TreeMap<String,Double>();
		settledAmountByDate.put("2018-12-22",110.0);
		settledAmountByDate.put("2018-12-23",190.0);
		return settledAmountByDate;
	}

	@Test
	public void whenInstructionListIsNull() {
		new ReportGenerator().generateReports(new TreeMap<String,Double>(),new HashMap<String,Double>(),"OUTGOING");
		validateLogs("No instructions to report for side : OUTGOING");
	}
	
	@Test
	public void validateInvalidInstructionsReport(){
		final ArrayList<String> list = getInvalidTransactions();
		new ReportGenerator().reportInvalidInstructions(list);
		validateLogs("########### Following instructions failed during validation ################",
				     "TradeInstruction [entity=ENTITY, side=BUY, fxRate=100, currencyCode=USD, instructionDate=10 Jan 2018, settlementDate=10 Jan 2018, tradeUnit=100, pricePerUnit=100]",
				     "TradeInstruction [entity=ENTITY, side=BUY, fxRate=100, currencyCode=USD, instructionDate=10 Jan 2018, settlementDate=10 Jan 2018, tradeUnit=100, pricePerUnit=100]");
	}

	private ArrayList<String> getInvalidTransactions() {
		final TradeInstruction trade1 = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY").withCurrencyCode("USD")
                                                                                     .withFxRate("100").withTradeUnit("100").withPricePerUnit("100")
                                                                                     .withInstructionDate("10 Jan 2018").withSettlementDate("10 Jan 2018")
                                                                                     .build();
		final TradeInstruction trade2 = new TradeInstruction.TradeInstructionBuilder().withEntity("ENTITY").withSide("BUY").withCurrencyCode("USD")
                                                                                      .withFxRate("100").withTradeUnit("100").withPricePerUnit("100")
                                                                                      .withInstructionDate("10 Jan 2018").withSettlementDate("10 Jan 2018")
                                                                                      .build();
		final ArrayList<String> list = new ArrayList<String>();
		list.add(trade1.toString());
		list.add(trade2.toString());
		return list;
	}
	
	private void validateLogs(String ... logs){
		try {
		final List<String> logList =  Files.readAllLines(Paths.get(LOG_FILE_PATH));
		assertTrue(logList.containsAll(Arrays.asList(logs)));
		}catch(final Exception e){
			e.printStackTrace();
		}
	}
	

}
