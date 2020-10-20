package javaToARM;

import java.util.ArrayList;

public class RegisterBank {
    private ArrayList<Word> rs;
    
    /**
     * creates a registerbank of words 
     * word is null if register is empty
     * @param size amount of registers
     * @param bits should be 32 to be a word
     */
    public RegisterBank(int size, int bits) {
        rs = new ArrayList<Word>();
        // instantiates the list of objects Word with bit size
        for(int i = 0; i < size; i++){
            rs.add(new Word(bits));
        }
    }
    
    /**
     * returns the array within the Word object of index
     * @param index register to get word from
     * @return the array within the Word object of index
     */
    public Word get(int index) {
        int[] i = rs.get(index).get();
        Word r = new Word(i.length);
        r.set(i);
        return r;
    }
    
    /**
     * sets an array for Word in a given index
     * @param index 
     * @param r register
     */
    public void set(int index, Word r) {
        rs.get(index).set(r.get());
    }
}

