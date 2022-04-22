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
		//JDA jda = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68").build();
		JDABuilder builder = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68");
		JDABuilder builder2 = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68");
		
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
	    TesterListener listener1 = new TesterListener();
	    builder.addEventListeners(listener1);
	    builder.build();
	    builder.removeEventListeners(listener1);
	    builder.addEventListeners(new MessageListener());
	    builder.build();
	    String request = String.format("""
				{"sources": ["Official", "CSUB"], "categories": ["%s"]}
				""", "PHYSICS");
	    System.out.println(request);
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
