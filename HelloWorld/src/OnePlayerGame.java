import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OnePlayerGame {
	public boolean continueGame = true;
	public boolean playerResponded = false;
	
	Random rand = new Random();
	
	MessageReceivedEvent event;
	MessageChannel channel;
	User author;
	GameListener listener;
	
	ArrayList<String> categories = new ArrayList<String>();
	String[] allCategories = new String[]{"PHYSICS", "ENERGY", "EARTH AND SPACE", "EARTH SCIENCE", 
				        "ASTRONOMY", "CHEMISTRY", "BIOLOGY", "MATH"};
	ArrayList<Integer> stats = new ArrayList<Integer>();
	
	JSONObject currentQuestion;
	
	int tusPlayed = 0;
	int bsPlayed = 0;
	
	public String userAnswer;
	
	
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
		//checks in between tossup and bonus questions to see if user wants to end game
		while (continueGame) {
			if (listener.MostRecentMessage.equals("!end")) {
				continueGame = false;
			}
			else {
				tossupQuestion();
			}
			if (listener.MostRecentMessage.equals("!end")) {
				continueGame = false;
			}
			else {
				bonusQuestion();
			}
		}
		endGameSequence();
	}
	
	public void tossupQuestion() throws Exception {
		
		String currentCategory = "";
		DatabaseUnpacking unpack = new DatabaseUnpacking();
		
		//if a category(s) was chosen, randomly pick a category to give 
		if (categories.size()!=0) {
			int index = rand.nextInt(categories.size());
			currentCategory = categories.get(index);
		}
		//return a question of the randomly chosen category
		//set currentQuestion equal to this JSON file so bonusQuestion() will give corresponding bonus
		currentQuestion = unpack.getQuestionOfCategory(currentCategory);
		String tossupFormat = (String) currentQuestion.get("tossup_format"); 
		String correctTUAnswer = (String) currentQuestion.get("tossup_answer");
		
		String question;
		try {
			question = (String) currentQuestion.get("tossup_question");
			channel.sendMessage("Tossup Question:\n" + question).queue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		channel.sendMessage("now i am waiting for a response").queue();
		
		event.getJDA().addEventListener(new GameStateMachine(channel, author, this));
		while (!playerResponded) {
			//stuck in this loop until player responds. 
		}
		//once player responds, userAnswer is set to first answer in the GameStateMachine class
		
		channel.sendMessage("wow you responded yay").queue();
		
		if (userAnswer.equalsIgnoreCase(correctTUAnswer)) {
			//if tossup was correct, update stats to reflect this
			updateStats(true, currentCategory);
			channel.sendMessage("Correct!").queue();
			channel.sendMessage("The correct answer was:\n" + correctTUAnswer).queue();
		}
		else if (tossupFormat.equals("Multiple Choice")){
			String userStart = userAnswer.substring(0,1).toUpperCase();
			String realStart = correctTUAnswer.substring(0,1);
			//if it's multiple choice, we only have to compare the first letter
			if (userStart.equalsIgnoreCase(realStart)) {
				channel.sendMessage("Correct!").queue();
				channel.sendMessage("The correct answer was:\n" + correctTUAnswer).queue();
				updateStats(true, currentCategory);
			}
		} else {
			channel.sendMessage("The correct answer was:\n" + correctTUAnswer).queue();
			channel.sendMessage("Were you correct? y/n").queue();
			event.getJDA().addEventListener(new GameStateMachine(channel, author, this));
			while (!playerResponded) {
				//stuck in this loop until player responds. 
			}
			if (userAnswer.equalsIgnoreCase("y")) {
				updateStats(true, currentCategory);
			}
		}
		
		tusPlayed ++;
		
	}
	
	public void bonusQuestion() {
		String bonusFormat = (String) currentQuestion.get("bonus_format");
		String correctBAnswer = (String) currentQuestion.get("bonus_answer");
		String currentCategory = (String) currentQuestion.get("category");
		
		String question;
		try {
			question = (String) currentQuestion.get("bonus_question");
			channel.sendMessage("Bonus Question:\n" + question).queue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.getJDA().addEventListener(new GameStateMachine(channel, author, this));
		while (!playerResponded) {
			//stuck in this loop until player responds. 
		}
		
		if (userAnswer.equalsIgnoreCase(correctBAnswer)) {
			//if tossup was correct, update stats to reflect this
			updateStats(false, currentCategory);
			channel.sendMessage("Correct!").queue();
			channel.sendMessage("The correct answer was:\n" + correctBAnswer).queue();
		}
		else if (bonusFormat.equals("Multiple Choice")){
			String userStart = userAnswer.substring(0,1).toUpperCase();
			String realStart = correctBAnswer.substring(0,1);
			//if it's multiple choice, we only have to compare the first letter
			if (userStart.equalsIgnoreCase(realStart)) {
				channel.sendMessage("Correct!").queue();
				channel.sendMessage("The correct answer was:\n" + correctBAnswer).queue();
				updateStats(false, currentCategory);
			}
		} else {
			channel.sendMessage("The correct answer was:\n" + correctBAnswer).queue();
			channel.sendMessage("Were you correct? y/n").queue();
			event.getJDA().addEventListener(new GameStateMachine(channel, author, this));
			while (!playerResponded) {
				//stuck in this loop until player responds. 
			}
			if (userAnswer.equalsIgnoreCase("y")) {
				updateStats(false, currentCategory);
			}
		}
		bsPlayed++;
		
	}
	
	public void updateStats(boolean tossup, String category) {
		
		//statsIndex needs a default value; I can't think of anything but 0? 
		int statsIndex = 0;
		
		//use switch statement to make sure points get added to the correct index in stats
		switch (category) {
		case "PHYSICS": 
			statsIndex = 0;
			break;
		case "ENERGY":
			statsIndex = 1;
			break;
		case "EARTH AND SPACE": 
			statsIndex = 2;
			break;
		case "EARTH SCIENCE":
			statsIndex = 3;
			break;
		case "ASTRONOMY":
			statsIndex = 4;
			break;
		case "CHEMISTRY":
			statsIndex = 5;
			break;
		case "BIOLOGY":
			statsIndex = 6;
			break;
		case "MATH":
			statsIndex = 7;
			break; 
		}
		
		if (tossup) {
			//correct tossup adds 4 points
			int oldScore = stats.get(statsIndex); 
			stats.set(statsIndex, oldScore+4);
		}
		else {
			//correct bonus adds 10 points
			int oldScore = stats.get(statsIndex); 
			stats.set(statsIndex, oldScore+10);
		}
		
	}
	
	public void endGameSequence() {
		//send subject specific and total stats
		//TODO: add number questions heard per subject? 
		channel.sendMessage("Thanks for playing! Here are your stats:").queue();
		channel.sendMessage("Physics: " + Integer.toString(stats.get(0))).queue();
		channel.sendMessage("Energy: " + Integer.toString(stats.get(1))).queue();
		channel.sendMessage("Earth and Space: " + Integer.toString(stats.get(2))).queue();
		channel.sendMessage("Earth Science: " + Integer.toString(stats.get(3))).queue();
		channel.sendMessage("Astronomy: " + Integer.toString(stats.get(4))).queue();
		channel.sendMessage("Chemistry: " + Integer.toString(stats.get(5))).queue();
		channel.sendMessage("Biology: " + Integer.toString(stats.get(6))).queue();
		channel.sendMessage("Math: " + Integer.toString(stats.get(7))).queue();
		channel.sendMessage("Total Tossups heard:" + Integer.toString(tusPlayed)).queue();
		channel.sendMessage("Total Bonuses heard:" + Integer.toString(bsPlayed)).queue();
		int pointsPossible = 4*tusPlayed + 10*bsPlayed;
		int pointsEarned = 0;
		for (int i =0; i<8; i++) {
			pointsEarned = pointsEarned + stats.get(i);
		}
		channel.sendMessage("Total Points earned:" + Integer.toString(pointsEarned)).queue();
		channel.sendMessage("Total Points possible:" + Integer.toString(pointsPossible)).queue();
		channel.sendMessage("That's it! Have a great day :)").queue();
		
	}
	
	
}
