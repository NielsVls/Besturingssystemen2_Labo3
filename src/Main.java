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
        System.out.println("Welk algoritme?");
        System.out.println("a) FCFS");
        System.out.println("b) SJF");
        System.out.println("c) HRRN");
        System.out.println("d) MLF");
        System.out.println("e) RR");
        System.out.println("f) SRT");
        System.out.println("g) Allemaal (1 processor)");
        System.out.println("h) Allemaal (2 processoren)");
        System.out.println("i) Allemaal (4 processoren)");
        String choice2=sc.next();

        //vermenigvuldigen van deze 2 keuzes leidt tot slechts 1 mogelijkheid
        String choice=choice1+choice2;

        //afhankelijk van de keuze, de juiste grafiek weergeven
        System.out.println("~~~~~~~~~~Dit zijn de gemiddelde waarden voor de verschillende scheduling algoritmen~~~~~~~~~~");
        switch(choice){
            case "1a":
                graph.FCFS(stage, 50000);break;
            case "1b":
                graph.SJF(stage, 50000);break;
            case "1c":
                graph.HRRN(stage, 50000);break;
            case "1d":
                graph.MLF(stage, 50000);break;
            case "1e":
                graph.RR(stage, 50000);break;
            case "1f":
                graph.SRT(stage, 50000);break;
            case "1g":
                graph.TatNorm(stage, 50000);break;
            case "1h":
                graph.TatNorm2(stage, 50000);break;
            case "1i":
                graph.TatNorm4(stage, 50000);break;
            case "2a":
                graph.WaitFCFS(stage, 50000);break;
            case "2b":
                graph.WaitSJF(stage, 50000);break;
            case "2c":
                graph.WaitHRRN(stage, 50000);break;
            case "2d":
                graph.WaitMLF(stage, 50000);break;
            case "2e":
                graph.WaitRR(stage, 50000);break;
            case "2f":
                graph.WaitSRT(stage, 50000);break;
            case "2g":
                graph.Wacht(stage, 50000);break;
            case "2h":
                graph.Wacht2(stage, 50000);break;
            case "2i":
                graph.Wacht4(stage, 50000);break;
            default:
                System.out.println("Dit was geen geldige keuze."); break;
        }
        stage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}
