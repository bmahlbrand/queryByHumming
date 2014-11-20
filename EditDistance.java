public class EditDistance {

	public static int levenshtein(String str1, String str2) {
		int len1 = str1.length() + 1;                                                     
		int len2 = str2.length() + 1;                                                     
	 
		int[] cost = new int[len1];                                                     
		int[] newcost = new int[len1];                                                  
	 
		for (int i = 0; i < len1; i++) 
			cost[i] = i;                                     
								  
		for (int j = 1; j < len2; j++) {                                                
			newcost[0] = j;                                                             
	 
			for(int i = 1; i < len1; i++) {                                             
				int match = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;             
	 
				int cost_replace = cost[i - 1] + match;                                 
				int cost_insert  = cost[i] + 1;                                         
				int cost_delete  = newcost[i - 1] + 1;                                  
	 
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
			}                                                                           
	 
			int[] swap = cost; cost = newcost; newcost = swap;                          
		}                                                                               
	 
		return cost[len1 - 1];     
	}
	
	public static int damerauLevenshtein(String str1, String str2) {
		int [][] d = new int[str1.length()][str2.length()];
		int cost;
	 
		for (int i = 0; i < str1.length(); i++)
			d[i][0] = i;
		for (int j = 0; j < str2.length(); j++)
			d[0][j] = j;

		for (int i = 1; i < str1.length(); i++) {
			for (int j = 1; j < str2.length(); j++) {
				if (str1.charAt(i) == str2.charAt(j)) 
					cost = 0;
				else 
					cost = 1;
				
				// if (i > 1 && j > 1)
				d[i][j] = Math.min(Math.min(
							d[i-1][j] + 1,     // deletion
							d[i][j-1] + 1),     // insertion
							d[i-1][j-1] + cost   // substitution
							);

				if(i > 1 && j > 1 && str1.charAt(i) == str2.charAt(j-1) && str1.charAt(i-1) == str2.charAt(j)) {
					d[i][j] = Math.min(d[i][j],
									 d[i-2][j-2] + cost   // transposition
									 );
				}
			}                
		}
	 
		return d[str1.length()][str2.length()];
	}

	public static int lcsLength(String a, String b) {
		int[][] lengths = new int[a.length()+1][b.length()+1];
	 	 
		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++)
				if (a.charAt(i) == b.charAt(j))
					lengths[i+1][j+1] = lengths[i][j] + 1;
				else
					lengths[i+1][j+1] =
						Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
		// read the substring out from the matrix
		StringBuffer sb = new StringBuffer();
		for (int x = a.length(), y = b.length();
			 x != 0 && y != 0; ) {
			if (lengths[x][y] == lengths[x-1][y])
				x--;
			else if (lengths[x][y] == lengths[x][y-1])
				y--;
			else {
				assert a.charAt(x-1) == b.charAt(y-1);
				sb.append(a.charAt(x-1));
				x--;
				y--;
			}
		}
 
		return sb.reverse().toString().length();
	}

	public static String lcsString(String a, String b) {
		int[][] lengths = new int[a.length()+1][b.length()+1];
	 
		// row 0 and column 0 are initialized to 0 already
	 
		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++)
				if (a.charAt(i) == b.charAt(j))
					lengths[i+1][j+1] = lengths[i][j] + 1;
				else
					lengths[i+1][j+1] =
						Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
		// read the substring out from the matrix
		StringBuffer sb = new StringBuffer();
		for (int x = a.length(), y = b.length();
			 x != 0 && y != 0; ) {
			if (lengths[x][y] == lengths[x-1][y])
				x--;
			else if (lengths[x][y] == lengths[x][y-1])
				y--;
			else {
				assert a.charAt(x-1) == b.charAt(y-1);
				sb.append(a.charAt(x-1));
				x--;
				y--;
			}
		}
 
		return sb.reverse().toString();
	}

	public int wuManber(String query, String song, int k) {
		int matchBit = 1;

		for (int i = 1; i < query.length(); i++) {
			matchBit <<= 1;
		}

		char presentChars[] = new char[query.length()];
		int oldState[] = new int[k];
		oldState[0] = 0;
		int state[] = new int[k];
		for (int i = 1; i < k; i++) {
			oldState[i] = (oldState[i - 1] << 1) | 1; 
		}

		for (int i = 0; i < song.length(); i++) {
			state[0] = ((oldState[0] << 1) | 1) & presentChars[i];

			for (int j = 1; j < k; j++) {
				state[j] = ((oldState[j-1])) & presentChars[i] | 
				((oldState[j - 1] << 1) | 1 ) | 
				((state[j - 1] << 1) | 1) | 
				oldState[j - 1];
			}

			for (int j = 0; j < k; j++) {
				oldState[j] = state[j];
			}

			if ((matchBit & state[k]) != 0) {
				System.out.printf("a match found ending at %d", i);
				return i;
			}
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		Parser parser = new Parser();

		System.out.printf("edit distance is %d \n", levenshtein(parser.parsonCode("music/14-1__Yellow_Submarine.mid"), parser.parsonCode("music/beatles-yellow_submarine.mid")));
		System.out.printf("lcs is %d \n", lcsLength(parser.parsonCode("music/14-1__Yellow_Submarine.mid"), parser.parsonCode("music/beatles-yellow_submarine.mid")));
		System.out.printf("lcs is %d \n", lcsLength("RRR", parser.parsonCode("music/beatles-yellow_submarine.mid")));
	}
}