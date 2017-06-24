package com.example.ramu.chatfirebase.EventAdmin;

import java.io.Serializable;

/**
 * Created by Ramu on 07-06-2017.
 */

public class CreateEvent implements Serializable {
    String  eventTitle,
            eventDescription,
            eventStartDate,
            eventEndDate,
            eventOrganizers,
            eventGuests,
            eventLocation,
            eventPosterURL,
    eventUserId;

    public String getEventUserId() {
        return eventUserId;
    }

    public void setEventUserId(String eventUserId) {
        this.eventUserId = eventUserId;
    }

    public String getEventPosterURL() {
        return eventPosterURL;
    }

    public void setEventPosterURL(String eventPosterURL) {
        this.eventPosterURL = eventPosterURL;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventOrganizers() {
        return eventOrganizers;
    }

    public void setEventOrganizers(String eventOrganizers) {
        this.eventOrganizers = eventOrganizers;
    }

    public String getEventGuests() {
        return eventGuests;
    }

    public void setEventGuests(String eventGuests) {
        this.eventGuests = eventGuests;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
