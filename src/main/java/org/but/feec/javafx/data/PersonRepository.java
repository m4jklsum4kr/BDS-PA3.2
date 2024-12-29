package org.but.feec.javafx.data;

import org.but.feec.javafx.api.*;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    public PersonAuthView findPersonByEmail(String email) {
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
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public PersonDetailView findPersonDetailedView(Long personId) {
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

    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<PersonBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, email, given_name, family_name, nickname, city" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO bds.person (email, given_name, nickname, pwd, family_name) VALUES (?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getEmail());
            preparedStatement.setString(2, personCreateView.getGivenName());
            preparedStatement.setString(3, personCreateView.getNickname());
            preparedStatement.setString(4, String.valueOf(personCreateView.getPwd()));
            preparedStatement.setString(5, personCreateView.getFamilyName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "UPDATE bds.person p SET email = ?, given_name = ?, nickname = ?, family_name = ? WHERE p.id_person = ?";
        String checkIfExists = "SELECT email FROM bds.person p WHERE p.id_person = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personEditView.getEmail());
            preparedStatement.setString(2, personEditView.getGivenName());
            preparedStatement.setString(3, personEditView.getNickname());
            preparedStatement.setString(4, personEditView.getFamilyName());
            preparedStatement.setLong(5, personEditView.getId());

            try {
                // TODO set connection autocommit to false
                /* HERE */
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("pwd"));
        return person;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId(rs.getLong("id_person"));
        personBasicView.setEmail(rs.getString("email"));
        personBasicView.setGivenName(rs.getString("given_name"));
        personBasicView.setFamilyName(rs.getString("family_name"));
        personBasicView.setNickname(rs.getString("nickname"));
        personBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("id_person"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setGivenName(rs.getString("given_name"));
        personDetailView.setFamilyName(rs.getString("family_name"));
        personDetailView.setNickname(rs.getString("nickname"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.sethouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        return personDetailView;
    }

}
