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
                     "SELECT email, pwd" +
                             " FROM bds.person p" +
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
                     "SELECT id_person, email, given_name, family_name, nickname, city, house_number, street" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address" +
                             " WHERE p.id_person = ?")) {
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

    public void removeBook(Long id) {
        String deleteBookSQL =  "DELETE FROM library.book WHERE book_id = ?";
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
                     "SELECT id_person, email, given_name, family_name, nickname, city" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address");
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

    public void createPerson(LibCreateView libCreateView) {
        String insertPersonSQL = "INSERT INTO bds.person (email, given_name, nickname, pwd, family_name) VALUES (?,?,?,?,?)";
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
        String insertPersonSQL = "UPDATE bds.person p SET email = ?, given_name = ?, nickname = ?, family_name = ? WHERE p.id_person = ?";
        String checkIfExists = "SELECT email FROM bds.person p WHERE p.id_person = ?";
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

}
