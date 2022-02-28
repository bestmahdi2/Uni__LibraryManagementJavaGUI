package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class SignUpForm extends JFrame {
    // Ui variables
    private JLabel labelLogo;
    private JLabel labelSignIn;
    private JPanel labelUsername;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JLabel labelUser;
    private JLabel labelPass;
    private JButton buttonSignUp;
    private JLabel labelErrorSignUp;
    private JLabel labelFirstName;
    private JLabel labelLastName;
    private JTextField fieldFirstName;
    private JTextField fieldLastName;
    private JPasswordField fieldConfirmPassword;
    private JPanel signUpFrame;
    private JLabel labelErrorAddBook;
    // keeper for name, username, firstName and lastName of the new user
    private String password;
    private String username;
    private String firstName;
    private String lastName;

    /**
     * Constructor method,
     */
    public SignUpForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(signUpFrame); // set main frame
        // Title and size of frame
        setTitle("SignUp Form");
        setSize(500, 750);

        // Action after closing this frame
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame

        setVisible(true); // show the frame and windows
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        buttonSignUp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    /**
     * mouseMoveActions method for setting click actions of mouse for any object on frame
     */
    private void mouseClickActions() {
        buttonSignUp.addActionListener(new ActionListener() { // when click of signup button
            @Override
            public void actionPerformed(ActionEvent e) {
                // get info
                username = fieldUsername.getText();
                password = String.valueOf(fieldPassword.getPassword());
                firstName = fieldFirstName.getText();
                lastName = fieldLastName.getText();

                // clean up label error
                labelErrorSignUp.setText("          ");
                labelErrorSignUp.setForeground(Color.RED);

                boolean continues = true;

                // conditions to signup
                for (String[] user : Manager.getAdminsUsers()[2]) {
                    if (Objects.equals(user[0], username)) {
                        labelErrorSignUp.setText("⚠️ Username is taken !");
                        continues = false;
                    }
                }
                if (continues) {
                    if (Objects.equals(username, ""))
                        labelErrorSignUp.setText("⚠️ Username can't be empty !");

                    else if (Objects.equals(password, ""))
                        labelErrorSignUp.setText("⚠️ Password can't be empty !");

                    else if (!password.equals(String.valueOf(fieldConfirmPassword.getPassword())))
                        labelErrorSignUp.setText("⚠️ Passwords don't match !");

                    else if (Objects.equals(firstName, ""))
                        labelErrorSignUp.setText("⚠️ Firstname can't be empty !");

                    else if (!Manager.isAlpha(firstName) || !Manager.isAlpha(lastName))
                        labelErrorSignUp.setText("⚠️ Firstname/Lastname can only be alphabetical !");

                    else { // if no error:
                        // sign up this user to database
                        Manager.signUp(username, password, firstName, lastName, "user");

                        labelErrorSignUp.setForeground(Color.GREEN);
                        labelErrorSignUp.setText("Signing up ...");

                        ActionListener taskPerformer = new ActionListener() { // go back to login form
                            public void actionPerformed(ActionEvent evt) {
                                setVisible(false);
                                Manager.loginForm.setVisible(true);
                                dispose();
                            }
                        };
                        Timer timer = new Timer(1000, taskPerformer);
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            }
        });
    }
}
