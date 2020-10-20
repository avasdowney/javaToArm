package javaToARM;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ARMTest {

	@Test
	public void testMOV() {
		ARM arm = new ARM(16, 32, 32, 32);
		// use mov to add a value to the register bank
		arm.mov(2, 0b010, 0);
		Word word = arm.getRegisterBank().get(2);
		assertEquals(0b010, word.getVal());
	}
	
	@Test
	public void testMOVBitshift() {
		ARM arm = new ARM(16, 32, 32, 32);
		// use mov to add a value to the register bank
		// test positive bitshift
		arm.mov(2, 0b010, 1);
		Word word = arm.getRegisterBank().get(2);
		assertEquals(0b001, word.getVal());
		// test negative bitshift
		arm.mov(2, 0b010, -1);
		Word word2 = arm.getRegisterBank().get(2);
		assertEquals(0b100, word2.getVal());
	}
	
	@Test
	public void testSTR() {
		ARM arm = new ARM(16, 32, 1, 4096);
		// this stores 0b010 in register 2
		arm.mov(2, 0b010, 0);
		// store memory address in register 0
		arm.mov(0, 32, 0);
		arm.str(2, 0, 0, false, false);
		RAM ram = arm.getRAM();
		// manually retrieving the word from an offset of 32
		Word word = ram.get(4, 32);
		assertEquals(0b010, word.getVal());
	}
	
	@Test
	public void testLDR() {
		ARM arm = new ARM(16, 32, 1, 4096);
		// store known value into RAM
		RAM ram = arm.getRAM();
		Word word = new Word(32, 0b010, 0);
		ram.set(word, 32);
		// store memory address 32 in register 1
		arm.mov(1, 32, 0);
		// load the word from RAM into register 32
		arm.ldr(2, 1, 0, false, false);
		Word word2 = arm.getRegisterBank().get(2);
		assertEquals(0b010, word2.getVal());
	}
	
	@Test
	public void testAdd() {
		ARM arm = new ARM(16, 32, 1, 4096);
		arm.mov(2, 0b010, 0);
		arm.add(4, 2, 0b010);
		Word sum = arm.getRegisterBank().get(4);
		assertEquals(0b100, sum.getVal());
	}

}
