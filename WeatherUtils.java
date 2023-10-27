import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherUtils {
  private static String fetchWeather() throws IOException {
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" +city +"&appid=bb10a01c853814bdc844bf02106099a6";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error fetching weather data. Response code: " + responseCode);
        }
    }

    public static void printCurrentWeather(){
        try {
            JSONObject weather = new JSONObject(fetchWeather());
            System.out.println("Қазір неше градус: " + (weather.getJSONObject("main").getInt("temp") - 273) + "℃");
            System.out.println("сезіледі : " + (weather.getJSONObject("main").getInt("feels_like") - 273) + "℃");
            System.out.println("ылҒалдылыҚ: " + weather.getJSONObject("main").getInt("humidity") + "%");
            System.out.println("Қысым: " + weather.getJSONObject("main").getInt("pressure") + " ПА");
        } catch (IOException e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printCurrentWeather();
    }
}