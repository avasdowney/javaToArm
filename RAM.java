package javaToARM;

public class RAM {
    private int[] rs;
    
    /**
     * declares array of RAM as 1s and 0s
     * @param size number of registers being used
     * @param bytes (8 bytes = 1 bit)
     */
    public RAM(int size, int bytes) {
        rs = new int[size*8*bytes];
    }
    
    /**
     * uses word class
     * @param bytes (8 bytes = 1 bit)
     * @param a offset in the RAM in bits
     * @return word from RAM based off size and offset
     */
    public Word get(int bytes, int a) {
        int bits = 8*bytes;
        int[] r = new int[bits];
        // an array of bytes as bits per register
        for(int i = 0; i < bits; i++) {
            r[i] = rs[a+i];
        }
        Word w = new Word(bits);
        w.set(r);
        return w;       
    }
    
    /**
     * sets a word into RAM based on offset
     * uses word class
     * @param w value
     * @param a offset in the RAM in bits
     */
    public void set(Word w, int a) {
        int[] r = w.get();
        for(int i = 0; i < r.length; i++) {
            rs[a+i] = r[i];
        }
        
    }
    
}