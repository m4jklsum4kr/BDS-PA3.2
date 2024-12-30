package org.but.feec.javafx.data;

import org.but.feec.javafx.api.*;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibRepository {

    public LibAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password" +
                             " FROM bookstore.users p" +
                             " WHERE p.email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by email with addresses failed.", e);
        }
        return null;
    }

    public LibDetailView findPersonDetailedView(Long personId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT u.*, up.profile_data" +
                             " FROM bookstore.users u" +
                             " LEFT JOIN bookstore.user_profile up ON u.user_id = up.user_id" +
                             " WHERE u.id_person = ?")) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    //TODO: no usages najit kde to vazne

    public void removeBook(Long id) {
        String deleteBookSQL =  "DELETE FROM bookstore.books WHERE book_id = ?";
        System.out.println(deleteBookSQL);
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement prpstmt = connection.prepareStatement(deleteBookSQL)){

            prpstmt.setLong(1, id);
            prpstmt.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("failed");
        }

    }
    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<LibBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT book_id, title, author_name, publication_date " +
                             " FROM bookstore.books p" +
                             " LEFT JOIN bookstore.book_authors a ON p.book_id = a.book_id");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<LibBasicView> libBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                libBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return libBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public List<InjectionView> getInjectionView(String input){
        String query = "SELECT user_id,username,first_name,last_name  from bookstore.users u where u.user_id =" + input ;
        // 1; DROP TABLE injection.user;--
        // 1 OR 1=1
        try (Connection connection = DataSourceConfig.getConnection();
             Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(query)) {
            List<InjectionView> injectionViews = new ArrayList<>();
            System.out.println(statement);
            while (resultSet.next()) {

                injectionViews.add(mapToInjectionView(resultSet));
            }
            return injectionViews;
        } catch (SQLException e) {
            throw new DataAccessException("Find all users SQL failed.", e);
        }


    }

    // proc to je tady sakra šedý

    public void createPerson(LibCreateView libCreateView) {
        String insertPersonSQL = "INSERT INTO bookstore.books(isbn, title, author_name, genre, publisher) VALUES (?,?,?,?,?)";
        String insertAuthorSQL = "INSERT INTO bookstore.book_authors(book_id, author_id) VALUES (?,?)";
        String insertConnectionSQL = "Insert into bookstore.book_author(book_book_id, author_author_id) values ((SELECT book_id FROM bookstore.books WHERE book_isbn = ?)," +
                " (SELECT author_id FROM bookstore.authors WHERE author_name = ?))";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setLong(1, libCreateView.getIsbn());
            preparedStatement.setString(2, libCreateView.getTitle());
            preparedStatement.setString(3, libCreateView.getAuthor_name());
            preparedStatement.setString(4, String.valueOf(libCreateView.getGenre()));
            preparedStatement.setString(5, libCreateView.getPublisher());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating book failed operation on the database failed.");
        }
    }

    public void editPerson(LibEditView libEditView) {
        String insertPersonSQL =
                "begin;" +
                "UPDATE bookstore.books b SET isbn = ?, title = ?, author_name = ?, publisher = ? WHERE b.book.id = ?" +
                "UPDATE bookstore.authors a SET author_name = ? WHERE a.author_id = (SELECT ba.author.id FROM bookstore.book_authors ba WHERE ba.book_id = ?) ";

        String checkIfExists = "SELECT isbn FROM bookstore.books b WHERE b.book_id = ?";

        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setLong(1, libEditView.getIsbn());
            preparedStatement.setString(2, libEditView.getTitle());
            preparedStatement.setString(3, libEditView.getAuthor_name());
            preparedStatement.setString(4, libEditView.getPublisher());
            preparedStatement.setLong(5, libEditView.getId());

            try {

                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, libEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This book for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating book failed, no rows affected.");
                }
                System.out.println(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating book failed operation on the database failed.");
        }
    }


    private LibAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        LibAuthView person = new LibAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("pwd"));
        return person;
    }

    private LibBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        LibBasicView libBasicView = new LibBasicView();
        libBasicView.setId(rs.getLong("id_person"));
        libBasicView.setIsbn(rs.getLong("isbn"));
        libBasicView.setTitle(rs.getString("title"));
        libBasicView.setAuthor_name(rs.getString("Author name"));
        libBasicView.setPublisher(rs.getString("publisher"));
        return libBasicView;
    }

    private LibDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        LibDetailView libDetailView = new LibDetailView();
        libDetailView.setId(rs.getLong("id_person"));
        libDetailView.setIsbn(rs.getLong("isbn"));
        libDetailView.setTitle(rs.getString("Title"));
        libDetailView.setAuthor_name(rs.getString("Author name"));
        libDetailView.setGenre(rs.getString("genre"));
        libDetailView.setPublisher(rs.getString("Publisher"));
        libDetailView.setPublication_date(rs.getString("Publication date"));
        return libDetailView;
    }
    private InjectionView mapToInjectionView(ResultSet rs ) throws  SQLException{
        InjectionView injectionView = new InjectionView();
        injectionView.setId(rs.getLong("id"));
        injectionView.setUsername(rs.getString("username"));
        injectionView.setFirst_name(rs.getString("First name"));
        injectionView.setLast_name(rs.getString("Last_name"));
        return injectionView;
    }

}
