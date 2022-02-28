package code;

import javax.swing.*;

public class AboutForm extends JFrame {
    // Ui variables
    private JPanel inJpanel;
    private JPanel labelUsername;
    private JLabel labelLogo;
    private JLabel version;
    private JPanel aboutForm;

    /**
     * Constructor method,
     */
    public AboutForm(){
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(aboutForm); // set main frame
        // Title and size of frame
        setTitle("User Form");
        setSize(500, 500);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        // set version
        setVersion();

        setVisible(true); // show the frame and windows
    }

    private void setVersion(){
        version.setText("Version " + Manager.getVersion());
    }
}
