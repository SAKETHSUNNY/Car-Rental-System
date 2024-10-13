import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Car {
    private String model;
    private double basePricePerDay;
    private boolean rented;
    private String renterName;
    private int rentalDays;
    private double totalCost;

    public Car(String model, double basePricePerDay) {
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.rented = false;
        this.renterName = "";
        this.rentalDays = 0;
        this.totalCost = 0;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return rented;
    }

    public String getRenterName() {
        return renterName;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void rent(String renterName, int rentalDays) {
        this.rented = true;
        this.renterName = renterName;
        this.rentalDays = rentalDays;
        this.totalCost = calculatePrice(rentalDays);
    }

    public void returnCar() {
        this.rented = false;
        this.renterName = "";
        this.rentalDays = 0;
        this.totalCost = 0;
    }

    @Override
    public String toString() {
        return model;
    }
}

public class CarRentalSystemGUI extends JFrame {
    private Map<String, List<Car>> carModels; // Map to store car models by make
    private JTextField nameField;
    private JComboBox<String> carMakeComboBox;
    private JComboBox<Car> carModelComboBox;
    private JTextField rentalDaysField;
    private JLabel costLabel;
    private JButton rentButton;
    private JButton returnButton;
    private JButton exchangeButton;
    private JButton calculateButton;
    private JButton resetButton;
    private JTextArea returnDetailsArea;
    private JCheckBox termsCheckBox;

    private Car currentRentedCar;

    public CarRentalSystemGUI() {
        carModels = new HashMap<>();
        carModels.put("BMW", new ArrayList<>());
        carModels.put("AUDI", new ArrayList<>());
        carModels.put("TATA", new ArrayList<>());

        carModels.get("BMW").add(new Car("BMW Camry", 60.0));
        carModels.get("BMW").add(new Car("BMW X5", 80.0));

        carModels.get("AUDI").add(new Car("AUDI Accord", 70.0));
        carModels.get("AUDI").add(new Car("AUDI A8", 90.0));

        carModels.get("TATA").add(new Car("TATA Thar", 150.0));
        carModels.get("TATA").add(new Car("TATA Nano", 30.0));

        setTitle("Car Rental System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        // Set background color for the panel
        panel.setBackground(new Color(173, 216, 230)); // Light Blue color

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel titleLabel = new JLabel("Car Rental System");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20)); // Poppins font
        panel.add(titleLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Name:"), c);

        c.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Car Make:"), c);

        c.gridx = 1;
        carMakeComboBox = new JComboBox<>(carModels.keySet().toArray(new String[0]));

        carMakeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMake = (String) carMakeComboBox.getSelectedItem();
                updateCarModelComboBox(selectedMake);
            }
        });
        panel.add(carMakeComboBox, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Car Model:"), c);

        c.gridx = 1;
        carModelComboBox = new JComboBox<>();
        panel.add(carModelComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Rental Days:"), c);

        c.gridx = 1;
        rentalDaysField = new JTextField(5);
        panel.add(rentalDaysField, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.WEST;
        rentButton = new JButton("Rent Car");
        panel.add(rentButton, c);

        c.gridx = 1;
        returnButton = new JButton("Return Car");
        panel.add(returnButton, c);

        c.gridx = 0;
        c.gridy = 6;
        exchangeButton = new JButton("Exchange Car");
        panel.add(exchangeButton, c);

        c.gridx = 0;
        c.gridy = 7;
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 2;
        calculateButton = new JButton("Calculate Cost");
        panel.add(calculateButton, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        costLabel = new JLabel("Total Cost: ");
        panel.add(costLabel, c);

        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        returnDetailsArea = new JTextArea(6, 20);
        returnDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(returnDetailsArea);
        panel.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        resetButton = new JButton("Reset");
        panel.add(resetButton, c);

        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 2;
        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        termsCheckBox = new JCheckBox("I agree to the terms and conditions");
        JButton viewTermsButton = new JButton("View Terms and Conditions");
        termsPanel.add(termsCheckBox);
        termsPanel.add(viewTermsButton);
        panel.add(termsPanel, c);

        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        panel.add(new JLabel(" "), c); // Empty space for separation

        ActionListener calculateActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCost();
            }
        };

        calculateButton.addActionListener(calculateActionListener);
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentCar();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar();
            }
        });

        exchangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exchangeCar();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDetails();
            }
        });

        viewTermsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTerms();
            }
        });

        getContentPane().add(panel);

        setVisible(true);
    }

    private void updateCarModelComboBox(String make) {
        carModelComboBox.removeAllItems();
        if (carModels.containsKey(make)) {
            List<Car> models = carModels.get(make);
            for (Car model : models) {
                carModelComboBox.addItem(model);
            }
        }
    }

    private void calculateCost() {
        if (!termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please agree to the terms and conditions.");
            return;
        }

        Car selectedCar = (Car) carModelComboBox.getSelectedItem();
        int rentalDays = Integer.parseInt(rentalDaysField.getText());
        double totalCost = selectedCar.calculatePrice(rentalDays);
        costLabel.setText("Total Cost: $" + totalCost);
    }

    private void rentCar() {
        Car selectedCar = (Car) carModelComboBox.getSelectedItem();
        if (selectedCar.isRented()) {
            JOptionPane.showMessageDialog(this, "Car is already rented by " + selectedCar.getRenterName() +
                    " for " + selectedCar.getRentalDays() + " days.");
        } else {
            String name = nameField.getText();
            int rentalDays = Integer.parseInt(rentalDaysField.getText());
            selectedCar.rent(name, rentalDays);
            currentRentedCar = selectedCar;
            returnDetailsArea.append("Car rented by " + name + " for " + rentalDays + " days. " +
                    "Total Cost for Car " + selectedCar.getModel() + ": $" + selectedCar.getTotalCost() + "\n");
            JOptionPane.showMessageDialog(this, "Car rented successfully by " + name +
                    " for " + rentalDays + " days.");
        }
    }

    private void returnCar() {
        Car selectedCar = (Car) carModelComboBox.getSelectedItem();
        if (!selectedCar.isRented()) {
            JOptionPane.showMessageDialog(this, "Car is not currently rented.");
        } else {
            String returnDetails = "Car returned by " + selectedCar.getRenterName() +
                    " after " + selectedCar.getRentalDays() + " days. " +
                    "Total Cost for Car " + selectedCar.getModel() + ": $" + selectedCar.getTotalCost();
            returnDetailsArea.append(returnDetails + "\n");
            selectedCar.returnCar();
            currentRentedCar = null;
            JOptionPane.showMessageDialog(this, "Car returned successfully.");
        }
    }

    private void exchangeCar() {
        if (currentRentedCar == null) {
            JOptionPane.showMessageDialog(this, "You need to rent a car before exchanging.");
            return;
        }

        Car newCar = (Car) carModelComboBox.getSelectedItem();

        if (newCar.equals(currentRentedCar)) {
            JOptionPane.showMessageDialog(this, "You cannot exchange for the same car.");
            return;
        }

        if (newCar.isRented()) {
            JOptionPane.showMessageDialog(this, "The selected car is already rented.");
            return;
        }

        String renterName = currentRentedCar.getRenterName();
        int rentalDays = currentRentedCar.getRentalDays();
        double totalCost = currentRentedCar.getTotalCost();

        currentRentedCar.returnCar();
        returnDetailsArea.append("Car returned by " + renterName +
                " after " + rentalDays + " days. " +
                "Total Cost for Car " + currentRentedCar.getModel() + ": $" + totalCost + "\n");

        newCar.rent(renterName, rentalDays);
        returnDetailsArea.append("Car rented by " + renterName + " for " + rentalDays + " days. " +
                "Total Cost for Car " + newCar.getModel() + ": $" + newCar.getTotalCost() + "\n");

        JOptionPane.showMessageDialog(this, "Car exchanged successfully.");
        currentRentedCar = newCar;
    }

    private void resetDetails() {
        nameField.setText("");
        carMakeComboBox.setSelectedIndex(0);
        carModelComboBox.removeAllItems();
        rentalDaysField.setText("");
        costLabel.setText("Total Cost: ");
        termsCheckBox.setSelected(false);
        currentRentedCar = null;
    }

    private void displayTerms() {
        JFrame termsFrame = new JFrame("Terms and Conditions");
        termsFrame.setSize(400, 300);
        termsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        termsFrame.setLayout(new BorderLayout());

        JTextArea termsText = new JTextArea();
        termsText.setText("Terms and Conditions:\n\n1. You agree to be bound by the terms and conditions.\n2. Parts and labor are covered by a warranty, the terms of which will be provided to the Customer separately. Warranty periods may vary depending on the type of repair. \n3. The Shop will make reasonable efforts to complete the repair work within the estimated time frame, but delays may occur due to unforeseen circumstances.\n The Customer will be informed of any delays and updated on the progress.");

        JScrollPane termsScrollPane = new JScrollPane(termsText);
        termsFrame.add(termsScrollPane, BorderLayout.CENTER);

        termsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarRentalSystemGUI());
    }
}
