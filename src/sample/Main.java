package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // Calling the varargs method with different number
        // of parameters
        StatsCalc.calculateStats();            	   // no parameter
        StatsCalc.calculateStats(100);         // one parameter
        StatsCalc.calculateStats(21,19);       // two parameter
        StatsCalc.calculateStats(31,28,34);    // three parameter
        StatsCalc.calculateStats(4,2,3,1);     // four parameters
        StatsCalc.calculateStats(52,6,48,23,17,94,78,48,15,29);     // ten parameters
        StatsCalc.calculateStats(10,85,2,15,35,87,83,31,2,60,93,90,7,91,27,16,77,16,38,81,92,84,42);     // 23 parameters

        int[] set0 = {10,85,2,15,35,87,83,31,2,60,93,90,7,91,27,16,77,16,38,81,92,84,42};
        int[] set1 = {52,6,48,23,17,94,78,48,15,29};
        int[] set2 = {31,28,34};

        System.out.println("===============================================================");
        System.out.println("\nPooled Stats...\n");
        StatsCalc.calculatePooledStats(set0,set1,set2);

        System.out.println("===============================================================");
        System.out.println("\nTransformed Stats...\n");
        StatsCalc.print("Before transformation: ", set1);
        int[] set3 = StatsCalc.translateData(2,8,set1);
        StatsCalc.print("Translated set (after f(x) = 2x+8 transformation): ", set3);
        StatsCalc.calculateStats(set3);
        //launch(args);
    }
}
