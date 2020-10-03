import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUI extends JFrame {
    private final ClientHandler clientHandler;

    public GUI() {
        setTitle("CP372 Assignment 1 App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        clientHandler = new ClientHandler();
        initComponents();
    }

    private void btnDisconnectHandler(ActionEvent e) {
        try {
            btnDisconnect.setEnabled(false);
            clientHandler.disconnect();
            connectDialog();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void comboBoxRequestsHandler(ActionEvent e) {
        if (comboBoxRequests.getSelectedItem() == Request.GET) {
            checkboxAll.setEnabled(true);
            checkboxBibtex.setEnabled(true);
        } else {
            checkboxAll.setEnabled(false);
            checkboxAll.setSelected(false);
            checkboxBibtex.setEnabled(false);
            checkboxBibtex.setSelected(false);
            checkboxAllHandler(e);
        }
    }

    private void checkboxAllHandler(ActionEvent e) {
        txtISBN.setEnabled(!checkboxAll.isSelected());
        txtTITLE.setEnabled(!checkboxAll.isSelected());
        txtAUTHOR.setEnabled(!checkboxAll.isSelected());
        txtPUBLISHER.setEnabled(!checkboxAll.isSelected());
        txtYEAR.setEnabled(!checkboxAll.isSelected());
    }

    private void btnClearFieldsHandler(ActionEvent e) {
        txtISBN.setText("");
        txtTITLE.setText("");
        txtAUTHOR.setText("");
        txtPUBLISHER.setText("");
        txtYEAR.setText("");
    }

    private void btnClearOutputHandler(ActionEvent e) {
        txtOutput.setText("");
    }

    private void btnSubmitHandler(ActionEvent e) {
        if (clientHandler.isConnected()) {
            try {
                String ISBN;
                // Handle Title
                String TITLE = txtTITLE.getText().trim();
                // Handle Author
                String AUTHOR = txtAUTHOR.getText().trim();
                // Handle Publisher
                String PUBLISHER = txtPUBLISHER.getText().trim();
                // Handle Year
                int YEAR = 0;
                if (txtYEAR.getText().length() > 0)
                    try {
                        YEAR = Integer.parseInt(txtYEAR.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(this, "Invalid Year", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                // Handle All Get request
                if (comboBoxRequests.getSelectedItem() == Request.GET && checkboxAll.isSelected()) {
                    txtOutput.setText(clientHandler.sendMessage(Request.GET, "", "", "", "", 0, true));
                    return;
                }

                ISBN = txtISBN.getText().replace("-", "").trim();

                if (comboBoxRequests.getSelectedItem() == Request.SUBMIT || comboBoxRequests.getSelectedItem() == Request.UPDATE)
                    if (ISBN.length() == 0) {
                        JOptionPane.showMessageDialog(this, "Please enter an ISBN", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                // Check if GET request and empty fields
                if (comboBoxRequests.getSelectedItem() == Request.GET)
                    if (ISBN.length() == 0 && TITLE.length() == 0 && AUTHOR.length() == 0 && PUBLISHER.length() == 0 && YEAR == 0) {
                        JOptionPane.showMessageDialog(this, "Please enter an field to search or select All", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                // Check if ISBN is correct length at least
                if (ISBN.length() == 13) {
                    int calculatedDigit = Util.calculateISBNDigit(ISBN);
                    if (Integer.parseInt(ISBN.toCharArray()[12] + "") == calculatedDigit) {
                        txtOutput.setText(clientHandler.sendMessage((Request) comboBoxRequests.getSelectedItem(), ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, false));
                        return;
                    }
                }
                // If ISBN was left blank, let user continue
                if (ISBN.length() == 0)
                    txtOutput.setText(clientHandler.sendMessage((Request) comboBoxRequests.getSelectedItem(), ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, false));
                else
                    JOptionPane.showMessageDialog(this, "Invalid ISBN", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Invalid ISBN", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            connectDialog();
        }
    }

    private void connectDialog() {
        txtIPAddress = new JTextField("127.0.0.1"); //TODO Remove default
        txtPort = new JTextField("3000"); //TODO Remove default
        Object[] fields = {"IP Address", txtIPAddress, "Port Number", txtPort};
        Object[] options = {"Connect", "Exit"};
        int option = JOptionPane.showOptionDialog(this, fields, "Connect to Server", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (option) {
            case JOptionPane.YES_OPTION:
                try {
                    clientHandler.connect(txtIPAddress.getText(), Integer.parseInt(txtPort.getText()));
                    btnDisconnect.setEnabled(true);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(this, "Invalid Port Number", "Error", JOptionPane.ERROR_MESSAGE);
                    connectDialog();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, "Unable to connect please check IP/Port", "Error", JOptionPane.ERROR_MESSAGE);
                    connectDialog();
                }
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;
        }
    }

    private void initComponents() {
        panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelParent);
        panelParent.setLayout(new BorderLayout(0, 0));

        panelHeader = new JPanel();
        panelHeader.setBorder(new EmptyBorder(5, 10, 20, 10));
        panelParent.add(panelHeader, BorderLayout.NORTH);
        panelHeader.setLayout(new BorderLayout(0, 0));

        lblNames = new JLabel("<html>Mandeep Sran<br />Matthew Paek</html>");
        panelHeader.add(lblNames, BorderLayout.WEST);

        lblTitle = new JLabel("CP372: Assignment 1");
        lblTitle.setFont(new Font("Serif", Font.PLAIN, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panelHeader.add(lblTitle, BorderLayout.CENTER);

        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.addActionListener(this::btnDisconnectHandler);
        btnDisconnect.setEnabled(false);
        panelHeader.add(btnDisconnect, BorderLayout.EAST);

        panelContent = new JPanel();
        panelParent.add(panelContent, BorderLayout.CENTER);
        panelContent.setLayout(new GridLayout(1, 0, 0, 0));

        panelLeft = new JPanel();
        panelLeft.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContent.add(panelLeft);
        panelLeft.setLayout(new BorderLayout(0, 0));

        panelRequest = new JPanel();
        panelRequest.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelLeft.add(panelRequest, BorderLayout.NORTH);
        panelRequest.setLayout(new GridLayout(1, 0, 0, 0));

        lblRequest = new JLabel("Request:");
        panelRequest.add(lblRequest);

        comboBoxRequests = new JComboBox<>(Request.values());
        comboBoxRequests.addActionListener(this::comboBoxRequestsHandler);
        panelRequest.add(comboBoxRequests);

        panelFields = new JPanel();
        panelFields.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelLeft.add(panelFields, BorderLayout.CENTER);
        panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));

        panelISBN = new JPanel();
        panelFields.add(panelISBN);
        panelISBN.setLayout(new GridLayout(0, 2, 0, 0));

        lblISBN = new JLabel("ISBN:");
        panelISBN.add(lblISBN);

        txtISBN = new JTextField();
        panelISBN.add(txtISBN);
        txtISBN.setColumns(10);

        panelTITLE = new JPanel();
        panelFields.add(panelTITLE);
        panelTITLE.setLayout(new GridLayout(0, 2, 0, 0));

        lblTITLE = new JLabel("TITLE:");
        panelTITLE.add(lblTITLE);

        txtTITLE = new JTextField();
        panelTITLE.add(txtTITLE);
        txtTITLE.setColumns(10);

        panelAUTHOR = new JPanel();
        panelFields.add(panelAUTHOR);
        panelAUTHOR.setLayout(new GridLayout(0, 2, 0, 0));

        lblAUTHOR = new JLabel("AUTHOR:");
        panelAUTHOR.add(lblAUTHOR);

        txtAUTHOR = new JTextField();
        panelAUTHOR.add(txtAUTHOR);
        txtAUTHOR.setColumns(10);

        panelPUBLISHER = new JPanel();
        panelFields.add(panelPUBLISHER);
        panelPUBLISHER.setLayout(new GridLayout(0, 2, 0, 0));

        lblPUBLISHER = new JLabel("PUBLISHER:");
        panelPUBLISHER.add(lblPUBLISHER);

        txtPUBLISHER = new JTextField();
        panelPUBLISHER.add(txtPUBLISHER);
        txtPUBLISHER.setColumns(10);

        panelYEAR = new JPanel();
        panelFields.add(panelYEAR);
        panelYEAR.setLayout(new GridLayout(0, 2, 0, 0));

        lblYEAR = new JLabel("YEAR:");
        panelYEAR.add(lblYEAR);

        txtYEAR = new JTextField();
        panelYEAR.add(txtYEAR);
        txtYEAR.setColumns(10);

        panelExtra = new JPanel();
        FlowLayout fl_panelExtra = (FlowLayout) panelExtra.getLayout();
        fl_panelExtra.setAlignment(FlowLayout.RIGHT);
        panelFields.add(panelExtra);

        checkboxAll = new JCheckBox("All");
        checkboxAll.setEnabled(false);
        checkboxAll.addActionListener(this::checkboxAllHandler);
        panelExtra.add(checkboxAll);

        checkboxBibtex = new JCheckBox("Bibtex");
        checkboxBibtex.setEnabled(false);
        panelExtra.add(checkboxBibtex);

        panelButtons = new JPanel();
        panelLeft.add(panelButtons, BorderLayout.SOUTH);
        panelButtons.setLayout(new GridLayout(1, 0, 0, 0));

        panelClear = new JPanel();
        panelButtons.add(panelClear);
        panelClear.setLayout(new GridLayout(1, 0, 0, 0));

        btnClearFields = new JButton("Clear Fields");
        btnClearFields.addActionListener(this::btnClearFieldsHandler);
        panelClear.add(btnClearFields);

        btnClearOutput = new JButton("Clear Output");
        btnClearOutput.addActionListener(this::btnClearOutputHandler);
        panelClear.add(btnClearOutput);

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this::btnSubmitHandler);
        panelButtons.add(btnSubmit);

        panelRight = new JScrollPane();
        panelRight.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelContent.add(panelRight);

        lblOutput = new JLabel("Output");
        lblOutput.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelRight.setColumnHeaderView(lblOutput);

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setLineWrap(true);
        txtOutput.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panelRight.setViewportView(txtOutput);

        setVisible(true);

        connectDialog();
    }

    JPanel panelParent;
    JPanel panelHeader;
    JLabel lblNames;
    JLabel lblTitle;
    JButton btnDisconnect;
    JPanel panelContent;
    JPanel panelLeft;
    JPanel panelRequest;
    JLabel lblRequest;
    JComboBox<Request> comboBoxRequests;
    JPanel panelFields;
    JPanel panelISBN;
    JLabel lblISBN;
    JTextField txtISBN;
    JPanel panelTITLE;
    JLabel lblTITLE;
    JTextField txtTITLE;
    JPanel panelAUTHOR;
    JLabel lblAUTHOR;
    JTextField txtAUTHOR;
    JPanel panelPUBLISHER;
    JLabel lblPUBLISHER;
    JTextField txtPUBLISHER;
    JPanel panelYEAR;
    JLabel lblYEAR;
    JTextField txtYEAR;
    JPanel panelExtra;
    JCheckBox checkboxAll;
    JCheckBox checkboxBibtex;
    JPanel panelButtons;
    JPanel panelClear;
    JButton btnClearFields;
    JButton btnClearOutput;
    JButton btnSubmit;
    JScrollPane panelRight;
    JLabel lblOutput;
    JTextArea txtOutput;

    JTextField txtIPAddress;
    JTextField txtPort;

}
