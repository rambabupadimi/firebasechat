package com.example.ramu.chatfirebase.EventAdmin;

/**
 * Created by Ramu on 07-06-2017.
 */

public class CreateEvent {
    String  eventTitle,
            eventDescription,
            eventStartDate,
            eventEndDate,
            eventOrganizers,
            eventGuests,
            eventLocation;


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
