package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class LoginForm extends JFrame {
    // Ui variables
    private JPanel loginFrame;
    private JPanel labelUsername;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JLabel labelSignIn;
    private JLabel labelLogo;
    private JButton buttonSignIn;
    private JLabel labelUser;
    private JLabel labelPass;
    private JLabel labelErrorLogin;
    private JLabel fieldSignUpClick;
    private String password;
    private String username;
    // main forms
    AdminForm mainPageAdmin;
    UserForm mainPageUser;

    /**
     * Constructor method,
     */
    public LoginForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(loginFrame); // set main frame
        // Title and size of frame
        setTitle("Login Form");
        setSize(500, 600);
        // Action after closing this frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame

        setVisible(true); // show the frame and windows
    }

    public String[] getLoginInfo() {
        return new String[]{username, password};
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        buttonSignIn.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        fieldSignUpClick.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                fieldSignUpClick.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    /**
     * mouseMoveActions method for setting click actions of mouse for any object on frame
     */
    private void mouseClickActions() {
        buttonSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get text in fields
                username = fieldUsername.getText();
                password = String.valueOf(fieldPassword.getPassword());
                // empty error label
                labelErrorLogin.setText("          ");
                labelErrorLogin.setForeground(Color.RED);

                if (Objects.equals(username, ""))
                    labelErrorLogin.setText("⚠️ Username can't be empty !");

                else if (Objects.equals(password, ""))
                    labelErrorLogin.setText("⚠️ Password can't be empty !");

                else { // if username and password weren't empty
                    if (Manager.loginCheck(username, password)) { // if credentials were correct
                        labelErrorLogin.setForeground(Color.GREEN);
                        labelErrorLogin.setText("Logging in ...");

                        ActionListener taskPerformer = new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                setVisible(false); // hide login form

                                Manager.setUsername(username); // set username

                                // check if this person is admin or user
                                boolean admin = false;
                                for (String[] user : Manager.getAdminsUsers()[1]) {
                                    if (Objects.equals(user[0], username)) {
                                        admin = true;
                                        break;
                                    }
                                }

                                // show proper form
                                if (admin)
                                    mainPageAdmin = new AdminForm();
                                else {
                                    mainPageUser = new UserForm();
                                }
                            }
                        };
                        Timer timer = new Timer(1000, taskPerformer);
                        timer.setRepeats(false);
                        timer.start();

                    } else labelErrorLogin.setText("⚠️ Username or password is wrong !");
                }
            }
        });

        fieldSignUpClick.addMouseListener(new MouseAdapter() { // show signup frame
            public void mouseClicked(MouseEvent e) {
                SignUpForm signUpForm = new SignUpForm();
                labelErrorLogin.setText("          ");
                labelErrorLogin.setForeground(Color.RED);
                fieldUsername.setText("");
                fieldPassword.setText("");
                setVisible(false);
            }
        });
    }

    /**
     * main method for testing the frame, it's useless in general
     */
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
    }
}