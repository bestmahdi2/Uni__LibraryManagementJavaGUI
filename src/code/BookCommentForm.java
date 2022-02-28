package code;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BookCommentForm extends JFrame {
    // Ui variables
    private JPanel labelUsername;
    private JScrollPane jscrollBook;
    private JLabel labelLogo;
    private JPanel bookCommentForm;
    private JButton addACommentButton;
    private JLabel bookInfoLabel;
    private JLabel reloadBookCommentForm;
    private JLabel labelAbout;
    private final String[] photosAddresses;
    private String[][][] books;
    private JPanel panel = new JPanel();
    private SimpleAttributeSet attributeSet;
    private SimpleAttributeSet center;
    // related form
    private AddCommentForm addCommentForm;

    /**
     * Constructor method,
     *
     * @param bookNumber An integer value of book
     */
    public BookCommentForm(int bookNumber) {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(bookCommentForm); // set main frame
        // Title and size of frame
        setTitle("Book Comment Form");
        setSize(650, 800);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        // set empty boarder to scroll panel
        jscrollBook.setBorder(new EmptyBorder(5, 10, 5, 10));

        // text center alignment
        attributeSet = new SimpleAttributeSet();
        center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        // fill arrays
        photosAddresses = Manager.getPhotosAddresses();
        books = Manager.getBooks();

        // add book and comments to panel
        addBookToPanel(bookNumber);
        addCommentsToPanel(bookNumber);

        mouseClickActions1(bookNumber); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame
    }

    /**
     * addBookToPanel method for add book photo to panel
     *
     * @param bookNumber An integer value of book
     */
    private void addBookToPanel(int bookNumber) {
        // create stars panel and initialize score
        JPanel stars = new JPanel();
        stars.setMaximumSize(new Dimension(80, 40));
        stars.setPreferredSize(new Dimension(80, 40));
        float floatScore = 0;
        int score = 0;

        try { // open stars photo
            BufferedImage imageStar = ImageIO.read(new File("src/images/star.png"));
            if (books[bookNumber][1].length > 0) {
                floatScore = (float) (100 * Integer.parseInt(books[bookNumber][0][4]) / books[bookNumber][1].length) / 20;
            } else
                floatScore = 0;
            score = (int) floatScore;
            for (int i = 0; i < score; i++) { // add stars to panel
                stars.add(new JLabel(new ImageIcon(imageStar.getScaledInstance(10, 10, Image.SCALE_SMOOTH))));
            }
        } catch (IOException ignored) { // ignore exceptions
        }

        if (floatScore - score != 0) { // add half star if score is float
            try {
                BufferedImage imageHalfStar = ImageIO.read(new File("src/images/star_half.png"));
                stars.add(new JLabel(new ImageIcon(imageHalfStar.getScaledInstance(5, 10, Image.SCALE_SMOOTH))));
            } catch (IOException ignored) {
            }
        }
        // add text to stars
        stars.add(new JLabel(String.format("(%.1f)", floatScore)));
        // set boarder for stars
        stars.setBorder(new EmptyBorder(0, 0, 40, 0));

        // open and add photo of book to panel
        try {
            BufferedImage imageBook = ImageIO.read(new File(photosAddresses[bookNumber]));
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(new ImageIcon(imageBook.getScaledInstance(100, 157, Image.SCALE_SMOOTH)));
            imageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
            imageLabel.setFont(new Font(imageLabel.getFont().getName(), Font.PLAIN, 14));
            imageLabel.setIconTextGap(10);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // add book photo to panel
            panel.add(imageLabel);
        } catch (IOException ignored) { // ignore exceptions
        }

        // Comments info text
        String commentsNum = "No Comments";
        if (books[bookNumber][1].length > 1)
            commentsNum = String.format("%d Comments", books[bookNumber][1].length);
        else if (books[bookNumber][1].length == 1)
            commentsNum = "1 Comment";

        // info setting part
        JTextPane bookNamePane = new JTextPane();
        JTextPane bookInfoPane = new JTextPane();

        bookInfoPane.setText(books[bookNumber][0][1]);
        bookNamePane.setText(books[bookNumber][0][0]);

        bookNamePane.setOpaque(false);
        bookInfoPane.setOpaque(false);
        bookNamePane.setEditable(false);
        bookInfoPane.setEditable(false);
        StyledDocument docName = bookNamePane.getStyledDocument();
        StyledDocument docInfo = bookInfoPane.getStyledDocument();
        bookNamePane.setCharacterAttributes(attributeSet, true);
        bookInfoPane.setCharacterAttributes(attributeSet, true);
        bookNamePane.setMaximumSize(new Dimension(500, 50));
        bookInfoPane.setMaximumSize(new Dimension(500, 100));
        docName.setParagraphAttributes(0, docName.getLength(), center, false);
        docInfo.setParagraphAttributes(0, docInfo.getLength(), center, false);
        bookNamePane.setFont(new Font(bookNamePane.getFont().getName(), Font.BOLD, 16));
        bookInfoPane.setFont(new Font(bookInfoPane.getFont().getName(), Font.PLAIN, 16));

        // add main parts to panel
        panel.add(bookNamePane);
        panel.add(bookInfoPane);
        panel.add(stars);
    }

    /**
     * addCommentsToPanel method for add book commments to panel
     *
     * @param bookNumber An integer value of book
     */
    private void addCommentsToPanel(int bookNumber) {
        for (int i = 0; i < books[bookNumber][1].length; i++) { // add each comment of this book to panel

            // add user's name
            JTextArea tempUser = new JTextArea();
            tempUser.setText(books[bookNumber][2][i]);
            tempUser.setOpaque(false);
            tempUser.setEditable(false);
            tempUser.setMaximumSize(new Dimension(450, 50));
            tempUser.setFont(new Font(bookInfoLabel.getFont().getName(), Font.BOLD, 14));
            tempUser.setBorder(new EmptyBorder(10, 0, 5, 0));
            tempUser.setLineWrap(true);
            tempUser.setWrapStyleWord(true);

            // add user's comment
            JTextArea tempComment = new JTextArea();
            tempComment.setText(books[bookNumber][1][i]);
            tempComment.setOpaque(false);
            tempComment.setEditable(false);
            tempComment.setMaximumSize(new Dimension(450, 100));
            tempComment.setFont(new Font(bookInfoLabel.getFont().getName(), Font.PLAIN, 14));
            tempComment.setBorder(new EmptyBorder(5, 0, 10, 0));
            tempComment.setLineWrap(true);
            tempComment.setWrapStyleWord(true);

            // add separator
            JSeparator js = new JSeparator();
            js.setMaximumSize(new Dimension(450, 10));
            // add user's name and comment to panel
            panel.add(js);
            panel.add(tempUser);
            panel.add(tempComment);
        }

        // add empty separator
        panel.add(new JSeparator());

        // set layout and add panel to scroll panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jscrollBook.setViewportView(panel);
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

        jscrollBook.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                addACommentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addACommentButton.setBackground(new Color(234, 35, 123));
            }
        });

        reloadBookCommentForm.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                reloadBookCommentForm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
    private void mouseClickActions1(int bookNumber) {
        MouseListener ml;
        ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                addCommentForm = new AddCommentForm(bookNumber);
                addCommentForm.setVisible(true);
            }
        };
        addACommentButton.addMouseListener(ml);

        MouseListener ml2;
        ml2 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                AboutForm aboutForm = new AboutForm();
            }
        };
        labelAbout.addMouseListener(ml2);

        mouseClickActions2(bookNumber);
    }

    private void mouseClickActions2(int bookNumber) {
        MouseListener ml1;
        ml1 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                books = Manager.getBooks();
                panel = new JPanel();
                addBookToPanel(bookNumber);
                addCommentsToPanel(bookNumber);
                mouseClickActions2(bookNumber);
                mouseMoveActions();
            }
        };
        reloadBookCommentForm.addMouseListener(ml1);
    }


    /**
     * main method for testing the frame, it's useless in general
     */
    public static void main(String[] args) throws IOException {
        BookCommentForm com = new BookCommentForm(0);

    }
}

