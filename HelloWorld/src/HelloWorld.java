import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class HelloWorld {
	
	public static JDA jda; 

	public static void main(String[] args) throws LoginException{
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("java.class.path"));
		JDA jda = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68").build();
		//jda.J.setActivity(Activity.playing("science bowl :)"));
		//jda.setStatus(OnlineStatus.ONLINE);
		
	}

}
