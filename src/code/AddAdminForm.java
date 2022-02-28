package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class AddAdminForm extends JFrame {
    // Ui variables
    private JPanel addAdminFrame;
    private JPanel labelUsername;
    private JLabel labelLogo;
    private JComboBox comboBoxAdmins;
    private JButton buttonChangeToAdmin;
    private JLabel labelChangeToAdmin;
    private JLabel labelAbout;

    /**
     * Constructor method,
     */
    public AddAdminForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(addAdminFrame);   // set main frame
        // Title and size of frame
        setTitle("Add Admin Form");
        setSize(500, 600);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        fillComboBox(); // fill combobox of users
        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame
    }

    /**
     * fillComboBox method for filling combobox with users
     */
    private void fillComboBox() {
        String[][] users = Manager.getAdminsUsers()[2];
        comboBoxAdmins.removeAllItems();
        for (String[] user : users) {
            comboBoxAdmins.addItem(new ComboItem(user[0], user[0]));
        }

    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        buttonChangeToAdmin.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonChangeToAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonChangeToAdmin.setBackground(new Color(135, 69, 211));
            }
        });

        comboBoxAdmins.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                comboBoxAdmins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonChangeToAdmin.setBackground(new Color(234, 35, 123));
            }
        });

        labelAbout.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                labelAbout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

    }

    /**
     * mouseMoveActions method for setting click actions of mouse for any object on frame
     */
    private void mouseClickActions() {
        buttonChangeToAdmin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (comboBoxAdmins.getItemCount() != 0) { // find user in combobox and change it to admin
                    String userToAdmin = ((ComboItem) comboBoxAdmins.getSelectedItem()).getValue();
                    Manager.promoteToAdmin(userToAdmin);
                    JOptionPane.showMessageDialog(null, String.format("User '%s' promoted to admin !", userToAdmin), "Admin Adding Dialog", JOptionPane.INFORMATION_MESSAGE);
                    fillComboBox();
                }
            }
        });

        MouseListener ml2;
        ml2 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                AboutForm aboutForm = new AboutForm();
            }
        };
        labelAbout.addMouseListener(ml2);
    }

    /**
     * main method for testing the frame, it's useless in general
     */
    public static void main(String[] args) {
        AddAdminForm com = new AddAdminForm();
    }
}

/**
 * ComboItem class for redesign it combo items and user it to get value of current selection of combobox
 */
class ComboItem {
    private final String key;
    private final String value;

    public ComboItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}