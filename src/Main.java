import Support.Graph;
import javafx.application.Application;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    @Override public void start(Stage stage) throws IOException, SAXException, ParserConfigurationException {
        //Alle mogelijke opties weergeven en de gebruiker laten kiezen
        Graph graph = new Graph();
        Scanner sc= new Scanner(System.in);

        //hier wordt de eerste keuze gemaakt
        System.out.println("Welke grafiek wilt u zien?");
        System.out.println("1) Genormaliseerde omlooptijd");
        System.out.println("2) Wachttijd");
        int choice1=sc.nextInt();

        //hier wordt de tweede keuze gemaakt
        System.out.println("Wat is het aantal processen die u wilt gebruiken?");
        System.out.println("a) 10.000 processen");
        System.out.println("b) 20.000 processen");
        System.out.println("c) 50.000 processen");
        String choice2=sc.next();

        //vermenigvuldigen van deze 2 keuzes leidt tot slechts 1 mogelijkheid
        String choice=choice1+choice2;

        //afhankelijk van de keuze, de juiste grafiek weergeven
        System.out.println("~~~~~~~~~~Dit zijn de gemiddelde waarden voor de verschillende scheduling algoritmen~~~~~~~~~~");
        switch(choice){
            case "1a":
                graph.TatNorm(stage,10000);break;
            case "1b":
                graph.TatNorm(stage,20000);break;
            case "1c":
                graph.TatNorm(stage,50000);break;
            case "2a":
                graph.Wait(stage,10000);break;
            case "2b":
                graph.Wait(stage,20000);break;
            case "2c":
                graph.Wait(stage,50000);break;
            default:
                System.out.println("Dit was geen geldige keuze."); break;
        }
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
