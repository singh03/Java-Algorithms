/*
Problem Statement : 
Given a string s, find the length of the longest 
substring
 without repeating characters.

 

Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 

Constraints:

0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.
*/

class Solution {
    public int lengthOfLongestSubstring(String str) {
        
        
        HashMap<Character , Integer> map = new HashMap<Character, Integer>();
		int i=0, j=0, size =str.length();
		int max = 0;
		while(j<size) {
			
			
			char ch = str.charAt(j);
			if(map.containsKey(ch)) {
				map.put(ch, map.get(ch)+1);
			}
			else
				map.put(ch,1);
			
			if(map.size() == j-i+1) {
				max = Math.max(max, j-i+1);
			}
			else if(map.size()< j-i+1) {
				while(map.size()< j-i+1) {
					map.put(str.charAt(i), map.get(str.charAt(i))-1);
					if(map.get(str.charAt(i)) == 0)
						map.remove(str.charAt(i));
					
					i++;
				}
				//j++;
			}
			j++;
		}
		return max;
		
	}
}
