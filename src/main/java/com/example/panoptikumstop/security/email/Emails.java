package com.example.panoptikumstop.security.email;


public class Emails {


    public static String PasswordResetMassage(String name, String link) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Kicken sie auf den Link um die Password zurück zusetzten&nbsp;</p>\n" +
                "<p> " + link + "</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");
    }

    public static String buildRegistrationEmail(String name) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Wir freuen uns sehr, dass sie bei angemeldet haben.&nbsp;</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");

    }


    public static String InfoEmail(String name) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Sie haben den Password zurückgesetzt.&nbsp;</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");
    }
}
