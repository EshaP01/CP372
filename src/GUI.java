import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

    private final ClientHandler clientHandler;

    public GUI() {
        setTitle("App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        initComponents();
        setVisible(true);
        clientHandler = new ClientHandler();
    }

    private void btnConnectActionListener(ActionEvent e) {
        try {
            if (clientHandler.isConnected()) {
                System.out.println("Disconnecting...");
                clientHandler.disconnect();
            } else {
                System.out.println("Connecting...");
                clientHandler.connect(txtIPAddress.getText(), Integer.parseInt(txtPort.getText()));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void btnSendActionListener(ActionEvent e) {
        try {
            if (clientHandler.isConnected()) {
                System.out.println("Sending");
                txtOutput.setText(clientHandler.sendMessage(txtInput.getText()));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initComponents() {
        panelParent = new JPanel();
        panelParent.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelParent);
        panelParent.setLayout(new GridLayout(0, 1, 0, 0));

        panelInputs = new JPanel();
        panelParent.add(panelInputs);
        panelInputs.setLayout(new GridLayout(0, 2, 0, 0));

        panelInput = new JPanel();
        panelInputs.add(panelInput);
        panelInput.setLayout(new BorderLayout(0, 0));
        panelInput.setBorder(new EmptyBorder(5, 5, 5, 5));

        lblInput = new JLabel("Input:");
        panelInput.add(lblInput, BorderLayout.NORTH);

        scrollPaneInput = new JScrollPane();
        panelInput.add(scrollPaneInput, BorderLayout.CENTER);

        txtInput = new JTextArea();
        txtInput.setLineWrap(true);
        scrollPaneInput.setViewportView(txtInput);

        panelConnection = new JPanel();
        panelInputs.add(panelConnection);
        panelConnection.setLayout(new GridLayout(2, 1, 0, 0));
        panelConnection.setBorder(new EmptyBorder(5, 5, 5, 5));

        panelSettings = new JPanel();
        panelConnection.add(panelSettings);
        panelSettings.setLayout(new GridLayout(0, 2, 0, 0));

        lblIPAddress = new JLabel("IP Address:");
        panelSettings.add(lblIPAddress);

        lblPort = new JLabel("Port #:");
        panelSettings.add(lblPort);

        txtIPAddress = new JTextField();
        panelSettings.add(txtIPAddress);
        txtIPAddress.setColumns(10);
        txtIPAddress.setText("127.0.0.1"); //TODO: Remove default

        txtPort = new JTextField();
        panelSettings.add(txtPort);
        txtPort.setColumns(10);
        txtPort.setText("3000"); //TODO: Remove default

        panelButton = new JPanel();
        panelConnection.add(panelButton);

        btnConnect = new JButton("Connect/Disconnect");
        btnConnect.addActionListener(this::btnConnectActionListener);
        panelButton.add(btnConnect);

        panelOutputs = new JPanel();
        panelParent.add(panelOutputs);
        panelOutputs.setLayout(new BorderLayout(0, 0));

        lblOutput = new JLabel("Output:");
        panelOutputs.add(lblOutput, BorderLayout.NORTH);

        scrollPaneOutput = new JScrollPane();
        panelOutputs.add(scrollPaneOutput, BorderLayout.CENTER);

        txtOutput = new JTextArea();
        txtOutput.setLineWrap(true);
        txtOutput.setEditable(false);
        scrollPaneOutput.setViewportView(txtOutput);

        btnSend = new JButton("Send");
        btnSend.addActionListener(this::btnSendActionListener);
        panelInput.add(btnSend, BorderLayout.SOUTH);
    }

    JPanel panelParent;
    JTextField txtIPAddress;
    JTextField txtPort;
    JPanel panelInputs;
    JPanel panelOutputs;
    JPanel panelInput;
    JLabel lblInput;
    JScrollPane scrollPaneInput;
    JTextArea txtInput;
    JPanel panelConnection;
    JPanel panelSettings;
    JLabel lblIPAddress;
    JLabel lblPort;
    JPanel panelButton;
    JButton btnConnect;
    JButton btnSend;
    JLabel lblOutput;
    JTextArea txtOutput;
    JScrollPane scrollPaneOutput;

}