import java.util.ArrayList;
import java.util.Arrays;

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
		/*
		if (event.getMessage().getContentRaw().startsWith("Hi Apples!")) {
		    event.getChannel().sendMessage("Hi! Tell me your name, or say \"Stop\"!").queue();
		    event.getJDA().addEventListener(new GameStateMachine(event.getChannel(), event.getAuthor()));
		}
		*/
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
			
			event.getMessage().reply("listener 1 here").queue();
		
			
		}
		else if (args[0].equalsIgnoreCase(prefix + "game")) {
			//TODO look at the thread name what we should do about that
			Thread newThread = new Thread(() -> {
				ArrayList<String> al = new ArrayList<String>();
				if (args.length>1) {
					//if category arguments were given
					al = new ArrayList<String>(Arrays.asList(args));
				}
			    OnePlayerGame thisGame = new OnePlayerGame(event, al);
			    try {
					thisGame.gameControl();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			newThread.start();
			
		}
		else if (args[0].equalsIgnoreCase(prefix + "help")) {
			
		}
	}

}
