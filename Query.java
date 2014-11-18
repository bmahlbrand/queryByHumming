import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Query {
	public static void main(String[] args) throws Exception {
		queryDatabase("RRR");
	}

	public static void queryDatabase(String query) throws Exception {
		BufferedReader reader = null;
		ArrayList<ResultNode> results = null;
		StringTokenizer toke = null;
		
		try {
			reader = new BufferedReader(new FileReader("database.txt"));
			results = new ArrayList<ResultNode>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				toke = new StringTokenizer(line, " ,");
				
				while (toke.hasMoreElements()) {
					System.out.printf("%s\n", toke.nextToken());
				}
			}	
			
		} catch (IOException ex) {
            // report
        } finally {
            try {reader.close();} catch (Exception ex) {}
        }
	}
}