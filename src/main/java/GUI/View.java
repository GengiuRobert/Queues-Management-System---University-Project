package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class View extends JFrame {

    public JPanel panel;
    public JLabel clientsLabel;
    public JLabel queuesLabel;
    public JLabel simulationTimeLabel;
    public JLabel arrivalTimeLabel;
    public JLabel serviceTimeLabel;
    public JTextField clientsNumberTextField;
    public JTextField queuesNumberTextField;
    public JTextField simulationTimeTextField;
    public JTextField arrivalTimeTextField1;
    public JTextField arrivalTimeTextField2;
    public JTextField serviceTimeTextField1;
    public JTextField serviceTimeTextField2;
    public JButton startButton;
    public JTextField strategyTextField;
    public JLabel strategyLabel;

    public View() {
        setPreferredSize(new Dimension(450, 300));


        panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        clientsLabel = new JLabel("Number of clients:");
        clientsLabel.setBounds(5, 0, 400, 30);
        clientsLabel.setFont(new Font("Arial", Font.BOLD, 18));

        queuesLabel = new JLabel("Number of queues:");
        queuesLabel.setBounds(5, 35, 400, 30);
        queuesLabel.setFont(new Font("Arial", Font.BOLD, 18));

        simulationTimeLabel = new JLabel("Time limit of simulation:");
        simulationTimeLabel.setBounds(5, 70, 400, 30);
        simulationTimeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        arrivalTimeLabel = new JLabel("Arrival time bounds:");
        arrivalTimeLabel.setBounds(5, 105, 400, 30);
        arrivalTimeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        serviceTimeLabel = new JLabel("Service time bounds:");
        serviceTimeLabel.setBounds(5, 140, 400, 30);
        serviceTimeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        clientsNumberTextField = new JTextField();
        clientsNumberTextField.setBounds(230, 0, 50, 30);
        clientsNumberTextField.setFont(new Font("Arial", Font.BOLD, 18));
        clientsNumberTextField.setBackground(new Color(144, 238, 144));

        queuesNumberTextField = new JTextField();
        queuesNumberTextField.setBounds(230, 35, 50, 30);
        queuesNumberTextField.setFont(new Font("Arial", Font.BOLD, 18));
        queuesNumberTextField.setBackground(new Color(144, 238, 144));

        simulationTimeTextField = new JTextField();
        simulationTimeTextField.setBounds(230, 70, 50, 30);
        simulationTimeTextField.setFont(new Font("Arial", Font.BOLD, 18));
        simulationTimeTextField.setBackground(new Color(144, 238, 144));

        arrivalTimeTextField1 = new JTextField();
        arrivalTimeTextField1.setBounds(230, 105, 50, 30);
        arrivalTimeTextField1.setFont(new Font("Arial", Font.BOLD, 18));
        arrivalTimeTextField1.setBackground(new Color(144, 238, 144));

        arrivalTimeTextField2 = new JTextField();
        arrivalTimeTextField2.setBounds(285, 105, 50, 30);
        arrivalTimeTextField2.setFont(new Font("Arial", Font.BOLD, 18));
        arrivalTimeTextField2.setBackground(new Color(144, 238, 144));

        serviceTimeTextField1 = new JTextField();
        serviceTimeTextField1.setBounds(230, 140, 50, 30);
        serviceTimeTextField1.setFont(new Font("Arial", Font.BOLD, 18));
        serviceTimeTextField1.setBackground(new Color(144, 238, 144));

        serviceTimeTextField2 = new JTextField();
        serviceTimeTextField2.setBounds(285, 140, 50, 30);
        serviceTimeTextField2.setFont(new Font("Arial", Font.BOLD, 18));
        serviceTimeTextField2.setBackground(new Color(144, 238, 144));


        startButton = new JButton("Start Execution");
        startButton.setBounds(65, 210, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(144, 238, 144));

        strategyTextField = new JTextField();
        strategyTextField.setBounds(385, 175, 50, 30);
        strategyTextField.setFont(new Font("Arial", Font.BOLD, 18));
        strategyTextField.setBackground(new Color(144, 238, 144));

        strategyLabel = new JLabel("1 -> Shortest_Queue // 0 -> Shortest_Time:");
        strategyLabel.setBounds(5, 175, 400, 30);
        strategyLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(clientsLabel);
        panel.add(queuesLabel);
        panel.add(simulationTimeLabel);
        panel.add(arrivalTimeLabel);
        panel.add(serviceTimeLabel);
        panel.add(clientsNumberTextField);
        panel.add(queuesNumberTextField);
        panel.add(simulationTimeTextField);
        panel.add(arrivalTimeTextField1);
        panel.add(arrivalTimeTextField2);
        panel.add(serviceTimeTextField1);
        panel.add(serviceTimeTextField2);
        panel.add(startButton);
        panel.add(strategyLabel);
        panel.add(strategyTextField);
        panel.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(panel);


        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public int getClientsNumber() {
        return Integer.parseInt(clientsNumberTextField.getText());
    }

    public int getQueuesNumber() {
        return Integer.parseInt(queuesNumberTextField.getText());
    }

    public int getSimulationTime() {
        return Integer.parseInt(simulationTimeTextField.getText());
    }

    public int getArrivalTime1() {
        return Integer.parseInt(arrivalTimeTextField1.getText());
    }

    public int getArrivalTime2() {
        return Integer.parseInt(arrivalTimeTextField2.getText());
    }

    public int getServiceTime1() {
        return Integer.parseInt(serviceTimeTextField1.getText());
    }

    public int getServiceTime2() {
        return Integer.parseInt(serviceTimeTextField2.getText());
    }

    public int getStrategy() {
        return Integer.parseInt(strategyTextField.getText());
    }
}
