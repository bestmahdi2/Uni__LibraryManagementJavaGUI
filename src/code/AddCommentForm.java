package code;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AddCommentForm extends JFrame {
    // Ui variables
    private JPanel addCommentForm;
    private JLabel labelLogo;
    private JButton addACommentButton;
    private JPanel labelUsername;
    private JEditorPane textEditorPane;
    private JLabel labelPhotoBook;
    private JLabel labelLeft;
    private JLabel errorAddComment;
    private JLabel labelAbout;
    // keeper variables
    private final String[] photosAddresses; // all photos addresses
    private final String[][][] books; // all books
    private final int maxLength = 290; // max length of character in text area
    private boolean allowToClick = true;

    /**
     * Constructor method,
     * @param bookNumber An integer value of book
     */
    public AddCommentForm(int bookNumber) {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(addCommentForm);  // set main frame
        // Title and size of frame
        setTitle("Add Book Comment Form");
        setSize(550, 700);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        // fill arrays
        photosAddresses = Manager.getPhotosAddresses();
        books = Manager.getBooks();

        // add book to panel
        addBookToPanel(bookNumber);
        lengthCheckerJEdit(); // check length of text area

        mouseClickActions(bookNumber); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     * @param bookNumber An integer value of book
     */
    private void addBookToPanel(int bookNumber) {
        // add book photo to panel
        try {
            BufferedImage imageBook = ImageIO.read(new File(photosAddresses[bookNumber]));
            labelPhotoBook.setIcon(new ImageIcon(imageBook.getScaledInstance(100, 157, Image.SCALE_SMOOTH)));
            labelPhotoBook.setBorder(new EmptyBorder(10, 10, 10, 10));
            labelPhotoBook.setFont(new Font(labelPhotoBook.getFont().getName(), Font.PLAIN, 14));
            labelPhotoBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        } catch (IOException ignored) {
        }
    }

    /**
     * lengthCheckerJEdit method for checking any edit on text editor
     */
    private void lengthCheckerJEdit() {
        textEditorPane.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                reachMaxText();
            }

            public void removeUpdate(DocumentEvent e) {
                reachMaxText();
            }

            public void insertUpdate(DocumentEvent e) {
                reachMaxText();
            }

        });
    }

    /**
     * reachMaxText method for checking if length of text is more than maxLength or not,
     * if it does, set allowToClick to false so button won't work
     */
    private void reachMaxText() {
        labelLeft.setText(String.format("Left: %d", maxLength - textEditorPane.getText().length()));
        allowToClick = textEditorPane.getText().length() <= maxLength;
    }

    /**
     * cleanup method for clean text area
     */
    private void cleanup() {
        textEditorPane.setText("");
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        addACommentButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addACommentButton.setBackground(new Color(135, 69, 211));
            }
        });

        errorAddComment.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setBackground(new Color(234, 35, 123));
            }
        });

        textEditorPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setBackground(new Color(234, 35, 123));
            }
        });

        labelPhotoBook.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setBackground(new Color(234, 35, 123));
            }
        });

        labelLeft.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setBackground(new Color(234, 35, 123));
            }
        });

        labelAbout.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                labelAbout.setBackground(new Color(234, 35, 123));
            }
        });
    }

    /**
     * mouseMoveActions method for setting click actions of mouse for any object on frame
     */
    private void mouseClickActions(int bookNumber) {
        addACommentButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (allowToClick && !Objects.equals(textEditorPane.getText(), "")) {
                    errorAddComment.setText("     "); // empty error label

                    // show dialog to find out if user recommend this book or not
                    int dialogResult = JOptionPane.showConfirmDialog(null, String.format("Do you recommend this book (%s) ? ", books[bookNumber][0][0]), "Recommendation Dialog", JOptionPane.YES_NO_OPTION);

                    // if this comment is not duplicated it would be added
                    boolean commentAdded = Manager.addComment(Manager.getUsername(), books[bookNumber][0][0], textEditorPane.getText(), dialogResult == JOptionPane.YES_OPTION);

                    if (commentAdded) { // if it wasn't duplicated comment
                        JOptionPane.showMessageDialog(null, "Thank you \uD83D\uDE0D, you comment have been saved !", "Commenting Success !", JOptionPane.INFORMATION_MESSAGE);
                        cleanup();
                    }
                    else { // if it was duplicated comment
                        JOptionPane.showMessageDialog(null, "Your comment is duplicated !", "Commenting failed !", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
                else if (Objects.equals(textEditorPane.getText(), "")){
                    errorAddComment.setText("Comment can't be empty !");
                }
                else {
                    errorAddComment.setText("Comment have to be under 290 characters !");
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
        AddCommentForm com = new AddCommentForm(0);
    }
}
