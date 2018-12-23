package com.jpmc.test.tradeprocess.process;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.test.tradeprocess.model.TradeInstruction;
import com.jpmc.test.tradeprocess.model.TradeInstructionProcessResult;


public class ReportGeneratorTest {
	
	private final String LOG_FILE_PATH = "target\\Logs.txt";
	
	@Before
	public void setup() throws FileNotFoundException {
		new File(LOG_FILE_PATH).delete();
		System.setOut(new PrintStream(new File(LOG_FILE_PATH)));
	}
	
	@Test
	public void validateSettlementByDateAndEntityReport(){
		final ArrayList<TradeInstructionProcessResult> list = getInstructionList();
		new ReportGenerator().generateReports(list, "OUTGOING");
		validateLogs(" SettlementDate: 2018-12-22 Total Amount settled: 110.0",
				     " SettlementDate: 2018-12-23 Total Amount settled: 190.0",
				     " Ranking: 1 ## Entities : entity2, entity1 ## Total Amount settled: 100.0",
				     " Ranking: 2 ## Entities : entity ## Total Amount settled: 90.0",
				     " Ranking: 3 ## Entities : entity3 ## Total Amount settled: 10.0");
	}
	
	@Test
	public void whenInstructionListIsEmpty() {
		new ReportGenerator().generateReports(emptyList(),"OUTGOING");
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

	private ArrayList<TradeInstructionProcessResult> getInstructionList() {
		final TradeInstructionProcessResult result1 = new TradeInstructionProcessResult("BUY","entity",LocalDate.now(),90);
		final TradeInstructionProcessResult result2 = new TradeInstructionProcessResult("BUY","entity1",LocalDate.now(),100);
		final TradeInstructionProcessResult result3 = new TradeInstructionProcessResult("BUY","entity2",LocalDate.now().minusDays(1),100);
		final TradeInstructionProcessResult result4 = new TradeInstructionProcessResult("BUY","entity3",LocalDate.now().minusDays(1),10);
		final ArrayList<TradeInstructionProcessResult> list = new ArrayList<TradeInstructionProcessResult>();
		list.add(result4);
		list.add(result3);
		list.add(result2);
		list.add(result1);
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
