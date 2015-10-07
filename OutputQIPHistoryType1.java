import javax.swing.*;
import java.io.*;

public class OutputQIPHistoryType1 extends OutputQIPHistory {
    public OutputQIPHistoryType1(String inp) {
        FileInputStream streamIN = null;
        Reader r = null;
        try {
            streamIN = new FileInputStream(inp);
            r = new InputStreamReader(streamIN, "Unicode");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedEncodingException y) {
            JOptionPane.showMessageDialog(null, "UnsupportedEncodingException", "Error", JOptionPane.ERROR_MESSAGE);
        }
        BufferedReader br = new BufferedReader(r);
        StringBuffer output = new StringBuffer();
        String res= "";
        String sen = "You: ";
        String date = "";
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("Date and Time: ")) {
                    date = line.substring(line.indexOf(":") + 2, line.length());
                } else if (line.contains(" message ")) {
                    res = line.substring(line.lastIndexOf("  ") + 2, line.length());
                    output.append((char) 13).append((char) 10);
                    for (int t = 1; t < 39; t++) {
                        output.append("-");
                    }
                    if (line.contains(" to ")) {
                        output.append(">-").append((char) 13).append((char) 10);
                        output.append(sen).append("(").append(date).append(")").append((char) 13).append((char) 10);
                    } else {
                        output.append("<-").append((char) 13).append((char) 10);
                        output.append(res).append(": ").append("(").append(date).append(")").append((char) 13).append((char) 10);
                    }
                } else {
                    output.append(line).append((char) 13).append((char) 10);
                }
            }
            setFilenameOut(res);
            stringOut = output.toString();
            streamIN.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Encode Error. Check if the file is ICQ Lite History.", "IOException", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, line, "NullPointerException", JOptionPane.ERROR_MESSAGE);
        }
    }
}
