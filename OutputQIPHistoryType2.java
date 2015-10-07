import javax.swing.*;
import java.io.*;

public class OutputQIPHistoryType2 extends OutputQIPHistory {
    public OutputQIPHistoryType2(String inp) {
        FileInputStream streamIN = null;
        Reader r = null;
        try {
            streamIN = new FileInputStream(inp);
            r = new InputStreamReader(streamIN, "Cp1251");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedEncodingException y) {
            JOptionPane.showMessageDialog(null, "UnsupportedEncodingException", "Error", JOptionPane.ERROR_MESSAGE);
        }
        BufferedReader br = new BufferedReader(r);
        StringBuffer output = new StringBuffer();
        String res = " ";
        String sen = " ";
        String date;
        String time;
        String line = "";

        boolean oneRoleChoosed = false;
        boolean bothRolesChoosed = false;
        Object[] options = {"Receiver", "Sender"};
        try {
            br.readLine();
            date = br.readLine();
            date = date.substring(0, date.length() - 1);
            br.readLine();
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.contains(") :")) {
                    time = line.substring(line.indexOf("("), line.indexOf(")") + 1);
                    String role = line.substring(0, line.indexOf("("));
                    for (int t = 1; t < 39; t++) {
                        output.append("-");
                    }
                    if (!oneRoleChoosed) {
                        int choice = JOptionPane.showOptionDialog(null, "file: " + inp.substring(inp.lastIndexOf("\\") + 1, inp.length()) + "\n" + "Choose role of: \n" + role, "Choose Role",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (choice == 0) {
                            res = role;
                        } else {
                            sen = role;
                        }
                        oneRoleChoosed = true;
                    }
                    if (role.equals(res)) {
                        output.append("<-").append((char) 13).append((char) 10);
                        output.append(res).append(" || ").append(date).append(time).append((char) 13).append((char) 10);
                    } else if (role.equals(sen)) {
                        output.append(">-").append((char) 13).append((char) 10);
                        output.append(sen).append(" || ").append(date).append(time).append((char) 13).append((char) 10);
                    } else if (bothRolesChoosed) {
                        output.append("*-").append((char) 13).append((char) 10);
                        output.append(role).append(" || ").append(date).append(time).append((char) 13).append((char) 10);
                    } else if (oneRoleChoosed) {
                        if (" ".equals(sen)) {
                            sen = role;
                            output.append(">-").append((char) 13).append((char) 10);
                            output.append(sen).append(" || ").append(date).append(time).append((char) 13).append((char) 10);
                        } else {
                            res = role;
                            output.append("<-").append((char) 13).append((char) 10);
                            output.append(res).append(" || ").append(date).append(time).append((char) 13).append((char) 10);
                        }
                        bothRolesChoosed = true;
                    }
                } else {
                    output.append(line).append((char) 13).append((char) 10);
                }
            }
            stringOut = output.toString();
            streamIN.close();
            setFilenameOut(res);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, line, "IOException!!!", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, line, "NullPointerException", JOptionPane.ERROR_MESSAGE);
        }
    }

}
