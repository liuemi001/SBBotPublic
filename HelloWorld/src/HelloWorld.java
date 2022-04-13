import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class HelloWorld {
	
	public static JDA jda; 

	public static void main(String[] args) throws LoginException{
		// TODO Auto-generated method stub
		//JDA jda = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68").build();
		JDABuilder builder = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68");
	    
	    // Disable parts of the cache
	    builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
	    // Enable the bulk delete event
	    builder.setBulkDeleteSplittingEnabled(false);
	    // Disable compression (not recommended)
	    builder.setCompression(Compression.NONE);
	    // Set activity (like "playing Something")
	    builder.setActivity(Activity.watching("TV"));
	    
	    builder.build();
		
		
	}
	
	private void sendGet() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://scibowldb.com/api/questions/random%22"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

}
