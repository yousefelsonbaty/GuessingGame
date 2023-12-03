package application;
	
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		//Create a tab pane for the application
		TabPane tabPane = new TabPane();
		
		//Add the games' 2 tabs to the tab pane
	    tabPane.getTabs().add(new Tab("Guess The Number", new Assignment02()));
	    tabPane.getTabs().add(new Tab("Math Quiz Game", new Assignment03()));
	    
	    //Scene established for the application
	    Scene scene = new Scene(tabPane, 950, 410);

	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Guessing Game");

	    primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//Class for the first game
	class Assignment02 extends GridPane {
		int count = 0;
		int actualNumber = (int)(Math.random()*101);
		public Assignment02() {
			//Create a GridPane
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			
			//About the game
			String words = 
				    "Welcome to 'Guess The Number'\n" +
				    "About: In this game, the player will need to guess the correct number between 0 and 100.\n" +
				    "The game will keep track of the incorrect attempts and the player has the choice to see the correct number.\n" +
				    "Once the player guesses the correct number, the player has the choice to play again or quit and the incorrect attempts will be initialized to 0. \n" +
				    "ENJOY!";
			Label explanation = new Label(words);
			grid.add(explanation, 0, 0);
			
			//Creating a label for user input
			Label label = new Label("Enter number: ");
			grid.add(label, 0, 6);
			
			//Creating a text field for user input
			TextField tfUser = new TextField();
			grid.add(tfUser, 1, 6);
					
			//Add a button for user to enter their input
			Button btnEnter = new Button("Enter");
			grid.add(btnEnter, 1, 7);
			
			//Appearing text
			Text actiontarget1 = new Text();
			Text actiontarget2 = new Text();
			grid.add(actiontarget1, 0, 9);
			grid.add(actiontarget2, 0, 10);
			
			//Creating a label for correct answer
			Label label1 = new Label("Click the button to reveal the correct answer:  ");
			grid.add(label1, 0, 8);
			
			//Add a button to view correct answer
			Button btnAnswer = new Button("Reveal the Answer");
			grid.add(btnAnswer, 1, 8);
			Text actiontarget3 = new Text();
			grid.add(actiontarget3, 0, 7);
			
			//Add a pop-up window 
			GridPane popupLayout = new GridPane();
	        Label title = new Label ("Do you want to continue the game?");
	        popupLayout.add(title, 0, 0);
	        
	        Button btnContinue = new Button("Continue");
	        popupLayout.add(btnContinue, 0, 5);
	        
	        Button btnQuit = new Button("Quit");
	        popupLayout.add(btnQuit, 5, 5);

			Scene popupScene = new Scene(popupLayout, 250, 50);
			Stage popupWindow = new Stage();
			popupWindow.setTitle("Game Over!");
			popupWindow.setScene(popupScene);
			
			//Disabling the buttons until the user enters an answer
			btnEnter.disableProperty().bind(tfUser.textProperty().isEmpty());
			btnAnswer.disableProperty().bind(tfUser.textProperty().isEmpty());
			
			//Condition if user input is correct or not
			btnEnter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String userInput = String.valueOf(tfUser.getText());
					if (tfUser.getText().matches("\\d+") && Integer.parseInt(userInput) == actualNumber) {
						actiontarget1.setText("Your guess is correct!");
						popupWindow.show();
					}
					else if (tfUser.getText().matches("\\d+") && Integer.parseInt(userInput) > actualNumber){
						actiontarget1.setText("Your guess is too high!");
						count++;
						actiontarget2.setText("Number of incorrect attempts:  " + count);
					}
					else if (tfUser.getText().matches("\\d+") && Integer.parseInt(userInput) < actualNumber) {
						actiontarget1.setText("Your guess is too low!");
						count++;
						actiontarget2.setText("Number of incorrect attempts:  " + count);
					}
					else {
						actiontarget1.setText("Please enter an integer.");
						tfUser.clear();
					}
				}
			});
			
			//Number of incorrect attempts
			btnAnswer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					actiontarget3.setText("Better luck next time! The answer is:  " + actualNumber);
					popupWindow.show();
				}
			});
			
			//Condition if user wants to quit
			btnQuit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Platform.exit();
				}
			});
			
			//Condition if user wants to continue
			btnContinue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					popupWindow.close();
					actiontarget1.setText(null);
					actiontarget2.setText(null);
					actiontarget3.setText(null);
					tfUser.clear();
					count = 0;
					actualNumber = (int)(Math.random()*101);
				}
			});
			
			getChildren().addAll(grid);
		}
	}	
	
	//Class for the second game
	class Assignment03 extends VBox {
		Random r = new Random();
		int min = 0;
		int max = 10;
		int low = 11;
		int high = 100;
		int a = (int)(Math.random()*11);
		int x = (int)(Math.random()*11);
		int b = a * x;
		int op1, op2, op3;
		public Assignment03() {

			//Ensure that the options will not be identical
			do
			{
				//Generates a random number between min and max
				op1 = r.nextInt(max - min) + min;
				op2 = r.nextInt(max - min) + min;
				op3 = r.nextInt(max - min) + min;
			}
			while(op1 == op2 || op2 == op1 || op1 == op3 || op3 == op1 || op2 == op3 || op3 == op2 || op1 == x || op2 == x || op3 == x);
			
			//Menu items for Options menu 
			Menu menu = new Menu("Options");
			MenuItem menuItem1 = new MenuItem("New Game");
			CheckMenuItem checkMenuItem2 = new CheckMenuItem("Advanced");
			MenuItem menuItem3 = new MenuItem("Exit");
			
			//Adding the menu items to the Options menu
			menu.getItems().addAll(menuItem1, checkMenuItem2, menuItem3);
			
			//Menus in the View menu 
			Menu menu1 = new Menu("View");
			Menu menu2 = new Menu("Font size");
			Menu menu3 = new Menu("Theme");
			
			//Menu items for the View menus
			MenuItem menuItem01 = new MenuItem("Small");
			MenuItem menuItem02 = new MenuItem("Large");
			MenuItem menuItem03 = new MenuItem("Red");
			MenuItem menuItem04 = new MenuItem("Blue");
			MenuItem menuItem05 = new MenuItem("Black");
			
			//Adding the menu items to the menus
			menu2.getItems().addAll(menuItem01, menuItem02);
			menu3.getItems().addAll(menuItem03, menuItem04, menuItem05);
			
			//Adding the menus to the View menu
			menu1.getItems().addAll(menu2, menu3);
			
			//Menu bar for the menus
			MenuBar menuBar = new MenuBar();
			menuBar.getMenus().addAll(menu, menu1);
			
			//Game Explanation
			String words = 
				    "Welcome to 'Math Quiz Game'\n" +
				    "About: In this game, the player will need to solve the presented equation.\n" +
				    "If the user chooses 'New Game', a will be a randomly generated integer between 0 and 10, and b will be its mulitiple.\n" +
				    "If the user chooses 'Advanced', a will be a randomly generated integer between 11 and 100. \n" +
				    "HAVE FUN! :)";
			Label explanation = new Label(words);
			
			//Text field to enter username
			Label labelName = new Label("Enter username: ");
			TextField tfUser = new TextField();
			tfUser.setMaxWidth(200);
			Button btnEnter = new Button("Enter");
			
			//Labels for question and answer's result
			Label question = new Label("What is x of " + a + "x = " + b + "?");
			Label confirmation = new Label();
			Label labelresponse = new Label();
			        
			//Button to submit the answers
			Button btnSubmit = new Button("Submit");
			
			//Radio buttons to display the options
			RadioButton radio1, radio2, radio3, radio4;
			
			radio1 = new RadioButton("" + op1);
			radio2 = new RadioButton("" + x);
			radio3 = new RadioButton("" + op2);
			radio4 = new RadioButton("" + op3);
			
			//Setting the toggling settings to the radio buttons
			ToggleGroup toggleGroup = new ToggleGroup();

			radio1.setToggleGroup(toggleGroup);
			radio2.setToggleGroup(toggleGroup);
			radio3.setToggleGroup(toggleGroup);
			radio4.setToggleGroup(toggleGroup);

			//Defining the actions for the radio buttons when clicked
			radio1.setOnAction(e -> btnSubmit.setDisable(false));
			radio2.setOnAction(e -> btnSubmit.setDisable(false));
			radio3.setOnAction(e -> btnSubmit.setDisable(false));
			radio4.setOnAction(e -> btnSubmit.setDisable(false));
			
			//Setting the disable settings for the buttons
			btnSubmit.setDisable(true);
			btnEnter.disableProperty().bind(tfUser.textProperty().isEmpty());
			
			//Action if Enter button is clicked after submitting a valid or invalid username, or not submitting a username
			btnEnter.setOnAction(e -> {
				String userInput = String.valueOf(tfUser.getText());
				if (tfUser.getText().matches("\\d+")) {
					confirmation.setText("Please enter a valid username.");
					tfUser.clear();
					labelresponse.setText(null);
					btnSubmit.setDisable(true);
					radio1.setSelected(false);
					radio2.setSelected(false);
					radio3.setSelected(false);
					radio4.setSelected(false);
				}	   
				else {
					confirmation.setText("Your username has been saved, " + userInput + "!");
				}           
			});
			
			//Action if user did submit the correct or incorrect answer
			btnSubmit.setOnAction(e -> {
				String userInput = String.valueOf(tfUser.getText());
				if (userInput.isEmpty() || userInput.matches("\\d+")) {
					confirmation.setText("Please enter a valid username.");
					labelresponse.setText(null);
					btnSubmit.setDisable(true);
					tfUser.clear();
					radio1.setSelected(false);
					radio2.setSelected(false);
					radio3.setSelected(false);
					radio4.setSelected(false);
				}	   
				else {
					if (radio2.isSelected()) {
						labelresponse.setText("Good job, " + userInput + "!");
						btnSubmit.setDisable(true);
					}	   
					else {
						labelresponse.setText("Try again, " + userInput + "!");
						btnSubmit.setDisable(true);
					}
				}           
			});
			
			//Action if user chooses a new game
			menuItem1.setOnAction(e -> {
				confirmation.setText(null);
				labelresponse.setText(null);
				tfUser.clear();
				radio1.setSelected(false);
				radio2.setSelected(false);
				radio3.setSelected(false);
				radio4.setSelected(false);
				a = (int)(Math.random()*11);
				x = (int)(Math.random()*11);
				b = a * x;
				question.setText("What is x of " + a + "x = " + b + "?");
				do
				{
					//Generates a random number between min and max
					op1 = r.nextInt(max - min) + min;
					op2 = r.nextInt(max - min) + min;
					op3 = r.nextInt(max - min) + min; 
				}
				while(op1 == op2 || op2 == op1 || op1 == op3 || op3 == op1 || op2 == op3 || op3 == op2 || op1 == x || op2 == x || op3 == x);
				radio1.setText("" + op1);
				radio2.setText("" + x);
				radio3.setText("" + op2);
				radio4.setText("" + op3);
			});
			
			//Action if user chooses an advanced version of the game
			checkMenuItem2.setOnAction(e -> {
				if (checkMenuItem2.isSelected()) {
					confirmation.setText(null);
					labelresponse.setText(null);
					tfUser.clear();
					radio1.setSelected(false);
					radio2.setSelected(false);
					radio3.setSelected(false);
					radio4.setSelected(false);
					a = r.nextInt(high-low) + low;
					x = r.nextInt(high-low) + low;
					b = a * x;
					question.setText("What is x of " + a + "x = " + b + "?");
					do
					{
						//Generates a random number between low and high
						op1 = r.nextInt(high - low) + low;
						op2 = r.nextInt(high - low) + low;
						op3 = r.nextInt(high - low) + low; 
					}
					while(op1 == op2 || op2 == op1 || op1 == op3 || op3 == op1 || op2 == op3 || op3 == op2 || op1 == x || op2 == x || op3 == x);
					radio1.setText("" + op1);
					radio2.setText("" + x);
					radio3.setText("" + op2);
					radio4.setText("" + op3);
				}
				else {
					confirmation.setText(null);
					labelresponse.setText(null);
					tfUser.clear();
					radio1.setSelected(false);
					radio2.setSelected(false);
					radio3.setSelected(false);
					radio4.setSelected(false);
					a = (int)(Math.random()*11);
					x = (int)(Math.random()*11);
					b = a * x;
					question.setText("What is x of " + a + "x = " + b + "?");
					do
					{
						//Generates a random number between min and max
						op1 = r.nextInt(max - min) + min;
						op2 = r.nextInt(max - min) + min;
						op3 = r.nextInt(max - min) + min; 
					}
					while(op1 == op2 || op2 == op1 || op1 == op3 || op3 == op1 || op2 == op3 || op3 == op2 || op1 == x || op2 == x || op3 == x);
					radio1.setText("" + op1);
					radio2.setText("" + x);
					radio3.setText("" + op2);
					radio4.setText("" + op3);
				}
			});
			
			//Action if user exits the game
			menuItem3.setOnAction(e -> {
				Platform.exit();
			});
			
			//Action if user wants to make the font smaller
			menuItem01.setOnAction(e -> {
				explanation.setFont(Font.font(null, null, null, 10));
				labelName.setFont(Font.font(null, null, null, 10));
				btnEnter.setFont(Font.font(null, null, null, 10));
				question.setFont(Font.font(null, null, null, 10));
				confirmation.setFont(Font.font(null, null, null, 10));
				labelresponse.setFont(Font.font(null, null, null, 10));
				btnSubmit.setFont(Font.font(null, null, null, 10));
				radio1.setFont(Font.font(null, null, null, 10));
				radio2.setFont(Font.font(null, null, null, 10));
				radio3.setFont(Font.font(null, null, null, 10));
				radio4.setFont(Font.font(null, null, null, 10));
				
			});
			
			//Action if user wants to make the font larger
			menuItem02.setOnAction(e -> {
				explanation.setFont(Font.font(null, null, null, 14));
				labelName.setFont(Font.font(null, null, null, 14));
				btnEnter.setFont(Font.font(null, null, null, 14));
				question.setFont(Font.font(null, null, null, 14));
				confirmation.setFont(Font.font(null, null, null, 14));
				labelresponse.setFont(Font.font(null, null, null, 14));
				btnSubmit.setFont(Font.font(null, null, null, 14));
				radio1.setFont(Font.font(null, null, null, 14));
				radio2.setFont(Font.font(null, null, null, 14));
				radio3.setFont(Font.font(null, null, null, 14));
				radio4.setFont(Font.font(null, null, null, 14));
			});
			
			//Action if user wants to make the theme red
			menuItem03.setOnAction(e -> {
				explanation.setTextFill(Color.RED);  
				labelName.setTextFill(Color.RED); 
				btnEnter.setTextFill(Color.RED); 
				question.setTextFill(Color.RED);
				confirmation.setTextFill(Color.RED);
				labelresponse.setTextFill(Color.RED); 
				btnSubmit.setTextFill(Color.RED); 
				radio1.setTextFill(Color.RED); 
				radio2.setTextFill(Color.RED); 
				radio3.setTextFill(Color.RED); 
				radio4.setTextFill(Color.RED); 
			});
			
			//Action if user wants to make the theme blue
			menuItem04.setOnAction(e -> {
				explanation.setTextFill(Color.BLUE);  
				labelName.setTextFill(Color.BLUE); 
				btnEnter.setTextFill(Color.BLUE); 
				question.setTextFill(Color.BLUE);
				confirmation.setTextFill(Color.BLUE);
				labelresponse.setTextFill(Color.BLUE); 
				btnSubmit.setTextFill(Color.BLUE); 
				radio1.setTextFill(Color.BLUE); 
				radio2.setTextFill(Color.BLUE); 
				radio3.setTextFill(Color.BLUE); 
				radio4.setTextFill(Color.BLUE); 
			});
			
			//Action if user wants to make the theme black
			menuItem05.setOnAction(e -> {
				explanation.setTextFill(Color.BLACK); 
				labelName.setTextFill(Color.BLACK); 
				btnEnter.setTextFill(Color.BLACK); 
				question.setTextFill(Color.BLACK); 
				confirmation.setTextFill(Color.BLACK);
				labelresponse.setTextFill(Color.BLACK); 
				btnSubmit.setTextFill(Color.BLACK); 
				radio1.setTextFill(Color.BLACK); 
				radio2.setTextFill(Color.BLACK); 
				radio3.setTextFill(Color.BLACK); 
				radio4.setTextFill(Color.BLACK); 
			});
			
			//Add all elements into the VBox
			VBox layout= new VBox(5);
			layout.getChildren().addAll(menuBar, explanation, labelName, tfUser, btnEnter, question, radio1, radio2, radio3, radio4, btnSubmit, confirmation, labelresponse);
			
			getChildren().addAll(layout);
		}
	}
}