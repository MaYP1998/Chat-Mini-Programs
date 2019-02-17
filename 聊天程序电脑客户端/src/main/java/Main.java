import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    Stage stage=new Stage();
    static Stage primaryStage=null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("聊天程序");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
        Main t1=new Main();
        t1.showWindow();
    }

    public void showWindow() throws Exception{
        start(stage);
    }

    public static void closeWindow(){
        primaryStage.close();
    }
}
