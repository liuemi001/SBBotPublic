import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameStateMachine extends ListenerAdapter {
	 private final long channelId, authorId; // id because keeping the entity would risk cache to become outdated
	 OnePlayerGame game;

	    public GameStateMachine(MessageChannel channel, User author, OnePlayerGame game) {
	        this.channelId = channel.getIdLong();
	        this.authorId = author.getIdLong();
	        this.game = game;
	    }

	    @Override
	    public void onMessageReceived(MessageReceivedEvent event) {
	        if (event.getAuthor().isBot()) return; // don't respond to other bots
	        if (event.getChannel().getIdLong() != channelId) return; // ignore other channels
	        MessageChannel channel = event.getChannel();
	        String content = event.getMessage().getContentRaw();
	        
	        if (event.getAuthor().getIdLong() == authorId) {
		        game.userAnswer = content;
		        System.out.println("user answer was:" + game.userAnswer);
		        System.out.println("In game state machine, playerresponded is");
		        game.playerResponded = true;
		        System.out.println(game.playerResponded);
	            event.getJDA().removeEventListener(this); // stop listening
	            //since the listener is removed immediately after triggered
	            //no need to worry about it taking later messages instead of the first one sent after triggered
	            
	        }
	        else {
	            channel.sendMessage("This is not your game " + event.getMember().getEffectiveName() + "! Please use a different channel.").queue();
	        }
	    }
}
