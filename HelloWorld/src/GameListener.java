import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter{
	 private final long channelId, authorId; 
	 // id because keeping the entity would risk cache to become outdated, according
	 public String MostRecentMessage = "";
	 
	 public GameListener(MessageChannel channel, User author) {
		 this.channelId = channel.getIdLong();
	     this.authorId = author.getIdLong();
	 }
	 
	 @Override
	 public void onMessageReceived(MessageReceivedEvent event) {
		 if (event.getAuthor().isBot()) return; // don't respond to other bots
		 if (event.getChannel().getIdLong() != channelId) return; // ignore other channels
		 MessageChannel channel = event.getChannel();
	     String content = event.getMessage().getContentRaw();
		 
		 if (event.getAuthor().getIdLong() == authorId) {
			 if (!content.startsWith("_")) {
				 MostRecentMessage = content;
			 }
		
			 
		 }
	 }
}
