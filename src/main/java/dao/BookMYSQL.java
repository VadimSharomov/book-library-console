package dao;

import entity.Book;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vadim Sharomov
 */
public class BookMYSQL implements BookDAO {
    private final static Logger logger = getLogger(BookMYSQL.class);
    private String url;
    private String user;
    private String password;
    private String table;
    private Connection dbConnection = null;
    private PreparedStatement preparedStatement = null;

    public BookMYSQL(String url, String user, String password, String table) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.table = table;
    }

    private void connectToBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("ClassNotFoundException or SQLException in connectToBase", e.getMessage());
        }

    }

    private void closeConnect() {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (dbConnection != null) dbConnection.close();
        } catch (SQLException e) {
            logger.error("SQLException in closeConnect", e.getMessage());
        }
    }

    @Override
    public void addBook(String nameBook, String author) {
        connectToBase();
        StringBuilder query = new StringBuilder("INSERT INTO ").append(table).append(" (name, author) VALUES (?,?)");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, nameBook);
            preparedStatement.setString(2, author);
            preparedStatement.executeUpdate();
            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in addBook", e.getMessage());
        } finally {
            closeConnect();
        }
    }

    @Override
    public void updateBook(String oldNameBook, String newNameBook) {
        connectToBase();
        StringBuilder query = new StringBuilder("UPDATE ").append(table).append(" SET name = ? WHERE name = ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, newNameBook);
            preparedStatement.setString(2, oldNameBook);
            preparedStatement.executeUpdate();
            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in updateBook", e.getMessage());
        } finally {
            closeConnect();
        }
    }

    @Override
    public int removeBook(String bookName) {
        connectToBase();
        int result = 0;
        StringBuilder query = new StringBuilder("DELETE FROM ").append(table).append(" WHERE name = ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, bookName);
            result = preparedStatement.executeUpdate();
            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in removeBook", e.getMessage());
        } finally {
            closeConnect();
        }
        return result;
    }


    @Override
    public List<Book> allBooks() {
        connectToBase();
        List<Book> listAllBook = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(table);
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            ResultSet rs = preparedStatement.executeQuery(query.toString());
            while (rs.next()) {
                listAllBook.add(new Book(rs.getString("name"), rs.getString("author")));
            }

            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in allBooks", e.getMessage());
        } finally {
            closeConnect();
        }
        return listAllBook;
    }

    @Override
    public List<Book> getBookByName(String bookName) {
        connectToBase();
        List<Book> listAllBook = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE name = ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, bookName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listAllBook.add(new Book(rs.getString("name"), rs.getString("author")));
            }

            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in getBookByName", e.getMessage());
        } finally {
            closeConnect();
        }
        return listAllBook;
    }
}
