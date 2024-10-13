import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {

    private int targetNumber;
    private int attempts;
    private JTextField textField;
    private JLabel resultLabel;

    public NumberGuessingGame() {
        targetNumber = new Random().nextInt(100) + 1;
        attempts = 0;

        setTitle("Number Guessing Game");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Guess the number (1-100):");
        textField = new JTextField(10);
        JButton guessButton = new JButton("Guess");

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(label);
        panel.add(textField);
        panel.add(guessButton);

        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(textField.getText());
            attempts++;

            if (guess < targetNumber) {
                resultLabel.setText("Higher. Attempts: " + attempts);
            } else if (guess > targetNumber) {
                resultLabel.setText("Lower. Attempts: " + attempts);
            } else {
                resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                textField.setEditable(false);
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
