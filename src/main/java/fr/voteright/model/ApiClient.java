package fr.voteright.model;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {
    public String login(String username, String password) {
        // Exemple de requête HTTP pour login
        try {
            URL url = new URL("https://api.example.com/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String requestBody = "username=" + username + "&password=" + password;
            connection.getOutputStream().write(requestBody.getBytes());

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}