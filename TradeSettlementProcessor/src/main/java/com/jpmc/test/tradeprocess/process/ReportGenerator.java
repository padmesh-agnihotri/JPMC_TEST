package com.jpmc.test.tradeprocess.process;

/*
 * class to generate report.The output is printed on the console 
 * but could be moved to file or DB
 */
import static java.lang.String.join;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.jpmc.test.tradeprocess.model.TradeInstructionProcessResult;

public class ReportGenerator {
	
	public void generateReports(final List<TradeInstructionProcessResult> tradeInstructionList,String side) {
		if (tradeInstructionList.size()==0) {
			System.out.println("No instructions to report for side : "+side);
			return;
		}
		System.out.println("Report for "+side+" instructions");
		System.out.println("########### Total amount settled grouped by Settlement Date ##########");
		generateReportForTotalSettlementAmountByDay(tradeInstructionList);
		System.out.println("########### Entities ranking based on settlement Amount #############");
		generateReportForEntityRankingBySettlementAmount(tradeInstructionList);
	}
	
	public void reportInvalidInstructions(List<String> invalidInstructions) {
		if (invalidInstructions.size()==0) {
			System.out.println("No invalid instruction to report");
			return;
		}
		
		System.out.println("########### Following instructions failed during validation ################");
		invalidInstructions.stream().forEach(System.out::println);
	}
	
	private void generateReportForTotalSettlementAmountByDay(final List<TradeInstructionProcessResult> tradeInstructionListBySide){
		final TreeMap<LocalDate,Double> dateWithSettlementAmount =  tradeInstructionListBySide.stream().collect(
				                                                     toMap(TradeInstructionProcessResult::getAdjustedSettlementDate,
                                                                     TradeInstructionProcessResult::getSettlementAmount,
                                                                     (existingAmount,newAmount)->existingAmount+newAmount,
                                                                     TreeMap::new));
		
		dateWithSettlementAmount.entrySet().stream()
		                                   .forEach(entry -> System.out.println(" SettlementDate: "+entry.getKey()+
				                                                                " Total Amount settled: "+entry.getValue()));
	}
	
	private void generateReportForEntityRankingBySettlementAmount(final List<TradeInstructionProcessResult> tradeInstructionListBySide) {
		final TreeMap<Double,String> entityWithAmount = tradeInstructionListBySide.stream().collect(toMap(TradeInstructionProcessResult::getSettlementAmount,
                                                           TradeInstructionProcessResult::getEntity,
                                                           (existingEntity,newEntity)->join(", ", existingEntity,  newEntity),
                                                           TreeMap::new));
		final AtomicInteger index = new AtomicInteger();
		entityWithAmount.descendingMap().entrySet().stream()
                                   .forEach(entry -> System.out.println(" Ranking: "+index.incrementAndGet()+
                                		                                " ## Entities : "+entry.getValue()+
                                                                        " ## Total Amount settled: "+entry.getKey()));
	}

}
