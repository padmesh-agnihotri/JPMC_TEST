package com.jpmc.test.tradeprocess.constant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jpmc.test.tradeprocess.constants.CurrencyCodeEnum;

public class CurrencyCodeEnumTest {

@Test	
 public void testCount(){
	assertEquals(5,CurrencyCodeEnum.values().length);
}
}
