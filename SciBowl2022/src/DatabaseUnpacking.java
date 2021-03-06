import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.JDA;

public class DatabaseUnpacking {

	public static JDA jda;
	public int helloint = 0;

	private final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();
	
	//Method that returns a JSONObject with all of the information for a single question
		//To get a specific thing from the JSON file, call (JSONObject Name).get("(Name of thing ie. question)");
		public JSONObject sendGet() throws Exception {
			HttpRequest request = HttpRequest.newBuilder()
					.GET()
					.uri(URI.create("https://scibowldb.com/api/questions/random"))
					.setHeader("User-Agent", "Java 11 HttpClient Bot")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			String Edited= response.body().substring(12,response.body().length()-2);
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(Edited);
	    }

		public JSONObject sendPost() throws Exception {
			HttpRequest.BodyPublisher data=HttpRequest.BodyPublishers.ofString("""
	        {"sources": ["Official", "CSUB", "98Nats", "05Nats"], "categories": ["PHYSICS"]}
	        """);
			HttpRequest request = HttpRequest.newBuilder()
					.POST(data)
					.uri(URI.create("https://scibowldb.com/api/questions/random"))
					.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
					.header("Content-Type", "application/json; utf-8")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			String Edited= response.body().substring(12,response.body().length()-2);
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(Edited);
		}
		
		public JSONObject getQuestionOfCategory(String category) throws Exception {
			//make the the category variable passed in exactly matches our categories
			String input = String.format("""
				{"sources": ["Official", "CSUB", "98Nats", "05Nats"], "categories": ["%s"]}
				""", category);
			
			System.out.println(input);
			
			HttpRequest.BodyPublisher data=HttpRequest.BodyPublishers.ofString(input);
			HttpRequest request = HttpRequest.newBuilder()
					.POST(data)
					.uri(URI.create("https://scibowldb.com/api/questions/random"))
					.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
					.header("Content-Type", "application/json; utf-8")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			String Edited= response.body().substring(12,response.body().length()-2);
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(Edited);
		}

		private String createJSON(){
			return null;
		}
}
