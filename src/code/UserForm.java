package code;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserForm extends JFrame {
    // Ui variables
    private JPanel labelUsername;
    private JLabel labelLogo;
    private JScrollPane jscrollBooks;
    private JPanel userFrame;
    private JLabel reloadUserForm;
    private JLabel labelAbout;
    // final arrays of books, images, and addresses got from database
    private JLabel[] books;
    private final BufferedImage[] images;
    private final String[] photosAddresses;
    // related forms
    private BookCommentForm bookCommentForm;

    /**
     * Constructor method,
     */
    public UserForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(userFrame); // set main frame
        // Title and size of frame
        setTitle("User Form");
        setSize(600, 800);
        // Action after closing this frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        // fill arrays with contents of program and database
        photosAddresses = Manager.getPhotosAddresses();
        images = prepareImages();
        books = prepareBooks();

        // set empty boarder to scroll panel
        jscrollBooks.setBorder(new EmptyBorder(5, 20, 5, 20));
        addBookToPanel(); // add all books in database to scroll panel

        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame

        setVisible(true); // show the frame and windows
    }

    /**
     * prepareBooks method for preaparing the books to be shown in scroll panel
     *
     * @return A JLabel array of books name, author and score of recommendation for each book
     */
    private JLabel[] prepareBooks() {
        String[][][] books = Manager.getBooks();
        JLabel[] prepared = new JLabel[books.length];

        float floatScore;
        for (int i = 0; i < prepared.length; i++) {
            // find score of recommendation in percent
            if (books[i][1].length > 0)
                floatScore = (float) (100 * Integer.parseInt(books[i][0][4]) / books[i][1].length) / 20;
            else
                floatScore = 0;

            // temp string of book values
            String text = String.format("<html><span style=\"font-weight: bold\">%s</span><br/><br/>%s<br/><br/>\uD83C\uDF1F %.1f</html>", books[i][0][0], books[i][0][1], floatScore);
            prepared[i] = new JLabel(text); // add this string to JLabel arrays
        }

        return prepared;
    }

    /**
     * prepareImages method for preaparing the images of books to be shown in scroll panel
     *
     * @return A BufferedImage array of books images
     */
    private BufferedImage[] prepareImages() {
        // initialize array of images to be filled
        BufferedImage[] images = new BufferedImage[prepareBooks().length];

        for (int i = 0; i < images.length; i++) {
            try { // open every image of books with address of them and adds to array
                images[i] = ImageIO.read(new File(photosAddresses[i]));
            } catch (IOException ignored) {
            }
        }

        return images;
    }

    /**
     * addBookToPanel method for adding each book image and its information
     */
    private void addBookToPanel() {
        JPanel imagesPanel = new JPanel(); // create a new panel for image and text

        for (int i = 0; i < books.length; i++) {
            // add photo and text of each book to its panel
            books[i].setIcon(new ImageIcon(images[i].getScaledInstance(100, 157, Image.SCALE_SMOOTH)));
            books[i].setBorder(new EmptyBorder(10, 10, 10, 10));
            books[i].setFont(new Font(books[i].getFont().getName(), Font.PLAIN, 14));
            books[i].setIconTextGap(10);

            // add this panel to main panel
            imagesPanel.add(books[i], BorderLayout.WEST);
            imagesPanel.add(new JSeparator());
        }

        // set layout for main panel
        imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.Y_AXIS));

        // set scroll panel this main panel
        jscrollBooks.setViewportView(imagesPanel);
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        for (int i = 0; i < books.length; i++) {
            int finalI = i;
            books[i].addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);
                    // add mouse click action for every object on frame
                    books[finalI].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });
        }

        reloadUserForm.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                reloadUserForm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        MouseListener ml;
        for (int i = 0; i < books.length; i++) { // add mouse action click to any object on frame
            int finalI = i;
            ml = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    bookCommentForm = new BookCommentForm(finalI);
                    bookCommentForm.setVisible(true);
                }
            };
            books[i].addMouseListener(ml);
        }

        MouseListener ml1;
        ml1 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                books = prepareBooks();
                addBookToPanel(); // add all books in database to scroll panel
                mouseClickActions();
                mouseMoveActions();
            }
        };
        reloadUserForm.addMouseListener(ml1);

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
        UserForm com = new UserForm();
    }
}
