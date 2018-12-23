# JPMC_TEST
TradeSettlementProcessor:

How to run :
Update file path in TradeProcessorApp.properties under main/resources
Main Class: com.jpmc.test.tradeprocess.TradeSettlementApplication
Update POM file if wants to make an executable jar file or call Main method from script?

Assumption:
1.FXRate/Price/Unit can not be zero or negative.If found the settlement amount will be defaulted to 0.
2.Used handful of countryCodes.However the codes are recommened to be ENUMs.
3. All fields mentioned in sample doc are considered mandatory.

Improvement:
1.Use Log4J for logging
2.Use Lombok/Bean-validation API to better validation.
3.BeanIO for file reading and processing.
