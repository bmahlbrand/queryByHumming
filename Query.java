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
			System.out.printf("\nResults:\n");
			while ((line = reader.readLine()) != null) {
				toke = new StringTokenizer(line, " ,");
				toke.nextToken(); //get rid of index int
				ResultNode rn = new ResultNode(ed.wuManber(query, toke.nextToken(), 100), toke.nextToken());
		
				results.add(rn);
				System.out.printf("%d %s\n", results.get(i).getDistance(), results.get(i).getFileName());
				i++;
			}	
			
		} catch (IOException ex) {
            // report
        } finally {
            try {reader.close();} catch (Exception ex) {}
        }
	}
}