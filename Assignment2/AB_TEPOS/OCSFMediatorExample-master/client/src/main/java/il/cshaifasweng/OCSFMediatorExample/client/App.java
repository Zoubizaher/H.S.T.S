package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private SimpleClient client;

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
    	client = SimpleClient.getClient();
    	client.openConnection();
        scene = new Scene(loadFXML("HomePage"), 640, 480);
        stage.setTitle("High School Test System, Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    

    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}


    public static Scene getScene(){
        return scene;
    }

    public static Stage getStage(){
        return primaryStage;
    }

	public static void main(String[] args) {
        launch();
    }

}