package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminForm extends JFrame {
    // Ui variables
    private JLabel labelLogo;
    private JPanel labelUsername;
    private JPanel adminFrame;
    private JButton buttonAddAdmin;
    private JButton buttonAddBook;
    private JButton buttonCommentSeek;
    private JLabel labelAbout;
    // related forms
    private final AddAdminForm addAdminForm = new AddAdminForm();
    private final AddBookForm addBookForm = new AddBookForm();
    private final CommentSeek commentSeekForm = new CommentSeek();

    /**
     * Constructor method,
     */
    public AdminForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(adminFrame); // set main frame
        // Title and size of frame
        setTitle("Admin Form");
        setSize(500, 600);
        // Action after closing this frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        buttonAddAdmin.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonAddAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonAddAdmin.setBackground(new Color(135, 69, 211));
                buttonAddBook.setBackground(new Color(234, 35, 123));
                buttonCommentSeek.setBackground(new Color(234, 35, 123));

            }
        });

        buttonAddBook.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonAddBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonAddAdmin.setBackground(new Color(234, 35, 123));
                buttonAddBook.setBackground(new Color(135, 69, 211));
                buttonCommentSeek.setBackground(new Color(234, 35, 123));
            }
        });

        buttonCommentSeek.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonCommentSeek.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonAddAdmin.setBackground(new Color(234, 35, 123));
                buttonAddBook.setBackground(new Color(234, 35, 123));
                buttonCommentSeek.setBackground(new Color(135, 69, 211));
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
        buttonCommentSeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commentSeekForm.setVisible(true);
            }
        });

        buttonAddAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdminForm.setVisible(true);
            }
        });

        buttonAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookForm.setVisible(true);
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
        AdminForm com = new AdminForm();
    }
}
