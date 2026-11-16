package com.rentmen.app.utils;

public class MessageTemplate {
    public MessageTemplate() {
    }

    public static String getMessageTemplate(String projectName, String endDateString, String endTimeString, String location, String TinyURL) {
        return "Project " + projectName + " is bevestigd! We verwachten je " + endDateString + " om " + endTimeString + " op locatie: " + location + " : \n" + TinyURL + "\nDit is een NOREPLY bericht.";
    }
}
