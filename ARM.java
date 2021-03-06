package javaToARM;

import representation.Convert;

public class ARM {
    private RegisterBank rb;
    private RAM mb;
    private Convert convert;
    
    private int wordLength;
    private int registers;
    
    public ARM(int size_reg, int bits_reg, int size_ram, int bytes_ram) {
        rb = new RegisterBank(size_reg, bits_reg);
        mb = new RAM(size_ram, bytes_ram);
        convert = new Convert();
        
        // number of bits in a word
        wordLength = bits_reg;
        // number of registers
        registers = size_reg;
    }
    
    /**
     * copies initial values into a register
     * @param Rd index of destination register
     * @param Rn number put into Rd
     * @param bitshift number to be assigned
     */
    public void mov(int Rd, int Rn, int bitshift) {
    	Word word = new Word(wordLength, Rn, bitshift);
    	rb.set(Rd, word);
    	
    }
    
    /**
     * In a register and we want to put it back to memory
     * @param Rd destination register
     * @param Rn register with target memory address
     * @param o offset
     * @param pre pre indexed
     * @param mod_pre post indexed
     */
    public void str(int Rd, int Rn, int o, boolean pre, boolean mod_pre) {
        Word destinationWord = rb.get(Rd);
        Word memoryAddress = rb.get(Rn);
        int offsetValue = memoryAddress.getVal() + o; // default is memory address + offset (offset mode)
        if (pre) {// if a pre-Offset
			if (mod_pre == false) {// If no exclamation point
				offsetValue = memoryAddress.getVal() + o;
			} else { // is exclamation point
				offsetValue = memoryAddress.getVal();
			}
			memoryAddress.setVal(offsetValue);
			rb.set(Rn, memoryAddress);
        }
        
        mb.set(destinationWord, offsetValue); // write offset value back

    }
    
    /**
     * Loading from memory into a register
     * @param Rd destination register
     * @param Rn current register
     * @param o offset
     * @param pre
     * @param mod_pre
     */
    public void ldr(int Rd, int Rn, int o, boolean pre, boolean mod_pre) {
    	Word memoryAddress = rb.get(Rn);
    	int offsetValue = memoryAddress.getVal() + o;
    	if (pre) { // if pre-offset
    		if (mod_pre == false) { // if no exclamation point
    			offsetValue = memoryAddress.getVal() + o;	
    		} else {
    			offsetValue = memoryAddress.getVal();
    		}
    		memoryAddress.setVal(offsetValue);
    		rb.set(Rn, memoryAddress);
    	}
    	Word destinationWord = mb.get(wordLength / 8, offsetValue);
    	rb.set(Rd, destinationWord);
    }
    
    /**
     * add two values from different registers together and place in new register
     * @param Rd destination register
     * @param Rn current register
     * @param Rc value being added to Rn
     */
    public void add(int Rd, int Rn, int Rc) {
    	int sum = rb.get(Rn).getVal() + rb.get(Rc).getVal();
    	mov(Rd, sum, 0);
    }
    
    /**
     * print out values in each register
     */
    public void print() {
        int[] val;
        
        for (int i=0; i<registers; i++) {
        	val = rb.get(i).get();
        	System.out.print("REGISTER " + i + ":\t");
        	for (int j=0; j<wordLength; j++) {
        		System.out.print(val[j]);
        	}
        	
        	System.out.println();
        	
        }
        
    }
    
    public RegisterBank getRegisterBank() {
    	return rb;
    }
    
    public RAM getRAM() {
    	return mb;
    }
    
}
