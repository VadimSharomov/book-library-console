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
            dbConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Unable to connect to data base! \nCheck data base url, user, password", e.getMessage());
            System.exit(1);
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
    public int addBook(String nameBook, String author) {
        connectToBase();
        int result = 0;
        StringBuilder query = new StringBuilder("INSERT INTO ").append(table).append(" (name, author) VALUES (?,?)");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, nameBook);
            preparedStatement.setString(2, author);
            result = preparedStatement.executeUpdate();
            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in addBook", e.getMessage());
        } finally {
            closeConnect();
        }
        return result;
    }

    @Override
    public int updateBook(long oldBookId, String newNameBook) {
        connectToBase();
        int result = 0;
        StringBuilder query = new StringBuilder("UPDATE ").append(table).append(" SET name = ? WHERE id = ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, newNameBook);
            preparedStatement.setLong(2, oldBookId);
            result = preparedStatement.executeUpdate();
            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in updateBook", e.getMessage());
        } finally {
            closeConnect();
        }
        return result;
    }

    @Override
    public int removeBook(long bookId) {
        connectToBase();
        int result = 0;
        StringBuilder query = new StringBuilder("DELETE FROM ").append(table).append(" WHERE id = ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setLong(1, bookId);
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
                listAllBook.add(new Book(rs.getLong("id"), rs.getString("name"), rs.getString("author")));
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
                listAllBook.add(new Book(rs.getLong("id"), rs.getString("name"), rs.getString("author")));
            }

            closeConnect();
        } catch (SQLException e) {
            logger.error("SQLException in getBookByName", e.getMessage());
        } finally {
            closeConnect();
        }
        return listAllBook;
    }

    @Override
    public List<Book> getBookLikeName(String bookName) {
        connectToBase();
        List<Book> listAllBook = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE name LIKE ?");
        try {
            preparedStatement = dbConnection.prepareStatement(query.toString());
            preparedStatement.setString(1, "%" + bookName + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listAllBook.add(new Book(rs.getLong("id"), rs.getString("name"), rs.getString("author")));
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
