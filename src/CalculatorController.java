import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CalculatorController {
	private final int SIZE = 4;
	private final int NUM_SIZE = 10;
	private String[] icons = {"+/-", ".", "/", "*", "-", "+"};
	private String answer;
	private Button[] btns;
	private Logic logic;

    @FXML
    private Text screen;

    @FXML
    private GridPane grid;
    
    public void initialize() {
    	logic = new Logic();
    	btns = new Button[SIZE*SIZE];
    	
    	for (int i = 0; i < SIZE*SIZE; i++) {
    		if (i < NUM_SIZE) 
    			btns[i] = new Button(i + ""); // Gives buttons number    		
    		else  
    			btns[i] = new Button(icons[i - NUM_SIZE]); // Gives buttons icon
    		
    		btns[i].setPrefSize(grid.getPrefWidth() / SIZE, grid.getPrefHeight() / SIZE);
    		grid.add(btns[i], i % SIZE, i / SIZE);
    		btns[i].setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					buttonHandle(event);
				}   			
    		});
    	}
    }

	@FXML
    void equalsPressed(ActionEvent event) {
		buttonHandle(event);
    }

    @FXML
    void clearPressed(ActionEvent event) {
    	screen.setText("0");
    	logic.clear();
    }
    
    private void buttonHandle(ActionEvent event) {
    	Button button = (Button) event.getSource();
		answer = logic.screenUpdate(button.getText());
		screen.setText(answer);
    }
}
