import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ParserFrame extends JFrame {
    private static Dimension FRAME_DIMENSION = new Dimension(500, 280);
    private JButton btnOpen;
    private JButton btnSave;
    private JButton btnParse;
    private JTextField sourseTextField;
    private JTextField outputTextField;
    private File [] sourseFiles;
    private JRadioButton type1;
    private JRadioButton type2;

    public ParserFrame() {
        super("ICQ to QIP History Converter");
        this.setSize(FRAME_DIMENSION);
        initUI();
        initListeners();
    }

    private void initUI() {
        Container contentPane = getContentPane();
        contentPane.setLayout((new GridLayout(2, 2, 40, 10)));
        sourseTextField = new JTextField(20);
        outputTextField = new JTextField(20);
        sourseTextField.setEditable(false);
        outputTextField.setEditable(false);
        btnOpen = new JButton("Browse");
        btnSave = new JButton("Browse");
        btnParse = new JButton("Parse");
        type1 = new JRadioButton("ICQ Lite");
        type2 = new JRadioButton("ICQ Pro");
        ButtonGroup bg = new ButtonGroup();
        bg.add(type1);
        bg.add(type2);

        JPanel pnlNorthLeft = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlSouthLeft = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlNorthRight = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlSouthRight = new JPanel(new GridBagLayout());
        pnlNorthLeft.add(new JLabel("Choose Sourse File:", 0));
        pnlNorthLeft.add(btnOpen);
        pnlNorthLeft.add(sourseTextField);
        pnlSouthLeft.add(new JLabel("Choose Output Directory:", 0));
        pnlSouthLeft.add(btnSave);
        pnlSouthLeft.add(outputTextField);
        pnlSouthRight.add(btnParse);
        pnlNorthRight.add(new JLabel("Choose ICQ History Type:", 0));
        pnlNorthRight.add(type1);
        pnlNorthRight.add(type2);

        contentPane.add(pnlNorthLeft);
        contentPane.add(pnlNorthRight);
        contentPane.add(pnlSouthLeft);
        contentPane.add(pnlSouthRight);
    }

    public Insets getInsets() {
        return new Insets(40, 20, 20, 20);
    }

    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fch = new JFileChooser();
                fch.setMultiSelectionEnabled(true);
                //fch.setCurrentDirectory(new File("Y:\\Не влезай!!!\\Letters\\АСЯ\\ICQ_HISTORY\\2003\\аська (до28.06.2003) - школа\\[ai32]_119087076\\"));
                switch (fch.showDialog(null, "Open")) {
                    case JFileChooser.APPROVE_OPTION:
                        sourseFiles = fch.getSelectedFiles();
                        String strFiles = "";
                        for (File sourseFile : sourseFiles) {
                            strFiles = strFiles + sourseFile.toString() + "; ";
                        }
                        sourseTextField.setText(strFiles);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(null, "Incorrect name of file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fch = new JFileChooser();
                fch.setFileSelectionMode(1);
                //fch.setCurrentDirectory(new File("C:\\Documents and Settings\\Home\\Рабочий стол"));
                switch (fch.showDialog(null, "Open")) {
                    case JFileChooser.APPROVE_OPTION:
                        File file = fch.getSelectedFile();
                        outputTextField.setText(file.toString());
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(null, "Incorrect name of file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
        btnParse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ("".equals(sourseTextField.getText()) || "".equals(outputTextField.getText())) {
                        JOptionPane.showMessageDialog(null, "Set File", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String inp;
                        for (File sourseFile : sourseFiles) {
                            if (!sourseFile.toString().endsWith(".txt")) {
                                JOptionPane.showMessageDialog(null, "Probably " + sourseFile.toString() + " is not QIP History File", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                            inp = sourseFile.toString();
                            String out = outputTextField.getText() + "\\";
                            OutputQIPHistory history = null;
                            if (type1.isSelected()) {
                                history = new OutputQIPHistoryType1(inp);
                                history.write(out);
                            } else if (type2.isSelected()) {
                                history = new OutputQIPHistoryType2(inp);
                                history.write(out);
                            } else {
                                JOptionPane.showMessageDialog(null, "Choose Convert Type", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "Set File", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "IOException", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        );
    }
}
