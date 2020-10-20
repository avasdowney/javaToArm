package javaToARM;

public class HW2 {

    public static void main(String[] args) {
        /*  (1) write a short program using the java ARM class that is like this C++ code:
            int x = 13;
            int y = 14;
            int z = 16;
            z = x+y+z;
        Note: assume 32bit ARM and RAM should have 4 byte alignments (4*bytes)
            (2) call the print method periodically to check your work or use the debugger
        */
    	ARM arm = new ARM(16, 32, 1, 4096);
    	// use mov to add a value to the register bank
		arm.mov(2, 0b010, 0);
		Word word = arm.getRegisterBank().get(2);
		arm.mov(7, 184756, 0);
		word = arm.getRegisterBank().get(7);
    	arm.print();
    }
    
}

