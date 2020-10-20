package representation;

import java.lang.Math;

/**
 * Conversions involving decimal, binary, ones complement, and twos complement
 * 
 * @author Ava Downey
 *
 */
public class Convert {
	
	/**
	 * Conversions involving decimal, binary, ones complement, and twos complement
	 */
	public Convert() {
		
	}
	
	/**
	 * Converts a decimal number to the signed binary equivalent
	 * @param value decimal value to be converted
	 * @param bits bits to represent the value
	 * @return array that signifies the binary value of the decimal value inputed
	 */
	public int[] decimalToSignedBinary(int value, int bits) {
		
		int[] bitArray = decimalToBinary(value, bits);
		
		// check if negative or positive
		if (value >= 0) {
			// positive value or zero
			bitArray[0] = 0;
		} else {
			// negative value
			bitArray[0] = 1;
		}
		
		return bitArray;
	}

	/**
	 * Converts a decimal number to unsigned binary
	 * @param value decimal value to be converted
	 * @param bits bits to represent the value
	 * @return binary value of decimal number
	 */
	private int[] decimalToBinary(int value, int bits) {
		// convert decimal to binary
		String val = Integer.toBinaryString(Math.abs(value));
		char[] chars = val.toCharArray();
		int[] bitArray = new int[bits];
		for (int i=0; i < chars.length; i++) {
			
			int nextValue = 0;
			
			// converts from unicode to regular integers
			if (chars[i] == '1') {
				nextValue = 1;
			} else if (chars[i] == '0') {
				nextValue = 0;
			}
			
			bitArray[bits-chars.length+i] = nextValue;
			
		}
		return bitArray;
	}
	
	/**
	 * Converts a decimal number to the ones complement equivalent
	 * @param value decimal value to be converted
	 * @param bits bits to represent the value
	 * @return array that signifies the ones complement value of the decimal value inputed
	 */
	public int[] decimalToOnesComplement(int value, int bits) {
		
		// convert decimal to binary
		int[] binaryArray = decimalToBinary(value, bits);
		// switch 1s and 0s
		onesComplement(binaryArray);
		return binaryArray;
	}

	/**
	 * flips 0s and 1s so binary value is in ones complement
	 * @param binaryArray array signifying binary number
	 */
	private void onesComplement(int[] binaryArray) {
		
		for (int i=0; i < binaryArray.length; i++) {
			int nextValue = 0;
			
			// converts from unicode to regular integers and flips to make it ones complement
			if (binaryArray[i] == 0) {
				nextValue = 1;
			} else if (binaryArray[i] == 1) {
				nextValue = 0;
			}
			binaryArray[i] = nextValue;
			
		}
		
	}
	
	/**
	 * convert from ones to twos complement
	 * @param ones array that signifies a number in ones complement form
	 * @return twos complement value of ones complement value inputed
	 */
	public int[] onesToTwosComplement(int[] ones) {
		
		// add one to ones complement array
		int[] twosArray = new int[ones.length + 1];
		
		// give myself one leading bit for carry
		System.arraycopy(ones, 0, twosArray, 1, ones.length);
		
		twosArray[twosArray.length-1] = twosArray[twosArray.length-1]+1;
		
		// make sure one is carried through
		for (int i = twosArray.length-1; i >= 0; i--) {
			if (twosArray[i] > 1) {
				twosArray[i-1] = twosArray[i-1]+1;
				twosArray[i] = twosArray[i]-1;
			} else {
				break;
			}
			
		}
		
		return twosArray;
	}
	
	/**
	 * converts a number from twos complement to decimal
	 * @param twos array signifying value in twos complement
	 * @return decimal value of twos complement number
	 */
	public int twosComplementToDecimal(int[] twos) {

		int total = 0;
		
		// checks if positive or negative number then converts to decimal
		if (twos[0] == 0) {
			total = binaryToDecimal(twos);
			
		} else {
			onesComplement(twos);
			twos = onesToTwosComplement(twos);
			total = binaryToDecimal(twos);
			total = total * -1;
		}
		
		return total;
	}

	
	/**
	 * converts a signed binary number to decimal
	 * @param binary binary number in array format
	 * @return decimal equivalent of signed binary number inputed
	 */
	public int signedBinaryToDecimal(int[] binary) {
		
		boolean isNegative = false;
		
		// checks if positive or negative
		if (binary[0] == 1) {
			onesComplement(binary);
			isNegative = true;
		}
		
		int total = binaryToDecimal(binary);
		
		if (isNegative) {
			total = total * -1;
		}
		
		return total;
		
	}
	/**
	 * converts a binary number to decimal
	 * @param binary binary number in array format
	 * @return decimal equivalent of binary number inputed
	 */
	public int binaryToDecimal(int[] binary) {

		int total = 0;
		int exponent = 0;
		
		// adds all values in binary array
		for (int i=binary.length-1; i>=0; i--) {
			if (binary[i] == 1) {
				total = (int) (total + (Math.pow(2, exponent)));
			}
			exponent++;
		}

		return total;
	}
	
	/**
	 * adds two signed binary numbers
	 * @param b1 binary value in array form
	 * @param b2 binary value in array form
	 * @return binary value of b1 + b2
	 */
	public int[] addSignedBinary(int[] b1, int[] b2) {
		
		// converts to decimal, adds, converts back to binary
		int valOne = signedBinaryToDecimal(b1);
		int valTwo = signedBinaryToDecimal(b2);
		int[] total = decimalToSignedBinary(valOne + valTwo, b1.length + b2.length);
		
		return total;
		
	}
	
	/**
	 * adds two twos complement numbers
	 * @param t1 twos complement in array form
	 * @param t2 twos complement value in array form
	 * @return twos complement value of t1 + t2
	 */
	public int[] addTwosComplement(int[] t1, int[] t2) {
		
		// converts to decimal, adds, converts back to twos
		int valOne = twosComplementToDecimal(t1);
		int valTwo = twosComplementToDecimal(t2);
		int [] total = decimalToOnesComplement(valOne + valTwo, t1.length+t2.length);
		if (valOne + valTwo < 0) {
			total = onesToTwosComplement(total);
		}
		
		return total; 
	}
	
	/**
	 * multiplies two binary numbers
	 * @param b1 binary value in array form
	 * @param b2 binary value in array form
	 * @return signed binary value of b1 * b2
	 */
	public int[] multSignedBinary(int[] b1, int[] b2) {
		
		// converts to decimal, multiplies, converts back to binary
		int valOne = signedBinaryToDecimal(b1);
		int valTwo = signedBinaryToDecimal(b2);
		int[] total = decimalToSignedBinary(valOne * valTwo, b1.length + b2.length);

		return total;
	}
	
	/**
	 * multiplies two twos complement numbers
	 * @param t1 twos complement in array form
	 * @param t2 twos complement value in array form
	 * @return twos complement value of t1 * t2
	 */
	public int[] multTwosComplement(int[] t1, int[] t2) {
		
		// converts to decimal, multiplies, converts back to twos
		int valOne = twosComplementToDecimal(t1);
		int valTwo = twosComplementToDecimal(t2);
		int [] total = decimalToOnesComplement(valOne * valTwo, t1.length+t2.length);
		if (valOne + valTwo < 0) {
			total = onesToTwosComplement(total);
		}
		
		return total;
		
	}
	
	/**
	 * shift value by bitshift bits
	 * @param val value to be shifted
	 * @param bitshift number of bits to shift (negative shifts left)
	 * @return shifted value
	 */
	public int shift(int val, int bitshift) {
		if (bitshift < 0) {
			return val << -1 * bitshift;
		} else {
			return val >> bitshift;
		}
		
	}

}
