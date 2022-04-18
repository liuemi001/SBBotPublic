import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter{

	//public static void main(String[] args) throws LoginException{
		// TODO Auto-generated method stub
		//JDA jda = JDABuilder.createDefault("OTU0ODE0MzAzNTcwMTAwMjg0.YjYmHQ.BzkEwALqpo9cvD9YbfjFhR5RA68").build();
	    //You can also add event listeners to the already built JDA instance
	    // Note that some events may not be received if the listener is added after calling build()
	    // This includes events such as the ReadyEvent
	   // jda.addEventListener(new MessageListener());

//	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
		System.out.println(event.getChannel());
		System.out.println(event.getMessage());
	    event.getChannel().sendMessage("hello!").queue();
    }

}
