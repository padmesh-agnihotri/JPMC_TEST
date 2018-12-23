package com.jpmc.test.tradeprocess.constant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jpmc.test.tradeprocess.constants.SideEnum;

public class SideEnumTest {

	@Test	
	 public void testCount(){
		assertEquals(2,SideEnum.values().length);
	}
}
