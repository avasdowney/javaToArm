package javaToARM;

import representation.Convert;

public class Word {
    private int[] bs;
    public int bits;
    private Convert convert;
    
    /**
     * part of the instruction... the part being manipulated
     * to be set and get
     * @param bits should be 32 since it is a word
     */
    public Word(int bits) {
    	convert = new Convert();
        bs = new int[bits];
        this.bits = bits;
    }
    
    /**
     * initialize a word with a value and bitshift it
     * @param bits number of bits in the word
     * @param val integer value of word
     * @param bitshift number of bits to be shifted (negative if left shift)
     */
    public Word(int bits, int val, int bitshift) {
    	this(bits);
    	int[] valBits = convert.decimalToSignedBinary(convert.shift(val, bitshift), bits);
    	set(valBits);
    }
    /**
     * returns word object
     * @return word object
     */
    public int[] get() {
        int[] r = new int[this.bs.length];
        for(int i = 0; i < this.bs.length; i++) {
            r[i] = this.bs[i];
        }
        return r;
    }
    
    /**
     * accepts a word and sets it to word object
     * @param r word object
     */
    public void set(int[] r) {
        for(int i = 0; i < r.length; i++) {
            this.bs[i] = r[i];
        }
        
    }
    
    /**
     * Converts from binary value of the word to decimal
     * @return integer value of the word
     */
    public int getVal() {
    	return convert.signedBinaryToDecimal(get());
    }
    
    /**
     * sets an integer value into a word
     * @param val value of integer
     */
    public void setVal(int val) {
    	int[] valBits = convert.decimalToSignedBinary(convert.shift(val, 0), bits);
    	set(valBits);
    }
    
}
