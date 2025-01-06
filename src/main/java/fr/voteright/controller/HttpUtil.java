package fr.voteright.controller;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {

    private static final CookieManager COOKIE_MANAGER = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .cookieHandler(COOKIE_MANAGER) // Associe le gestionnaire de cookies
            .build();

    /**
     * Effectue une requête GET.
     *
     * @param url L'URL de la requête.
     * @return La réponse sous forme de chaîne de caractères.
     * @throws Exception En cas d'erreur.
     */
    public static String get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json") // Indique qu'on attend une réponse JSON
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    /**
     * Effectue une requête POST avec un corps JSON.
     *
     * @param url L'URL de la requête.
     * @param jsonBody Le corps de la requête au format JSON.
     * @return La réponse sous forme de chaîne de caractères.
     * @throws Exception En cas d'erreur.
     */
    public static String post(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json") // Indique que le corps est JSON
                .header("Accept", "application/json")       // Indique qu'on attend une réponse JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    /**
     * Effectue une requête PATCH avec un corps JSON.
     *
     * @param url L'URL de la requête.
     * @param jsonBody Le corps de la requête au format JSON.
     * @return La réponse sous forme de chaîne de caractères.
     * @throws Exception En cas d'erreur.
     */
    public static String patch(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json") // Indique que le corps est JSON
                .header("Accept", "application/json")       // Indique qu'on attend une réponse JSON
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    /**
     * Effectue une requête PUT avec un corps JSON.
     *
     * @param url L'URL de la requête.
     * @param jsonBody Le corps de la requête au format JSON.
     * @return La réponse sous forme de chaîne de caractères.
     * @throws Exception En cas d'erreur.
     */
    public static String put(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    /**
     * Effectue une requête DELETE.
     *
     * @param url L'URL de la requête.
     * @return La réponse sous forme de chaîne de caractères.
     * @throws Exception En cas d'erreur.
     */
    public static String delete(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    /**
     * Ajoute un cookie personnalisé au gestionnaire de cookies.
     *
     * @param url L'URL associée au cookie.
     * @param name Nom du cookie.
     * @param value Valeur du cookie.
     */
    public static void addCookie(String url, String name, String value) {
        CookieStore cookieStore = COOKIE_MANAGER.getCookieStore();
        HttpCookie cookie = new HttpCookie(name, value);
        cookie.setDomain(URI.create(url).getHost());
        cookie.setPath("/");
        cookieStore.add(URI.create(url), cookie);
    }

    /**
     * Liste et affiche les cookies actuellement stockés dans le CookieManager.
     */
    public static void listCookies() {
        List<HttpCookie> cookies = COOKIE_MANAGER.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("Aucun cookie trouvé.");
        } else {
            System.out.println("Cookies enregistrés :");
            for (HttpCookie cookie : cookies) {
                System.out.println("Nom: " + cookie.getName() + ", Valeur: " + cookie.getValue());
            }
        }
    }

        /**
         * Gère la réponse et lance une exception en cas de code HTTP non valide.
         *
         * @param response La réponse HTTP.
         * @return Le corps de la réponse sous forme de chaîne.
         * @throws Exception Si le code HTTP est en dehors de 200-299.
         */
    private static String handleResponse(HttpResponse<String> response) throws Exception {
        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            return response.body();
        } else {
            throw new Exception("Requête échouée avec le statut HTTP : " + statusCode + "\nCorps : " + response.body());
        }
    }
}
