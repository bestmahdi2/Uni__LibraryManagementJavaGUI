package code;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CommentSeek extends JFrame {
    // Ui variables
    private JPanel labelUsername;
    private JLabel labelLogo;
    private JLabel labelUser;
    private final JTextArea textAreaComment;
    private JPanel commentSeekFrame;
    private JLabel labelErrorSeekComment;
    private JPanel inJpanel;
    private JScrollPane jscrollBook;
    private JScrollPane jscrollComment;
    private JLabel labelAbout;
    private final JTree treeUserBook;

    /**
     * Constructor method,
     */
    public CommentSeek() {
        setResizable(false); // forbidden user to resize the frame
        setLocationRelativeTo(null); // to be opened in center
        try { // change its look to Microsoft Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentPane(commentSeekFrame); // set main frame
        // Title and size of frame
        setTitle("Comment Seek Form");
        setSize(500, 700);

        // set Icon for JFrame
        setIconImage(new ImageIcon("src/images/mOMIDs.png").getImage());

        // make a new a tree frame
        treeUserBook = new JTree();
        jscrollBook.setViewportView(treeUserBook); // add it to scroll

        // make a new text area
        textAreaComment = new JTextArea();
        jscrollComment.setViewportView(textAreaComment); // add it to scroll
        fillTreeBookUser(); // fill tree frame

        mouseClickActions(); // mouse click actions to object in frame
        mouseMoveActions(); // mouse move actions to object in frame
    }

    /**
     * fillTreeBookUser method for filling tree frame with users and their comments on books
     */
    public void fillTreeBookUser() {
        labelErrorSeekComment.setText(""); // empty label error

        // remove old data in tree
        DefaultTreeModel model = (DefaultTreeModel) treeUserBook.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();

        // get all comments of all users
        String[][][] userInfo = Manager.getUsersActivity();

        if (userInfo[0][0].length != 0) {
            for (String[][] info : userInfo) { // make folder for every user, add node for any book they commented on
                DefaultMutableTreeNode nefo = new DefaultMutableTreeNode(info[0][0]);
                ArrayList<String> dupCheck = new ArrayList<>();
                for (int i = 1; i < info.length; i++) {
                    if (!dupCheck.contains(info[i][0])) {
                        dupCheck.add(info[i][0]);
                        nefo.add(new DefaultMutableTreeNode(info[i][0]));
                    }
                }
                root.add(nefo);
            }

        } else {
            labelErrorSeekComment.setText("No users' comments were found !");
        }
        // add model to root
        model.reload(root);
    }

    /**
     * mouseMoveActions method for setting move actions of mouse for any object on frame
     */
    private void mouseMoveActions() {
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
        // if any of nodes were clicked, it searches for its comment and show it
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = treeUserBook.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = treeUserBook.getPathForLocation(e.getX(), e.getY());
                if (selPath != null && selRow > 0) {
                    String[] selPathArray = selPath.toString().replace("[", "").replace("]", "").split(", ");
                    if (selPathArray.length > 2) {
                        if (e.getClickCount() == 1 || e.getClickCount() == 2) {
                            textAreaComment.setText(Manager.getUserComments(selPathArray[1], selPathArray[2]));
                        }
                    }
                }
            }
        };
        treeUserBook.addMouseListener(ml);

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
        CommentSeek commentSeekForm = new CommentSeek();
        commentSeekForm.setVisible(true);
    }

}
