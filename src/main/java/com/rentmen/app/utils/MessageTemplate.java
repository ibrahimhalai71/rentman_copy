package com.rentmen.app.utils;

public class MessageTemplate {
    public MessageTemplate() {
    }

    public static String getMessageTemplate(String workerName, String endDateString, String endTimeString, String location, String TinyURL) {
//        return "Project " + projectName + " is bevestigd! We verwachten je " + endDateString + " om " + endTimeString + " op locatie: " + location + " : \n" + TinyURL + "\nDit is een NOREPLY bericht.";
//        return "Hoi " + worker + ",\n\n" +
//                "Wil je je gewerkte uren van vandaag (" + endDateString + ") invullen?\n" +
//                "Locatie: " + location + "\n\n" +
//                "Klik hier om je uren door te geven:\n" + TinyURL + "\n\n" +
//                "Groet,\nEventico";

        return "Hoi " + workerName + ",\n" +
                "Wil je je gewerkte uren van vandaag (" + endDateString + ") invullen?\n" +
                "Locatie: " + location + "\n" +
                "---\n" +
                "Klik hier om je uren door te geven:\n" + TinyURL + "\n" +
                "---\n" +
                "Groet, Eventico";


    }
}
