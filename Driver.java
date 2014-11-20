import java.io.*;
import java.util.*;

class Driver {
	public void keyPressed(KeyEvent e) {
	    if(e.getKeyCode() == KeyEvent.VK_SPACE) {
	        System.out.println("F5 pressed");
	    }
	}
	
	public static void main(String[] args) throws Exception {
		
		Parser parser = new Parser();
		Query query = new Query();

		ProcessBuilder builder = new ProcessBuilder("/bin/bash");
		String line;
		Scanner scan = new Scanner(System.in);
		Process p = builder.start();

		OutputStream stdin = p.getOutputStream();
		InputStream stdout = p.getInputStream();

		BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		
		builder.redirectErrorStream(true);

		while (true) {
			System.out.printf("\n 1 - Record new query \n 2 - Run prerecorded test query");
			System.out.printf("\n 3 - Build new database file");
			System.out.printf("\n 4 - Exit \n Enter the option: ");			
			
			int option = scan.nextInt();

			switch(option) {
				case 1: 
					try {
						AudioRecorder ar = new AudioRecorder();
						ar.start();
						// Thread.sleep(50);
						ar.finish();
						continue;
					} catch(Exception e) {

					}
					break;
			
				case 2:
					query.queryDatabase(parser.parsonCode("music/beatles-yellow_submarine.mid"));

					break;
			
				case 3:
					
        			final File folder = new File("music");
        			parser.buildDatabase(folder);

        			
					break;
			
				case 4:
			
					return;
					
				default:
					return;
					
			}
		}
    }
}