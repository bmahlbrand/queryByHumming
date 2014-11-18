import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Query {

	public static void queryDatabase(String query) throws Exception {
		BufferedReader reader = null;
		ArrayList<ResultNode> results = null;
		StringTokenizer toke = null;
		EditDistance ed = null;
		
		try {
			reader = new BufferedReader(new FileReader("database.txt"));
			results = new ArrayList<ResultNode>();
			ed = new EditDistance();
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				toke = new StringTokenizer(line, " ,");
				 toke.nextToken();
				ResultNode rn = new ResultNode(ed.levenshtein(query, toke.nextToken()), toke.nextToken());
				// while (toke.hasMoreElements()) {
				results.add(rn);
				System.out.printf("%d %s\n", results.get(i).getDistance(), results.get(i).getFileName());
				// }
				i++;
			}	
			
		} catch (IOException ex) {
            // report
        } finally {
            try {reader.close();} catch (Exception ex) {}
        }
	}
}