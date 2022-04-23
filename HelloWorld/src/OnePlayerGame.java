import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OnePlayerGame {
	public boolean continueGame = true;
	Random rand = new Random();
	MessageReceivedEvent event;
	MessageChannel channel;
	User author;
	GameListener listener;
	ArrayList<String> categories = new ArrayList<String>();
	String[] allCategories = new String[]{"PHYSICS", "ENERGY", "EARTH AND SPACE", "EARTH SCIENCE", 
				        "ASTRONOMY", "CHEMISTRY", "BIOLOGY", "MATH"};
	ArrayList<Integer> stats = new ArrayList<Integer>();
	
	
	public OnePlayerGame(MessageReceivedEvent event, ArrayList<String> categories) {
		this.event = event;
		this.channel = event.getChannel();
		this.author = event.getAuthor();
		this.listener = new GameListener(event.getChannel(), event.getAuthor());
		ArrayList<String> rawcats = new ArrayList<String>();
		rawcats.addAll(categories);
		
		//parse user inputs to the formal category names for JSON fetching
		for (int i =0; i < rawcats.size(); i++) {
			switch (rawcats.get(i).toLowerCase()) {
			case "phys": 
				this.categories.add("PHYSICS");
				break;
			case "nrg":
				this.categories.add("ENERGY");
				break;
			case "es": 
				this.categories.add("EARTH AND SPACE");
				break;
			case "earth":
				this.categories.add("EARTH SCIENCE");
				break;
			case "astro":
				this.categories.add("ASTRONOMY");
				break;
			case "chem":
				this.categories.add("CHEMISTRY");
				break;
			case "bio":
				this.categories.add("BIOLOGY");
				break;
			case "math":
				this.categories.add("MATH");
				break;
			}
				
		}
		
		
		//initialize stats arraylist to 0 for each category
		//each entry in stats corresponds to a category in the order of allcats
		for (int i=1;i<9;i++) {
			stats.add(0);
		}
	}
	
	
	public void gameControl() throws Exception {
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
	
	public void questionCycle() throws Exception {
		String currentCategory = "";
		DatabaseUnpacking unpack = new DatabaseUnpacking();
		
		//if a category(s) was chosen, randomly pick a category to give 
		if (categories.size()!=0) {
			int index = rand.nextInt(categories.size());
			currentCategory = categories.get(index);
		}
		//return a question of the randomly chosen category
		JSONObject questionFile = unpack.getQuestionOfCategory(currentCategory);
		
		String question;
		try {
			question = (String) questionFile.get("tossup_question");
			channel.sendMessage("Tossup Question:\n" + question).queue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.getJDA().addEventListener(new GameStateMachine(channel, author));
		//implement here pause until a message is sent
		
		String userAnswer = listener.MostRecentMessage;
		if (userAnswer.equalsIgnoreCase((String) questionFile.get("tossup_answer"))) {
			
			//use switch statement to make sure points get added to the correct index in stats
			switch (questionFile.get("category"))
		}
		
	}
	
	public void endGameSequence() {
		
	}
	
	
}
