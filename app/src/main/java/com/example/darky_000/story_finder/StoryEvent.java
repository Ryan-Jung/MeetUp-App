package com.example.darky_000.story_finder;

/**
 * Created by darky_000 on 12/4/2016.
 */
//The API Meetup Link is this
//https://api.meetup.com/find/events?key=783e741d1d3316397c7e3b2b175025b
public class StoryEvent {

    private int created;
    private int duration;
    private int id;
    private String name;
    private int rsvp_limit;
    private String status;
    private int time;
    private int updated;
    private int utc;
    private int waitlist;
    private int rsvp_count;

    private StoryEvent(){

    }

    public StoryEvent(int created, int duration, int id, String name, int rsvp_limit,
                      String status, int time, int updated, int utc, int waitlist,
                      int rsvp_count){
        this.created = created;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.rsvp_limit = rsvp_limit;
        this.status = status;
        this.time = time;
        this.updated = updated;
        this.utc = utc;
        this.waitlist = waitlist;
        this.rsvp_count = rsvp_count;

    }

    @Override
    public String toString(){
        return ("\nCreated: " + created + "\nDuration: " + duration + "\nID: " + id + "\nName: " +
                name + "\nRSVP limit: " + rsvp_limit + "\nStatus: " + status + "\nTime of Event: " +
                time + "\nUpdate: " + updated + "\nUTC: " + utc + "\nWaitlist: " + waitlist +
                "\nRSVP Count: " + rsvp_count);
    }

    public class Venue {

        private int id;
        private String name;
        private int rsvp_limit;
        private double latitude;
        private double longnitude;
        private boolean repinned;
        private String address;
        private String city;
        private String country;
        private String local_country;
        private String zip;
        private String state;


        private Venue() {
        }
        @Override
        public String toString(){
            return ("ID: " + id + "\nName: " + name + "\nID: " + id + "\nName: "
                    + name + "\nRSVP limit: " + rsvp_limit + "\nStatus: " + status +
                    "\nTime of Event: " + time + "\nUpdate: " + updated + "\nUTC: " + utc +
                    "\nWaitlist: " + waitlist + "\nRSVP Count: " + rsvp_count);
        }


    }

}


