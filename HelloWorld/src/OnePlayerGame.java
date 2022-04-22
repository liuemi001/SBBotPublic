import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OnePlayerGame {
	public boolean continueGame = false;
	Random rand = new Random();
	MessageReceivedEvent event;
	GameListener listener;
	ArrayList<String> categories = new ArrayList<String>();
	ArrayList<Integer> stats = new ArrayList<Integer>();
	
	public OnePlayerGame(MessageReceivedEvent event, ArrayList<String> categories) {
		this.event = event;
		categories.addAll(categories);
	}
	
	public void startGame() {
		//method triggered when user triggers !game
		continueGame = true;
		this.listener = new GameListener(event.getChannel(), event.getAuthor());
	}
	
	public void gameControl() {
		while (continueGame) {
			if (listener.MostRecentMessage.equals("!end")) {
				continueGame = false;
			}
			else {
				questionCycle();
			}
		}
		endGameSequence();
	}
	
	public void questionCycle() {
		String currentCategory = "";
		DatabaseUnpacking unpack = new DatabaseUnpacking();
		if (categories.size()!=0) {
			int index = rand.nextInt(categories.size());
			currentCategory = categories.get(index);
		}
		questionFile = unpack.
	}
	
	public void endGameSequence() {
		
	}
	
	
}
