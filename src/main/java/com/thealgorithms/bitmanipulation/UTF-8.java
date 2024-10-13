/*

Problem Statement : 


Given an integer array data representing the data, return whether it is a valid UTF-8 encoding (i.e. it translates to a sequence of valid UTF-8 encoded characters).

A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:

For a 1-byte character, the first bit is a 0, followed by its Unicode code.
For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
This is how the UTF-8 encoding would work:

     Number of Bytes   |        UTF-8 Octet Sequence
                       |              (binary)
   --------------------+-----------------------------------------
            1          |   0xxxxxxx
            2          |   110xxxxx 10xxxxxx
            3          |   1110xxxx 10xxxxxx 10xxxxxx
            4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
x denotes a bit in the binary form of a byte that may be either 0 or 1.

Note: The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.

 

Example 1:

Input: data = [197,130,1]
Output: true
Explanation: data represents the octet sequence: 11000101 10000010 00000001.
It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
Example 2:

Input: data = [235,140,4]
Output: false
Explanation: data represented the octet sequence: 11101011 10001100 00000100.
The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
The next byte is a continuation byte which starts with 10 and that's correct.
But the second continuation byte does not start with 10, so it is invalid.
 

Constraints:

1 <= data.length <= 2 * 104
0 <= data[i] <= 255



Solution : 

*/



class Solution {
    // Bit masks to identify different UTF-8 byte patterns
    private static final int bit_7_mask = 1 << 7; // 10000000 - used to check the most significant bit
    private static final int bit_6_mask = 1 << 6; // 01000000 - used for checking two-byte characters
    private static final int bit_5_mask = 1 << 5; // 00100000 - used for checking three-byte characters
    private static final int bit_4_mask = 1 << 4; // 00010000 - used for checking four-byte characters
    private static final int bit_3_mask = 1 << 3; // 00001000 - used for checking invalid cases

    /**
     * Validates whether the given integer array represents a valid UTF-8 encoding.
     * @param data an array of integers representing bytes.
     * @return true if the array represents a valid UTF-8 encoding, false otherwise.
     */
    public boolean validUtf8(int[] data) {
        int size = data.length;
        
        int i = 0; // Index to iterate through the data array.
        int count = 1; // Counts the bytes required for the current UTF-8 character.
        int first, byteZ; // `first` stores the current byte, `byteZ` stores the number of bytes in a character.
        
        // Iterate through each byte in the input data
        while(i < size) {
            first = data[i++]; // Get the current byte and increment the index.
            byteZ = getBytes(first); // Determine how many bytes this UTF-8 character should have.
            
            // If `byteZ` is -1, it means the byte is not a valid starting byte.
            if(byteZ == -1)
                return false;
            
            // Validate the subsequent bytes if more than one byte is required.
            while(count < byteZ) {
                count++; // Increment count to track the number of bytes processed for the current character.
                // Check if there's enough bytes and if each subsequent byte starts with "10xxxxxx" pattern.
                if ((i == size) || (data[i++] < bit_7_mask))
                    return false;
            }
            count = 1; // Reset count for the next character.
        }
        
        return true; // Return true if all bytes are validated correctly.
    }

    /**
     * Determines the number of bytes a UTF-8 character should have based on its first byte.
     * @param first the first byte of a UTF-8 character.
     * @return the number of bytes required for the character, or -1 if invalid.
     */
    private int getBytes(int first) {
        int i = 0;

        // If the first bit is 0 (0xxxxxxx), it is a 1-byte character (ASCII).
        if((first & bit_7_mask) == 0)
            return 1;
        
        // If the first bit is 1 and the second bit is 0 (10xxxxxx), it is invalid as a starting byte.
        else if((first & bit_6_mask) == 0)
            return -1;
        
        // If the first two bits are "110" (110xxxxx), it is a 2-byte character.
        else if((first & bit_5_mask) == 0)
            return 2;
        
        // If the first three bits are "1110" (1110xxxx), it is a 3-byte character.
        else if((first & bit_4_mask) == 0)
            return 3;
        
        // If the first four bits are "11110" (11110xxx), it is a 4-byte character.
        else if((first & bit_3_mask) == 0)
            return 4;
        
        // Any other pattern is invalid.
        return -1;
    }
}
