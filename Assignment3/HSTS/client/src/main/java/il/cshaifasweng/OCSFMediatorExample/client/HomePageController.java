package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

public class HomePageController {
	private EventBus eventBus;
	private StudentListSubscriber subscriber;
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private ImageView eye;

	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			showAlert("Error", "Failed to send warning: " + e.getMessage());
		}
	}

	@FXML
	void ClientProfileLoad(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("clientHomePage.fxml"));
			AnchorPane newScene = loader.load();

			Stage currentStage = App.getStage();
			Scene scene = new Scene(newScene);
			currentStage.setTitle("Client Home Page");
			currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String title, String message) {
		// Display an alert dialog to the user
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void logginin(ActionEvent actionEvent) {
		try {
			String messageToSend = "#LogInAttempt," + usernameField.getText() + "@" + passwordField.getText();
			StringSubscriber subscriber = new StringSubscriber();
			EventBus.getDefault().register(subscriber);
			SimpleClient.getClient().sendToServer(messageToSend);
			Thread.sleep(500);
			String recievedMSG = subscriber.getReceivedMSG();
		} catch (IOException e) {
			showAlert("Error", "Failed to Get Login message!" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}