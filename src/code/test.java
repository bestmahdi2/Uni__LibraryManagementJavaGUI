package code;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        int[][] array = new int[3][3];
        ArrayList<Integer> numbers = new ArrayList();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < array.length; i++) {
            String thisArray = "Array " + (i + 1);
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(thisArray + ", element " + j + " > ");
                array[i][j] = scanner.nextInt();
                numbers.add(array[i][j]);
            }
        }

        numbers = (ArrayList<Integer>) numbers.stream().distinct().collect(Collectors.toList());

        for (int n : numbers) {
            int counter = 0;
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] == n)
                        counter++;
                }
            }
            System.out.println(n + " repeated " + counter + " times !");
        }

    }
}


// LoginForm:

//  getContentPane().setLayout(new FlowLayout());

/*        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Manager.loginForm.setVisible(true);
            }
        };
        addWindowListener(exitListener);*/

/////////////////////////////////////////////////////////

// AddBookForm

//              open image:
//                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));

/////////////////////////////////////////////////////////

// Manager:

// bigger: {{{"Mahdi"}, {"London", "it's good"}, {"Tehran", "it's good"}}, {{"Ali"}, {"Tehran", "it's good"}}  };

// small: {{"Mahdi"}, {"London", "it's good"}, {"Tehran", "it's good"}}

// smaller: {{"Mahdi"}, {"London", "it's good"}}


//        /* open image:
//        JFileChooser fc = new JFileChooser();
//        if(fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION){
//            BufferedImage img = ImageIO.read(fc.getSelectedFile());//it must be an image file, otherwise you'll get an exception
//            JLabel label = new JLabel();
//            label.setIcon(new ImageIcon(img));
//            app.getContentPane().add(label);
//        }*/


//        signUp("ali", "1234", "Ali", "Badiee", "admin");
//        signUp("mahdi", "4321", "Mahdi", "Badiee", "user");
//        addBook("Man Pish Az To", "JoJo Moyes", "src/photos/Man Pish Az To.jpg", "src/photos/Man Pish Az To.jpg", "1");
//        addBook("Pas Az To", "JoJo Moyes", "src/photos/Pas Az To.jpg", "src/photos/Pas Az To.jpg", "1");
//        addBook("Yek Be Alave Yek", "JoJo Moyes", "src/photos/Yek Be Alave Yek.jpg", "src/photos/Yek Be Alave Yek.jpg", "1");
//        addComment("ali", "Man Pish Az To", "This is a very good book !");
//        addComment("ali", "Man Pish Az To", "This is a very good book !");
//        addComment("mahdi", "Man Pish Az To", "This is a very good book 1 !");
//        addComment("mahdi", "Pas Az To", "This is a very good book 2 !");
//        addCommentForBook("qwerty", "This is america1");
//        addCommentForBook("qwerty", "This is america2");
//        users.addAll(Arrays.asList(getUsers()));
//        System.out.println(elementExists2(new String[][]{{"Mahdi"}, {"London", "it's good"}, {"Tehran", "it's good"}}, new String[]{"London", "it's good"}));
//        addComment("ali123", "12344", "good1 baba good");
//        getAdminsUsers();

/////////////////////////////////////////////////////////

// Book Comment From:

//        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
//        textLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
//        textLabel.setFont(new Font(textLabel.getFont().getName(), Font.PLAIN, 14));
//        textLabel.setIconTextGap(10);
//        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//        imagesPanel.add(textLabel);

//        bookInfoPane.setText(String.format("%s\n\n%s\n\n%s", books[bookNumber][0][1], "\uD83C\uDF1F".repeat(Integer.parseInt(books[bookNumber][0][4])), commentsNum));


/////////////////////////////////////////////////////////


// Add Comment Form

//        textArea1 = new JTextArea(new DefaultStyledDocument() {
//            @Override
//            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
//                if ((getLength() + str.length()) <= maxLength) {
//                    System.out.println("ok");
//                    super.insertString(offs, str, a);
//                }
//                else {
//                    Toolkit.getDefaultToolkit().beep();
//                }
//            }
//        });