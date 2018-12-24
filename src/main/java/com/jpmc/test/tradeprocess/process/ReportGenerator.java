package com.jpmc.test.tradeprocess.process;

/*
 * class to generate report.The output is printed on the console 
 * but could be moved to file or DB
 */
import static java.util.Collections.reverseOrder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportGenerator {
	
	public void generateReports(TreeMap<String,Double> settledAmountByDate,Map<String,Double> entitySettlementAmountMap,String side) {
		//safe to check either of the two maps
		if (settledAmountByDate == null || settledAmountByDate.size()==0) {
			System.out.println("No instructions to report for side : "+side);
			return;
		}
		System.out.println("Report for "+side+" instructions");
		System.out.println("########### Total amount settled grouped by Settlement Date ##########");
		generateReportForTotalSettlementAmountByDay(settledAmountByDate);
		System.out.println("########### Entities ranking based on settlement Amount #############");
		generateReportForEntityRankingBySettlementAmount(entitySettlementAmountMap);
	}
	
	public void reportInvalidInstructions(List<String> invalidInstructions) {
		if (invalidInstructions.size()==0) {
			System.out.println("No invalid instruction to report");
			return;
		}
		
		System.out.println("########### Following instructions failed during validation ################");
		invalidInstructions.stream().forEach(System.out::println);
	}
	
	private void generateReportForTotalSettlementAmountByDay(final TreeMap<String,Double> settledAmountByDate){
		settledAmountByDate.entrySet().stream()
		                                   .forEach(entry -> System.out.println(" SettlementDate: "+entry.getKey()+
				                                                                " Total Amount settled: "+entry.getValue()));
	}
	/*
	 * Create treemap to have amount as key and list of entities as list.The argument entitySettlementAmountMap
	 * contains Entity with its total settled amount.
	 */
	private void generateReportForEntityRankingBySettlementAmount(final Map<String,Double> entitySettlementAmountMap) {
		final TreeMap<Double,Set<String>> entityOrderedByAmount = new TreeMap<Double,Set<String>>(reverseOrder());
		entitySettlementAmountMap.entrySet().stream().forEach(entry -> entityOrderedByAmount.computeIfAbsent(
				                                                                     entry.getValue(),k -> new HashSet<String>()).add(entry.getKey()));
		final AtomicInteger index = new AtomicInteger();
		entityOrderedByAmount.entrySet().stream()
                                   .forEach(entry -> System.out.println(" Ranking: "+index.incrementAndGet()+
                                		                                " ## Entities : "+entry.getValue()+
                                                                        " ## Total Amount settled: "+entry.getKey()));
	}

}
