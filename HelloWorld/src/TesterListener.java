import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TesterListener extends ListenerAdapter{
	public String prefix = "!";
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split(" ");
		//Thread newThread = new Thread(() -> {
			if (args[0].equalsIgnoreCase(prefix + "hi")) {
				
				//DatabaseUnpacking unpack = new DatabaseUnpacking();
				//unpack.helloint = 2;
				//try {
				//	TimeUnit.SECONDS.sleep(3);
				//} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
				event.getMessage().reply("tester 2").queue();
			}
		//});
		//newThread.start();
		
	}
	
	
}
