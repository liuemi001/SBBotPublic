import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter{
	public String prefix = "!";
	DatabaseUnpacking bird = new DatabaseUnpacking();

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
		String[] args = event.getMessage().getContentRaw().split(" ");
		//System.out.println(args[0]);
		if (args[0].equalsIgnoreCase(prefix + "question")) 
		{
			String question;
			try {
				question = (String) bird.sendGet().get("tossup_question");
				System.out.println(question);
				event.getMessage().reply(question).queue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if (args[0].equalsIgnoreCase(prefix + "hi")) {
			event.getMessage().reply("hi look I can reply!").queue();
		}
		else if (args[0].equalsIgnoreCase(prefix + "game")) {
			
		}
		else if (args[0].equalsIgnoreCase(prefix + "end")) {
			
		}
	}

}
