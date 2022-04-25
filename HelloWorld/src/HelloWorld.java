import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.security.auth.login.LoginException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class HelloWorld {
	
	public static JDA jda;

	private final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();


	public static void main(String[] args) throws Exception{

		// TODO Auto-generated method stub
		JDABuilder builder = JDABuilder.createDefault("INSERT TOKEN HERE");
		HelloWorld obj = new HelloWorld();
		JSONObject QuestionFile=obj.sendGet();
		System.out.println(QuestionFile.get("bonus_question"));

	    // Disable parts of the cache
	    builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
	    // Enable the bulk delete event
	    builder.setBulkDeleteSplittingEnabled(false);
	    // Disable compression (not recommended)
	    builder.setCompression(Compression.NONE);
	    // Set activity (like "playing Something")
	    builder.setActivity(Activity.playing("Science Bowl!"));
	    builder.addEventListeners(new MessageListener());
	    builder.build();
	}

	//Method that returns a JSONObject with all of the information for a single question
	//To get a specific thing from the JSON file, call (JSONObject Name).get("(Name of thing ie. question)");
	private JSONObject sendGet() throws Exception {
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

	private JSONObject sendPost() throws Exception {
		HttpRequest.BodyPublisher data=HttpRequest.BodyPublishers.ofString("""
        {"sources": ["Official", "CSUB"], "categories": ["PHYSICS"]}
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

	private String createJSON(){
		return null;
	}
}
