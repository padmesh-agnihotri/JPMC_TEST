package com.jpmc.test.tradeprocess;

import static com.jpmc.test.tradeprocess.constants.SideEnum.BUY;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import com.jpmc.test.tradeprocess.model.TradeInstruction;
import com.jpmc.test.tradeprocess.model.TradeInstructionProcessResult;
import com.jpmc.test.tradeprocess.process.ReportGenerator;
import com.jpmc.test.tradeprocess.process.TradeInstructionProcessor;

/**
 * Main class to read the instruction file and process each record.
 * 
 *
 */
public class TradeSettlementApplication {
	private static final String FILE_SEPARATOR = ",";
	private static final String APP_CONFIG_FILE = "TradeProcessorApp.properties";
	private static final String INSTRUCTION_FILE_LOCATION_KEY = "jpmc.test.instruction.file.location";

	public static void main(String[] args) throws Exception {
		final TradeSettlementApplication processor = new TradeSettlementApplication();
		processor.process();
	}

	private void process() throws Exception {
		final Properties properties = new Properties();
		final File file = new File(getClass().getClassLoader().getResource(APP_CONFIG_FILE).getFile());
		properties.load(new FileInputStream(file));
		final String filePath = properties.getProperty(INSTRUCTION_FILE_LOCATION_KEY);
		List<TradeInstruction> tradeInstructionList = new ArrayList<TradeInstruction>();
		try {
		tradeInstructionList = readAndParseFile(filePath);
		}catch(final Exception e) {
			System.out.println("Unable to read instructions from file at "+filePath+" Terminating the process.. "+e.getMessage());
			return;
		}
		final List<String> invalidInstructionList = new ArrayList<>();
		final List<TradeInstructionProcessResult> buyTradeInstructionList = new ArrayList<>();
		final List<TradeInstructionProcessResult> sellTradeInstructionList = new ArrayList<>();
		final TradeInstructionProcessor processor = new TradeInstructionProcessor();
		final ReportGenerator reportGenerator = new ReportGenerator();
		
		for(final TradeInstruction instruction:tradeInstructionList) {
			final TradeInstructionProcessResult processResult = processor.processInstuction(instruction);
			
			if (processResult == null) {
				invalidInstructionList.add(instruction.toString());
				continue;
			}
			if(processResult.getSide().equals(BUY.name())) {
				buyTradeInstructionList.add(processResult);
			} else {
				sellTradeInstructionList.add(processResult);
			}
		}
		reportGenerator.generateReports(buyTradeInstructionList,"OUTGOING");
		reportGenerator.generateReports(sellTradeInstructionList,"INCOMING");
		reportGenerator.reportInvalidInstructions(invalidInstructionList);
		


	}

	private List<TradeInstruction> readAndParseFile(final String filePath) throws IOException {
       try(final Stream<String> fileContentByLine = Files.lines(Paths.get(filePath),StandardCharsets.UTF_8)){
    	   
		final List<TradeInstruction> tradeInstructionList =  fileContentByLine.filter(line -> line !=null)
				                                                              .filter(line -> line.length()!=0)
				                                                              .map(line -> line.split(FILE_SEPARATOR))
				                                                              .map(lineSplit -> parseLineToTradeObject(lineSplit))
				                                                              .filter(tradeObj -> tradeObj != null)
				                                                              .collect(toList());

		return tradeInstructionList;
       }
	}

	private  TradeInstruction parseLineToTradeObject(String[] line) {
		if(line.length < 8) {
			System.out.println("Unparsable Instruction : "+String.join(" ", line));
			return null;
		}
		return new TradeInstruction.TradeInstructionBuilder().withEntity(line[0]).withSide(line[1]).withFxRate(line[2])
				                                             .withCurrencyCode(line[3]).withInstructionDate(line[4])
				                                             .withSettlementDate(line[5]).withTradeUnit(line[6])
				                                             .withPricePerUnit(line[7]).build();

	}
}
