/*

Given a string s, return the longest 
palindromic
 
substring
 in s.

 

Example 1:

Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
Example 2:

Input: s = "cbbd"
Output: "bb"
 

Constraints:

1 <= s.length <= 1000
s consist of only digits and English letters.

*/

//Solution : 

class Solution {
    
    int low = 0, high = 0;
    public String longestPalindrome(String s) {
        
        if(s == null || s.length() < 2)
            return s;
        
        for(int i=0; i<s.length(); i++){
            //odd
            solve(s,i,i);
            //Even
            solve(s,i,i+1);
        }
        
        return s.substring(low,low+high);
    }
    
    public void solve(String s, int start, int end){
        
        while(start >=0 && end <= s.length()-1 &&s.charAt(start) == s.charAt(end)){
            start--; end++;
        }
        
        if(high < end-start-1){
            high = end - start - 1;
            low = start+1;
        }
        
    }
}
