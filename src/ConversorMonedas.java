import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;

public class ConversorMonedas {
    public static double obtenerTasaCambio(String monedaOrigen, String monedaDestino) {
        try {
            String url = "https://api.exchangerate-api.com/v4/latest/" + monedaOrigen;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String cuerpo = response.body();

            JSONObject json = new JSONObject(cuerpo);
            return json.getJSONObject("rates").getDouble(monedaDestino);
        } catch (Exception e) {
            System.out.println("Error al obtener tasa de cambio: " + e.getMessage());
            return -1;
        }
    }
}
