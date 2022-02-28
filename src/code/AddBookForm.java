package code;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class AddBookForm extends JFrame {
    // Ui variables
    private JPanel adminFrame;
    private JPanel labelUsername;
    private JLabel labelLogo;
    private JLabel labelName;
    private JTextField fieldBookName;
    private JButton buttonOpenPhoto;
    private JButton buttonAddBook;
    private JTextField fieldBookPhotoAddress;
    private JLabel labelAuthor;
    private JTextField fieldBookAuthor;
    private JLabel labelErrorAddBook;
    private JLabel labelAbout;
    // keeper for name, author and photo address of the new book
    private String bookName;
    private String authorName;
    private String photoAddress;

    /**
     * Constructor method,
     */
    public AddBookForm() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(adminFrame); // set main frame
        // Title and size of frame
        setTitle("Add Book Form");
        setSize(500, 700);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame
    }

    /**
     * cleanup method for clean text fields
     */
    private void cleanup() {
        fieldBookName.setText("");
        fieldBookAuthor.setText("");
        fieldBookPhotoAddress.setText("");
    }

    /**
     * cleanup method for clean text fields
     *
     * @param sourceFile The source file of the photo
     * @param destLoc    String value of destination location with folder and file
     * @param destFolder String value of destination location with folder
     * @param destType   String value of file type
     */
    private void copyPhoto(File sourceFile, String destLoc, String destFolder, String destType) {
        try { // try to copy photo to database location of photos
            Files.copy(sourceFile.toPath(),
                    (new File(destLoc)).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // rename the file to name of the book for ease of access
        File destFile = new File(destLoc);
        File newFile = new File(destFolder + fieldBookName.getText() + destType);
        destFile.renameTo(newFile);
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
        fieldBookName.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonAddBook.setBackground(new Color(234, 35, 123));
                buttonOpenPhoto.setBackground(new Color(234, 35, 123));

            }
        });

        fieldBookPhotoAddress.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonAddBook.setBackground(new Color(234, 35, 123));
                buttonOpenPhoto.setBackground(new Color(234, 35, 123));
            }
        });

        buttonOpenPhoto.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonOpenPhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonAddBook.setBackground(new Color(234, 35, 123));
                buttonOpenPhoto.setBackground(new Color(135, 69, 211));
            }
        });

        buttonAddBook.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                buttonAddBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttonOpenPhoto.setBackground(new Color(234, 35, 123));
                buttonAddBook.setBackground(new Color(135, 69, 211));
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
        buttonAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get values
                bookName = fieldBookName.getText();
                authorName = fieldBookAuthor.getText();
                photoAddress = fieldBookPhotoAddress.getText();

                // set error label to none
                labelErrorAddBook.setText("          ");
                labelErrorAddBook.setForeground(Color.RED);

                boolean continues = true;

                // check if a book is already in program or not
                for (String[][] user : Manager.getBooks()) {
                    if (Objects.equals(user[0][0], bookName)) {
                        labelErrorAddBook.setText("⚠️ Book name is taken !");
                        continues = false;
                    }
                }

                if (continues) {
                    if (Objects.equals(bookName, ""))
                        labelErrorAddBook.setText("⚠️ Book name can't be empty !");

                    else if (Objects.equals(authorName, ""))
                        labelErrorAddBook.setText("⚠️ Author Name can't be empty !");

                    else if (!Manager.isAlpha(authorName))
                        labelErrorAddBook.setText("⚠️ Author Name can only be alphabetical !");

                    else if (Objects.equals(photoAddress, ""))
                        labelErrorAddBook.setText("⚠️ Photo must be chosen !");

                    else if (!new File(photoAddress).isFile())
                        labelErrorAddBook.setText("⚠️ Photo does not exist !");

                    else { // if all conditions were fine:
                        labelErrorAddBook.setText("          ");

                        // create new file, choose destination
                        File sourceFile = new File(photoAddress);
                        String destFolder = "src/photos/";
                        String destLoc = destFolder + sourceFile.getName();
                        String destType = destLoc.substring(destLoc.lastIndexOf('.'));

                        copyPhoto(sourceFile, destLoc, destFolder, destType); // copy file to photos folder

                        // add book to database
                        Manager.addBook(bookName, authorName, sourceFile.getPath(), destFolder + fieldBookName.getText() + destType, "0");

                        // show a message
                        JOptionPane.showMessageDialog(null, String.format("'%s' added to books successfully !", bookName), "Book Adding Success !", JOptionPane.INFORMATION_MESSAGE);

                        cleanup(); // clean text fields
                    }
                }
            }
        });

        buttonOpenPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // open file chooser
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
                fileChooser.setFileFilter(filter);

                if (fileChooser.showOpenDialog(adminFrame) == JFileChooser.APPROVE_OPTION) {
                    fieldBookPhotoAddress.setText(String.valueOf(fileChooser.getSelectedFile()));
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
        AddBookForm com = new AddBookForm();
    }
}
