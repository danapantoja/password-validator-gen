import java.security.SecureRandom;
import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PasswordGeneratorApp {
    private static final String lower= "abcdefghijklmnopqrstuwxyz";
    private static final String upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits="0123456789";
    private static final String special="!@#$%^&*()_-<>?";

    private static final SecureRandom random= new SecureRandom();
    private JPanel mainPanel;
    private CardLayout cardLayout;
    

    public PasswordGeneratorApp(){
        JFrame frame = new JFrame("Password Generator/Validator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        cardLayout= new CardLayout();
        mainPanel = new JPanel(cardLayout);

        

        JPanel passwordPanel=createPasswordPanel();
       JPanel homePanel = homePanel();
       JPanel validatePanel= validatePasswordPanel();

        
        mainPanel.add(homePanel,"Home");
        mainPanel.add(passwordPanel,"Password");
        mainPanel.add(validatePanel,"Validate" );
        frame.add(mainPanel);
        frame.setVisible(true);

        


    }

    private JPanel homePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Color col = new Color(255, 244, 242);

        panel.setBackground(col);
        
        JLabel titleLabel = new JLabel("Password Generator & Validator >_<");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 400, 39);
        
        JButton generateButton = new JButton("Go to Password Generator");
        generateButton.setFont(new Font("Dialog", Font.BOLD, 16));
        generateButton.setBounds(50, 90, 300, 50);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Password");
            }
        });

        JButton validateButton = new JButton("Go to Password Validator");
        validateButton.setFont(new Font("Dialog", Font.BOLD, 16));
        validateButton.setBounds(50, 150, 300, 50);
        validateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Validate");
            }
        });


        panel.add(titleLabel);
        panel.add(generateButton);
        panel.add(validateButton);
        return panel;
    }

    //generate password panel
    private JPanel createPasswordPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Color col = new Color(255, 244, 242);

        panel.setBackground(col);
        

        //password generation components
        JLabel generateLabel=new JLabel("Password Generator");
        generateLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        generateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        generateLabel.setBounds(0,10,400,39);

        JTextArea passwordField= new JTextArea();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        passwordField.setBounds(10, 70, 380, 50);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel lengthLabel = new JLabel("Password Length (6-12):");
        lengthLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        lengthLabel.setBounds(10, 130, 250,20);

        JTextField lengthField = new JTextField();
        lengthField.setFont(new Font("Dialog", Font.PLAIN, 16));
        lengthField.setBounds(210, 130, 180, 20);
        
        

        //checkbox options for character types
        JCheckBox lowerCheckBox = new JCheckBox("Lowercase Letters:", false);
        lowerCheckBox.setHorizontalTextPosition(JCheckBox.LEADING);
        lowerCheckBox.setFont(new Font("Dialog", Font.PLAIN, 16));
        lowerCheckBox.setBounds(5, 160, 250, 20);
       
        JCheckBox upperCheckBox=new JCheckBox("Uppercase Letters:", false);
        upperCheckBox.setHorizontalTextPosition(JCheckBox.LEADING);
        upperCheckBox.setFont(new Font("Dialog", Font.PLAIN, 16));
        upperCheckBox.setBounds(5,190,250,20);


        JCheckBox digitCheckBox= new JCheckBox("Digits:", false);
        digitCheckBox.setHorizontalTextPosition(JCheckBox.LEADING);
        digitCheckBox.setFont(new Font("Dialog", Font.PLAIN, 16));
        digitCheckBox.setBounds(5,220,250,20);

    
        JCheckBox specialCheckBox=new JCheckBox("Special Characters:", false);
        specialCheckBox.setHorizontalTextPosition(JCheckBox.LEADING);
        specialCheckBox.setFont(new Font("Dialog", Font.PLAIN, 16));
        specialCheckBox.setBounds(5,250,250,20);

        JLabel error= new JLabel();
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setFont(new Font("Dialog", Font.PLAIN, 15));
        error.setForeground(Color.RED);
        error.setBounds(0,280,400,20);



        JButton generateButton= new JButton("Generate Password");
        generateButton.setFont(new Font("Dialog", Font.BOLD, 20));
        generateButton.setHorizontalAlignment(SwingConstants.CENTER);
        generateButton.setBounds(50,300,300,40);


        JButton homeButton= new JButton("Return to Home Page");
        homeButton.setFont(new Font("Dialog", Font.BOLD, 20));
        homeButton.setHorizontalAlignment(SwingConstants.CENTER);
        homeButton.setBounds(50,350,300,40);
      
    
        //add components to panel
        panel.add(generateLabel);
        panel.add(passwordField);
        panel.add(lengthLabel);
        panel.add(lengthField);
        panel.add(lowerCheckBox);
        panel.add(upperCheckBox);
        panel.add(digitCheckBox);
        panel.add(specialCheckBox);
        panel.add(generateButton);
        panel.add(error);
        panel.add(homeButton);
        
    

        generateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int length= Integer.valueOf(lengthField.getText());
                boolean includeLower= lowerCheckBox.isSelected();
                boolean includeUpper=upperCheckBox.isSelected();
                boolean includeDigits= digitCheckBox.isSelected();
                boolean includeSpecial= specialCheckBox.isSelected();
                
                String password = generateComplexPassword(length, includeLower, includeUpper, includeDigits, includeSpecial);

                if (password.equals("Password must be between 6-12 characters") || password.equals("No character types selected")){
                    error.setText(password);
                }else{
                    error.setText("");
                    passwordField.setText(password);
                }

            }
        });
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                cardLayout.show(mainPanel, "Home");
            }

        });

        return panel;

    }
    private JPanel validatePasswordPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Color col = new Color(255, 244, 242);

        panel.setBackground(col);
        JLabel validateLabel= new JLabel("Password Validator");
        validateLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        validateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        validateLabel.setBounds(0,10,400,39);

        JLabel enterPass= new JLabel("Enter Password:");
        enterPass.setFont(new Font("Dialog", Font.PLAIN, 16));
        enterPass.setBounds(10, 50, 250, 30);

        JTextArea enteredField= new JTextArea();
        enteredField.setFont(new Font("Dialog", Font.PLAIN, 18));
        enteredField.setBounds(10, 80, 380, 50);
        enteredField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel resultLabel=new JLabel("Results:");
        resultLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        resultLabel.setBounds(10, 140, 250, 30);

        JTextArea result=new JTextArea(5,30);
        result.setEditable(false);
        result.setBounds(10, 170,380, 90);
        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton validateButton= new JButton("Validate Password");

        validateButton.setFont(new Font("Dialog", Font.BOLD, 20));
        validateButton.setHorizontalAlignment(SwingConstants.CENTER);
        validateButton.setBounds(50,300,300,40);

        JButton homeButton= new JButton("Return to Home Page");
        homeButton.setFont(new Font("Dialog", Font.BOLD, 20));
        homeButton.setHorizontalAlignment(SwingConstants.CENTER);
        homeButton.setBounds(50,350,300,40);


        //adding to panel
        panel.add(validateLabel);
        panel.add(enterPass);
        panel.add(enteredField);
        panel.add(resultLabel);
        panel.add(result);
        panel.add(validateButton);
        panel.add(homeButton);


        //validation
        validateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                
                String password = enteredField.getText().trim();
                
                String validationResult = validation(password);
                result.setText(validationResult);

            }
        });

        //back to home
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                cardLayout.show(mainPanel, "Home");
            }

        });

        return panel;
    }



    private static String generateComplexPassword(int length, boolean includeLower, boolean includeUpper, boolean includeDigits, boolean includeSpecial){
        if(length<6 || length>12){
            return"Password must be between 6-12 characters";
        }
        StringBuilder password=new StringBuilder(length);

        //includes the characters in one string so we can pick random characters from it depending on the users wants
        StringBuilder combinedChars =new StringBuilder();

        if(includeLower) combinedChars.append(lower);
        if(includeUpper) combinedChars.append(upper);
        if(includeDigits) combinedChars.append(digits);
        if(includeSpecial) combinedChars.append(special);

        if(combinedChars.length()==0){
            return"No character types selected";
        }
        for (int i=0; i<length; i++){
            int index = random.nextInt(combinedChars.length());
            password.append(combinedChars.charAt(index));
        }
        return password.toString();
    }

    private static String validation(String password){

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            }
            if (Character.isLowerCase(ch)) {
                hasLower = true;
            } 
            if (Character.isDigit(ch)) {
                hasDigit = true;
            }
            if (isSpecialCharacter(ch)) {
                hasSpecial = true;
            }
        }
        
        
        
            StringBuilder errors=new StringBuilder();
            if (!( password.length() >= 6 && password.length() <= 12)) errors.append("Password must be between 6-12 characters.\n");
            if (!hasUpper) errors.append("Password must contain at least one uppercase letter.\n");
            if (!hasLower) errors.append("Password must contain at least one lowercase letter.\n");
            if (!hasDigit) errors.append("Password must contain at least one digit.\n");
            if (!hasSpecial) errors.append("Password must contain at least one special character.\n");

            if (errors.length()==0){
                return"Password is valid!";
            }
            return errors.toString();
        


    }
    private static boolean isSpecialCharacter(char ch) {
        // special chars
        char[] specialChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '=', '[', ']', '{', '}', ';', ':', '\'', '"', ',', '.', '/', '<', '>', '?', '\\', '|'};
        for (char specialChar : specialChars) {
            if (ch == specialChar) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        new PasswordGeneratorApp();
       
    }
}
