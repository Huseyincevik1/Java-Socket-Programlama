package application;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SampleController implements Initializable{
	@FXML 
	private Button sendButton;
	@FXML 
	private TextField writeTextField;
	@FXML 
	private ScrollPane scrollPane;
	@FXML 
	private VBox messageVBox;
	
	private Client client;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		  try {
			  client=new Client(new Socket("localhost",5555));
              System.out.println("Servera bağlanıldı!");
		  }catch(IOException e) {
			  e.printStackTrace();
		  }
	
		  messageVBox.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				scrollPane.setVvalue((Double)arg2);	
				// SCROLL BAR AYARLANMASI
			}
		  });
		  
		  client.receiveMessageFromServer(messageVBox); //SERVERDAN GELEN MESAJ
		  
		  sendButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String messageToSend = writeTextField.getText();
				if(!messageToSend.isEmpty()){
					HBox hBox = new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					hBox.setPadding(new Insets(10,20,10,20));
					
					Text text = new Text(messageToSend);
					TextFlow textFlow = new TextFlow(text);
					textFlow.setStyle("-fx-background-color: #8EC3B0;"+
				                      "-fx-color: #fff;"+
				                      "-fx-background-radius: 20px;");
					textFlow.setPadding(new Insets(10,20,10,20));
					text.setFill(Color.color(0.934, 0.954, 0.996));
					
					hBox.getChildren().add(textFlow);
					messageVBox.getChildren().add(hBox);
					
					client.sendMessageToServer(messageToSend); //SERVERA MESAJ GÖNDERME
				
					writeTextField.clear();
					
				}
				
			}
		  });
	}



	public static void addMessage(String messageFromServer, VBox vBox) {
		
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(10,20,10,20));
		
		Text text = new Text(messageFromServer);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setStyle("-fx-background-color: #DEF5E5;"+
	                      "-fx-background-radius: 20px;");
		textFlow.setPadding(new Insets(10,20,10,20));
		hBox.getChildren().add(textFlow);
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				vBox.getChildren().add(hBox);
			}
			
		});
		
	}


 
}
