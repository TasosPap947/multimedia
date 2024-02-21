package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

import resources.AlertBox;

public class Book {
	
	private static HttpURLConnection connection;
	private static String openLibraryID;
	private static String text;
	
	public Book(String openLibraryID) {
		Book.openLibraryID = openLibraryID;
		sendRequest();
	}
	
	public String getText() {
		return Book.text;
	}
	
	public static void sendRequest() {
		//Method 1: java.net.HttpURLConnection
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://openlibrary.org/works/" + openLibraryID + ".json");
			connection = (HttpURLConnection) url.openConnection();
			
			//Request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			//System.out.println(status);
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			parse(responseContent.toString());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	
	public static String parse(String responseBody) {
		JSONObject response = new JSONObject(responseBody);
		try {
		JSONObject description = response.getJSONObject("description");
		String text = description.getString("value");
		//System.out.println(text);
		Book.text = text;
		return null;
		} catch (Exception e) {
			AlertBox.display("Error", "BookID is invalid. Try again");
			System.out.println("Invalid BookID");
			return null;
		}
		
	}
}
