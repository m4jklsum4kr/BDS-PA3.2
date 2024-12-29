package org.but.feec.javafx.services;

import org.but.feec.javafx.api.MeetingView;
import org.but.feec.javafx.data.MeetingRepository;

import java.util.List;

public class MeetingService {

    private MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<MeetingView> getAllMeetings() {
        return this.meetingRepository.getMeetingsView();
    }
}
