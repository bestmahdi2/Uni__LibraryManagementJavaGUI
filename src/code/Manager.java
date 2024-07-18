package code;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.twmacinta.util.MD5;

/**
 * package,
 * Import needed libraries, com.twmacinta.util.MD5 is for hashing user password.
 */
public class Manager {
    /**
     * Initialize allUsers: An Arraylist to keep all users [username, hashed password, name, lastname, privilege]
     * Initialize allBooks: An Arraylist to keep all books info = [name, author, currentAddress, oldAddress, recommendedTimes], comments = [], usernameWhoCommented = [] > all in order.
     * Initialize username: The username of user who logins to system.
     * Initialize loginForm: LoginFrom object > to show login form.
     */
    static ArrayList<String[]> allUsers = new ArrayList<>();
    static ArrayList<String[][]> allBooks = new ArrayList<>();
    private static String username;
    public static LoginForm loginForm;
    private static final String version = "1.1.0";

    /**
     * main method >
     * fillUsers(): fill allUsers with UsersDB database information,
     * fillBooks(): fill allBooks with BooksDB database information.
     * create a new object and sets to loginForm > show login form
     */
    public static void main(String[] args) {
        fillUsers();
        fillBooks();

        loginForm = new LoginForm();

        // Defaults:
//        signUp("alib", "1234", "Ali", "Badiee", "admin");
//        signUp("user", "1234", "User", "Useri", "user");
//        addBook("Man Pish Az To", "JoJo Moyes", "src/photos/Man Pish Az To.jpg", "src/photos/Man Pish Az To.jpg", "0");
//        addBook("Pas Az To", "JoJo Moyes", "src/photos/Pas Az To.jpg", "src/photos/Pas Az To.jpg", "0");
//        addBook("Yek Be Alave Yek", "JoJo Moyes", "src/photos/Yek Be Alave Yek.jpg", "src/photos/Yek Be Alave Yek.jpg", "0");

//        System.out.println(Arrays.deepToString(allUsers.toArray()));
//        System.out.println(Arrays.deepToString(allBooks.toArray()));
    }

    /**
     * getter method for getting version
     *
     * @return A string value of versin of the app
     */
    public static String getVersion(){
        return version;
    }

    /**
     * Setter method for username
     *
     * @param username The username of user who logins to system.
     */
    public static void setUsername(String username) {
        Manager.username = username;
    }

    /**
     * Getter method for username
     *
     * @return A string value of username logged in to system
     */
    public static String getUsername() {
        if (username != null)
            return username;
        else return "Test";
    }

    /**
     * getAdminsUsers method for getting admin and users information
     *
     * @return A 3d array of [admin & users, admins, users]
     */
    public static String[][][] getAdminsUsers() {
        /*
        Initialize 2d arrays for all, users, admins > allCount: number of users and admins together, adminCount: number of admins
         */
        String[][] all = {}, users, admins;
        int allCount = 0;
        int adminCount = 0;

        /*
        Connect to UsersDB database and get all
         */
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("src/databases/UsersDB.txt"));
            all = (String[][]) inputStream.readObject();

        } catch (Exception ignored) { // ignore all Exceptions
        }

        // foreach loop to count admins and all
        for (String[] user : all) {
            if (Objects.equals(user[4], "admin")) {
                adminCount++;
            }
            allCount++;
        }

        // initialize arrays for admin and users
        admins = new String[adminCount][];
        users = new String[allCount - adminCount][];

        // foreach loop to find admin and users and add them into their privilege
        for (String[] user : all) {
            if (Objects.equals(user[4], "admin")) { // this person is admin
                for (int i = 0; i < admins.length; i++) {
                    if (admins[i] == null) {
                        admins[i] = user;
                        break;
                    }
                }
            } else { // this person is user
                for (int i = 0; i < users.length; i++) {
                    if (users[i] == null) {
                        users[i] = user;
                        break;
                    }
                }
            }

        }

        return new String[][][]{all, admins, users};

    }

    /**
     * fillUsers(): fill allUsers with UsersDB database information,
     */
    public static void fillUsers() {
        allUsers = new ArrayList<>(); // create an empty array list for allUsers
        allUsers.addAll(Arrays.asList(getAdminsUsers()[0])); // convert 2d Array to arraylist<String[]>
    }

    /**
     * loginCheck method for check if credentials user entered matches anyone
     *
     * @param username The username user entered
     * @param password The password user entered
     * @return A boolean to say if user credentials were correct or not
     */
    public static boolean loginCheck(String username, String password) {
        fillUsers(); // fill allUsers

        // create new object from MD5 > used to hash password
        MD5 md5 = new MD5();

        /*
        hash: keep hashed password
        users_essentials: get all users information
         */
        String hash;
        String[][] users_essentials = Manager.getAdminsUsers()[0];

        //check if password is hashable, if not return false
        try {
            md5.Update(password, null);
            hash = md5.asHex();
        } catch (UnsupportedEncodingException ignored) {
            return false;
        }

        //foreach loop to find username, if username and password matches anyone > return true
        for (String[] user : users_essentials) {
            if (user[0].equals(username) && user[1].equals(hash))
                return true;
        }

        return false;
    }

    /**
     * signUp method for signing in a new user
     *
     * @param username  string value of username user entered
     * @param password  string value of password user entered
     * @param firstName string value of first name user entered
     * @param lastName  string value of last name user entered
     * @param mode      string value of user privilege
     */
    public static void signUp(String username, String password, String firstName, String lastName, String mode) {
        fillUsers();

        // create new object from MD5 > used to hash password
        MD5 md5 = new MD5();

        /*
        hash: keep hashed password
        users_essentials: get all users information
         */
        String hash = "";

        //check if password is hashable, if not return false
        try {
            md5.Update(password, null);
            hash = md5.asHex();
        } catch (UnsupportedEncodingException ignored) {
        }

        // add this new user to allUsers
        allUsers.add(new String[]{username, hash, firstName, lastName, mode});

        // convert allUsers from arraylist to 2d array for saving
        String[][] saver = allUsers.toArray(new String[0][]);

        try { // save to database
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/databases/UsersDB.txt"));
            outputStream.writeObject(saver);
        } catch (Exception ignored) {
        }
    }

    /**
     * promoteToAdmin method for promote a user to admin
     *
     * @param username Username of user whom to change to admin
     */
    public static void promoteToAdmin(String username) {
        fillUsers();

        // search for username and change it to admin
        for (int i = 0; i < allUsers.size(); i++) {
            if (Objects.equals(allUsers.get(i)[0], username)) {
                allUsers.set(i, new String[]{allUsers.get(i)[0], allUsers.get(i)[1], allUsers.get(i)[2], allUsers.get(i)[3], "admin"});
            }
        }

        // convert allUsers from arraylist to 2d array for saving
        String[][] saver = allUsers.toArray(new String[0][]);

        try { // save to database
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/databases/UsersDB.txt"));
            outputStream.writeObject(saver);
        } catch (Exception ignored) {
        }
    }

    /**
     * getBooks method for get all books
     *
     * @return A 3d string array of all books in database
     */
    public static String[][][] getBooks() {
        // initialize temp 3d string array for books
        String[][][] books = {};

        // connect to database and get books
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("src/databases/BooksDB.txt"));
            books = (String[][][]) inputStream.readObject();

        } catch (Exception ignored) {
        }

        return books;
    }

    /**
     * fillBooks method for fill allBooks with books information
     */
    public static void fillBooks() {
        allBooks = new ArrayList<>(); // create an empty array list for allBooks
        allBooks.addAll(Arrays.asList(getBooks())); // convert 3d Array to arraylist<String[][]>
    }

    /**
     * getPhotosAddresses method for getting all books current address
     *
     * @return A string array of books photo addresses
     */
    public static String[] getPhotosAddresses() {
        fillBooks();

        // initialize a string array for keeping addresses
        String[] addresses = new String[allBooks.size()];

        // foreach loop to find each book address and append to Addresses
        int x = 0;
        for (String[][] book : allBooks) {
            if (new File(book[0][2]).isFile()) // check if current photo is available
                addresses[x] = book[0][2];
            else if (new File(book[0][3]).isFile()) // check if old photo is available
                addresses[x] = book[0][3];
            else addresses[x] = null; // if none of them were available
            x++;
        }

        return addresses;
    }

    /**
     * addBook method for add a new book to database
     *
     * @param name            String value of name of the book
     * @param author          String value of author of the book
     * @param newPhotoAddress String value of current book photo address
     * @param oldPhotoAddress String value of old book photo address
     * @param recommend       String value of how many people recommend this book
     */
    public static void addBook(String name, String author, String newPhotoAddress, String oldPhotoAddress, String recommend) {
        fillBooks();

        // add a new 2d array to allBooks
        allBooks.add(new String[][]{{name, author, newPhotoAddress, oldPhotoAddress, recommend}, {}, {}});

        // convert allBooks from arraylist to 3d String array
        String[][][] saver = allBooks.toArray(new String[0][][]);

        try { // connect to database and replace books
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/databases/BooksDB.txt"));
            outputStream.writeObject(saver);
        } catch (Exception ignored) {
        }
    }

    /**
     * getUsersActivity method for getting all users activities
     *
     * @return A 3d string array of users activities > comments on every book
     */
    public static String[][][] getUsersActivity() {
        String[][][] usersInfo;
        try { // connect to database and get all information
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("src/databases/UsersInfoDB.txt"));
            usersInfo = (String[][][]) inputStream.readObject();
            inputStream.close();

        } catch (Exception e) { // if not successful
            usersInfo = new String[][][]{{{}}};
        }
        return usersInfo;
    }

    /**
     * getUserComments method for finding all user comments on a book
     *
     * @param username A string value of username of the user whom to find
     * @param book     A string value of name of the book to find
     * @return A string value of books info in table
     */
    public static String getUserComments(String username, String book) {
        // get all users information
        String[][][] usersInfo = getUsersActivity();
        // initialize a 2d array for the user we're looking for
        String[][] userinfo = new String[1][];
        // initialize a array list for comment of the user
        ArrayList<String> array = new ArrayList<>();

        // foreach loop to find user activities by username
        for (String[][] user : usersInfo) {
            if (Objects.equals(user[0][0], username)) {
                userinfo = user;
                break;
            }
        }

        // foreach loop to find user comments and add to array list
        for (String[] user : userinfo) {
            if (user.length > 1 && Objects.equals(user[0], book)) {
                array.add(user[1]);
            }
        }
        return String.join("\n-----------\n", array);
    }

    /**
     * addComment method for adding a new comment > to user and to book
     *
     * @param username  string value of username of user
     * @param book      string value of book name
     * @param comment   string value of comment user typed
     * @param recommend boolean value > if this username recommends this book or not
     * @return A boolean value of if comment was added and wasn't duplicated
     */
    public static boolean addComment(String username, String book, String comment, boolean recommend) {
        addCommentForUser(username, book, comment); // added to users
        return addCommentForBook(username, book, comment, recommend); // add to books and return value
    }

    /**
     * addCommentForUser method for adding a comment of a book for user
     *
     * @param username string value of username of user
     * @param book     string value of book name
     * @param comment  string value of comment user typed
     */
    private static void addCommentForUser(String username, String book, String comment) {
        fillUsers();

        // usersInfo to get all users activities and comments
        String[][][] usersInfo = getUsersActivity();

        String[][] oldInfo;
        found:
        { // get old info of that user and sets it to 2d array oldInfo (comments)
            if (usersInfo[0][0].length != 0) {
                for (String[][] user : usersInfo) {
                    if (Objects.equals(user[0][0], username)) {
                        oldInfo = user;
                        break found;
                    }
                }
            }
            oldInfo = new String[][]{{username}};
        }

        // make a new array to be added to comments of user if not already in there
        String[] newInfo = {book, comment};

        /*
        if newInfo not in oldInfo already, it makes a new array and sets old info + new info to it,
        it adds to user information and then update database
        */

        if (!elementExists2(oldInfo, newInfo)) {
            String[][] mergedArray = new String[oldInfo.length + 1][]; // to merge old and new arrays
            System.arraycopy(oldInfo, 0, mergedArray, 0, oldInfo.length); // copy
            mergedArray[oldInfo.length] = newInfo; // add newInfo to end of the mergedArray

            if (usersInfo[0][0].length != 0) { // if userInfo comments were not empty
                found:
                { // find userinfo in all users, and sets its merged array to it
                    for (int i = 0; i < usersInfo.length; i++) {
                        if (Objects.equals(usersInfo[i][0][0], username)) {
                            usersInfo[i] = mergedArray;
                            break found;
                        }
                    }
                    String[][][] temp = new String[usersInfo.length + 1][][]; // copy all usersInfo to temp, and sets merged to it, then change to usersInfo
                    System.arraycopy(usersInfo, 0, temp, 0, usersInfo.length);
                    temp[usersInfo.length] = mergedArray;
                    usersInfo = temp;
                }
            } else {
                usersInfo[0] = mergedArray;
            }

            try { // save usersInfo to database
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/databases/UsersInfoDB.txt"));
                outputStream.writeObject(usersInfo);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * addCommentForBook method for adding a comment to book
     *
     * @param username  string value of username of user
     * @param book      string value of book name
     * @param comment   string value of comment user typed
     * @param recommend boolean value > if this username recommends this book or not
     * @return A string value of books info in table
     */
    private static boolean addCommentForBook(String username, String book, String comment, boolean recommend) {
        fillBooks();

        // boolean value to be checked if comment was added or not
        boolean commentAdded = false;

        for (int i = 0; i < allBooks.size(); i++) {
            if (Objects.equals(allBooks.get(i)[0][0], book)) {
                boolean continuee = true;

                // check in all users to find user, if same comment was already made by this username, doesn't continue
                for (int j = 0; j < allBooks.get(i)[1].length; j++) {
                    if (Objects.equals(allBooks.get(i)[1][j], comment) && Objects.equals(allBooks.get(i)[2][j], username)) {
                        continuee = false;
                        break;
                    }
                }

                if (continuee) { //if same comment wasn't duplicated by this username, continues
                    // comments array with a new index
                    String[] comments = new String[allBooks.get(i)[1].length + 1];

                    // copy all old comments to comments array
                    if (allBooks.get(i)[1].length > 0)
                        System.arraycopy(allBooks.get(i)[1], 0, comments, 0, allBooks.get(i)[1].length);

                    // add new comment to comments
                    comments[allBooks.get(i)[1].length] = comment;

                    // add new user to books (this user made that comment)
                    String[] users = new String[allBooks.get(i)[2].length + 1];

                    // copy all old users to users array
                    if (allBooks.get(i)[2].length > 0)
                        System.arraycopy(allBooks.get(i)[2], 0, users, 0, allBooks.get(i)[2].length);

                    // add new user to users
                    users[allBooks.get(i)[2].length] = username;

                    // if this book was recommended by this user, add 1 to recommends of this book
                    if (recommend)
                        allBooks.get(i)[0][4] = String.valueOf(Integer.parseInt(allBooks.get(i)[0][4]) + 1);

                    // update book in allBooks with new comment
                    allBooks.set(i, new String[][]{allBooks.get(i)[0], comments, users});

                    commentAdded = true; // comment is going to be added
                }
            }
        }

        String[][][] saver = allBooks.toArray(new String[0][][]);

        try { // save change to database
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/databases/BooksDB.txt"));
            outputStream.writeObject(saver);
        } catch (
                Exception ignored) {
        }

        return commentAdded;
    }

    /**
     * isAlpha method for checking if this string is all alphabetical or not
     *
     * @param str String value to be checked
     * @return A boolean value of if string was all alphabetical English / Persian
     */
    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }

        String persian = "ضصثقفغعهخحجچپشسیبلآاتنمکگظطزرذدئوژ" + " ";

        for (int i = 0; i < str.length(); i++) { // between A-Z or a-z
            char character = str.charAt(i);
            if (character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || persian.contains(String.valueOf(character))) {
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * elementExists3 method for checking if 2d array is in 3d array
     *
     * @param bigger  A parent 3d array
     * @param smaller A child 2d array
     * @return A boolean value of if child was in parent
     */
    public static boolean elementExists3(String[][][] bigger, String[][] smaller) {
        if (bigger[0][0].length == 0 || smaller[0].length == 0)
            return false;

        for (String[][] small : bigger) {
            if (Objects.equals(small[0][0], smaller[0][0])) {
                int match = 0;
                for (int i = 1; i < small.length; i++) {
                    for (int j = 0; j < small[i].length; j++) {
                        if (Objects.equals(small[i][j], smaller[i][j]))
                            match++;
                    }
                }
                if (match == smaller.length)
                    return true;
            }
        }
        return false;
    }

    /**
     * elementExists2 method for checking if 1d array is in 2d array
     *
     * @param bigger  A parent 2d array
     * @param smaller A child 1d array
     * @return A boolean value of if child was in parent
     */
    public static boolean elementExists2(String[][] bigger, String[] smaller) {
        if (bigger[0].length == 0 || smaller.length == 0)
            return false;

        for (String[] small : bigger) {
            try {
                if (Objects.equals(small[0], smaller[0])) {
                    int match = 0;
                    for (int i = 0; i < small.length; i++) {
                        if (Objects.equals(small[i], smaller[i]))
                            match++;
                    }
                    if (match == smaller.length)
                        return true;
                }
            } catch (Exception NullPointerException) {
                return false;
            }
        }
        return false;
    }
}