package org.but.feec.javafx.data;

import org.but.feec.javafx.api.MeetingView;
import org.but.feec.javafx.api.PersonBasicView;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    public List<MeetingView> getMeetingsView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_meeting, note, place, start_time, end_time FROM bds.meeting");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<MeetingView> meetingViews = new ArrayList<>();
            while (resultSet.next()) {
                meetingViews.add(mapToMeetingView(resultSet));
            }
            return meetingViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    private MeetingView mapToMeetingView(ResultSet rs) throws SQLException {
        MeetingView meetingView = new MeetingView();
        meetingView.setId(rs.getLong("id_meeting"));
        meetingView.setNote(rs.getString("note"));
        meetingView.setPlace(rs.getString("place"));
        meetingView.setStart(rs.getString("start_date"));
        meetingView.setEnd(rs.getString("end_date"));
        return meetingView;
    }
}
