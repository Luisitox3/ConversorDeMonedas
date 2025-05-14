import java.util.Scanner;
import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;

public class ConversorMonedas{
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
        System.out.println("***** Conversor de monedas *****");

        String[] monedas=
                {"USD", "EUR", "GBP","JPY", "MXN","BRL"};
        boolean continuar = true;

        while (continuar) {

        System.out.println( "Selecciona la opcion de la moneda de origen" );
        for (int i = 0; i <monedas.length; i++ ){
            System.out.println((i + 1) + ". " + monedas[i]);
        }
        System.out.print("Opcion: ");
        int opcionOrigen = scanner.nextInt();
        String monedaOrigen = monedas[opcionOrigen - 1];

        System.out.print("Seleccione la opcion de la moneda de cambio");
        for (int i = 0; i < monedas.length; i++){
            System.out.println((i+1)+ ". "+ monedas[i]);
        }
        System.out.print("Opcion: ");
        int opcionDestino = scanner.nextInt();
        String monedaDestino = monedas[opcionDestino -1];

        System.out.println("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

            double tasaCambio = obtenerTasaCambio(monedaOrigen, monedaDestino);
            if (tasaCambio > 0) {
                double resultado = cantidad * tasaCambio;
                System.out.printf("%.2f %s = %.2f %s\n", cantidad, monedaOrigen, resultado, monedaDestino);
            } else {
                System.out.println("No se pudo obtener la tasa de cambio.");
            }

            System.out.print("\n¿Te gustaría hacer otra conversión? (1 Sí, 2 No): ");
            scanner.nextLine(); // limpiar buffer
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("1")) {
                continuar = false;
            }
        }

        System.out.println("Gracias! Hasta pronto!");
        scanner.close();
    }


    public static double obtenerTasaCambio(String monedaOrigen, String monedaDestino)
    {
    try {
        String url = "https://api.exchangerate-api.com/v4/latest/" + monedaOrigen;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String cuerpo = response.body();

        JSONObject json = new JSONObject(cuerpo);
        double tasa = json.getJSONObject("rates").getDouble(monedaDestino);

        return tasa;
    } catch (Exception e) {
        System.out.println("Error al obtener tasa de cambio: " + e.getMessage());
        return -1;
        //Final

    }
    }

    }

