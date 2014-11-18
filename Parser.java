import java.io.*;
import javax.sound.midi.*;

public class Parser {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;

    public static void main(String[] args) throws Exception {
        final File folder = new File("music");
        buildDatabase(folder);
    }
    
    //Takes all the files in the folder called "music", parse into parsons code,
    //and create a database file.
    public static void buildDatabase(final File folder) throws Exception {
        Writer writer = null;
        int i=1;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("database.txt"), "utf-8"));
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    buildDatabase(fileEntry);
                } else {
                    writer.write(i+", "+parsonCode("music/"+fileEntry.getName())+", "+ fileEntry.getName()+"\n");
                    i++;
                }
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }
    
    public static String parsonCode(String filename) throws Exception {
        File music_file = new File(filename);
        int previous_key=0;
        int trackNumber = 0;
        int firstnote =0;
        StringBuilder ret = new StringBuilder();
        Sequence sequence = MidiSystem.getSequence(music_file);
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                         if(firstnote==0){
                             ret.append("*");
                             firstnote=1;
                             int key = sm.getData1();
                             previous_key=key;
                         }else{
                             int key = sm.getData1();
                             if(key==previous_key)
                                 ret.append("R");
                             else if(key>previous_key)
                                 ret.append("U");
                             else
                                 ret.append("D");
                             previous_key=key;
                         }
                    } 
                }
            }
        }
        return ret.toString();
    }
}