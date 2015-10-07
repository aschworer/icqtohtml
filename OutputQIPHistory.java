import javax.swing.*;
import java.io.*;

abstract public class OutputQIPHistory {
    protected String stringOut = "none";
    protected String filenameOut;

    public void setFilenameOut(String filenameOut) {
        this.filenameOut = filenameOut;
    }

    public void write(String directoryOUT)
            throws java.io.IOException {

        String out = directoryOUT + "\\" + filenameOut + ".txt";
        FileOutputStream streamOUT = null;

        try {
            File f = new File(out);
            int i=1;
            while(f.exists()){
               out = directoryOUT + "\\" + filenameOut + Integer.toString(i) +".txt";
                i++;
                f = new File(out);
            }
            streamOUT = new FileOutputStream(out, true);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Writer wr = new OutputStreamWriter(streamOUT, "Cp1251");
        wr.write(stringOut);
        wr.close();
    }
}
