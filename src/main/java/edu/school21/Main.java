package edu.school21;

import edu.school21.enums.ScreenType;
import edu.school21.viewmodels.BasicViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private BasicViewModel viewModel;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final URL url = getClass().getResource(ScreenType.BASIC.getFileName());

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        final Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(ScreenType.BASIC.getTitle());

        viewModel = loader.getController();
        viewModel.initialize(stage);

        stage.show();
    }

    @Override
    public void stop() {
        viewModel.stop();
    }
}