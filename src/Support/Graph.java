package Support;

import Algorithms.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {

    private ReadXML p;
    private Queue<Process> processes;

    public Graph() {
        p = new ReadXML();
    }

    //functie om Genormaliseerd omlooptijd uit te zetten in functie van de bedieningstijd
    public void TatNorm(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen");

        //assen grafiek opstellen
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Percentiel bedieningstijd");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(100);
        xAxis.setTickUnit(10);
        yAxis.setLabel("Genormaliseerde omlooptijd");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(20);
        yAxis.setTickUnit(2);

        //grafiek aanmaken
        final LineChart<Number, Number> Graph = new LineChart<>(xAxis, yAxis);
        Graph.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();                    //S -> series
        Sfcfs.setName("FCFS");                                          //Q -> Queue
        Scheduler SCfcfs = new FirstComeFirstServed();                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesNorm(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new ShortestJobFirst();
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesNorm(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new ShortestRemainingTime();
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesNorm(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR2
        XYChart.Series Srr2 = new XYChart.Series();
        Srr2.setName("RR q=2");
        Scheduler SCrr2 = new RoundRobin();
        PriorityQueue<Process> Qrr2 = SCrr2.schedule(processes, 2);
        vulSeriesNorm(Qrr2, Srr2);
        Graph.getData().add(Srr2);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR q=4");
        Scheduler SCrr4 = new RoundRobin();
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesNorm(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //RR8
        XYChart.Series Srr8 = new XYChart.Series();
        Srr8.setName("RR q=8");
        Scheduler SCrr8 = new RoundRobin();
        PriorityQueue<Process> Qrr8 = SCrr8.schedule(processes, 8);
        vulSeriesNorm(Qrr8, Srr8);
        Graph.getData().add(Srr8);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesNorm(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiLevelFeedback();
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes);
        vulSeriesNorm(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    //functie om Wachttijd uit te zetten in functie van de bedieningstijd
    public void Wait(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Wachttijd bij " + amount + " processen");

        //assen opstellen
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Percentiel bedieningstijd");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(100);
        xAxis.setTickUnit(10);
        yAxis.setLabel("Wachttijd");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(2000);
        yAxis.setTickUnit(200);

        //grafiek aanmaken
        final LineChart<Number, Number> Graph = new LineChart<>(xAxis, yAxis);
        Graph.setTitle("Wachttijd bij " + amount + " processen");
        Graph.setCreateSymbols(false);

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();
        Sfcfs.setName("FCFS");
        Scheduler SCfcfs = new FirstComeFirstServed();
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesWait(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new ShortestJobFirst();
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesWait(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new ShortestRemainingTime();
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesWait(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR2
        XYChart.Series Srr2 = new XYChart.Series();
        Srr2.setName("RR q=2");
        Scheduler SCrr2 = new RoundRobin();
        PriorityQueue<Process> Qrr2 = SCrr2.schedule(processes, 2);
        vulSeriesWait(Qrr2, Srr2);
        Graph.getData().add(Srr2);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR q=4");
        Scheduler SCrr4 = new RoundRobin();
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesWait(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //RR8
        XYChart.Series Srr8 = new XYChart.Series();
        Srr8.setName("RR q=8");
        Scheduler SCrr8 = new RoundRobin();
        PriorityQueue<Process> Qrr8 = SCrr8.schedule(processes, 8);
        vulSeriesWait(Qrr8, Srr8);
        Graph.getData().add(Srr8);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new HighestResponseRatioNext();
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesWait(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiLevelFeedback();
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes, 2);
        vulSeriesWait(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    //functie om de grafieken mee op te stellen voor de Genormaliseerde omlooptijd
    public void vulSeriesNorm(PriorityQueue<Process> queue, XYChart.Series series) {
        Process p;
        int aantal = 0;
        int percentielSize = queue.size() / 100;

        double tatnorm = 0;
        double excute = 0;

        while (!queue.isEmpty()) {
            p = queue.poll();
            tatnorm += p.getTatnorm();
            excute += p.getServicetimeneeded();

            if (aantal % percentielSize == 0 && aantal != 0) {
                tatnorm = tatnorm / percentielSize ;
                excute = excute / percentielSize;
                series.getData().add(new XYChart.Data(excute/4, tatnorm));
                tatnorm = 0;
                excute = 0;
            }
            aantal++;
        }
    }

    //functie om de grafieken mee op te stellen voor de Wachttijd
    public void vulSeriesWait(PriorityQueue<Process> queue, XYChart.Series series) {
        Process p;
        int aantal = 0;
        int percentielSize = queue.size() / 100;

        double waittime = 0;
        double excute = 0;

        while (!queue.isEmpty()) {

            p = queue.poll();
            waittime += p.getWaittime();
            excute += p.getServicetimeneeded();

            //per percentiel toevoegen
            if (aantal % percentielSize == 0 && aantal != 0) {
                waittime = waittime / percentielSize;
                excute = excute / percentielSize;
                series.getData().add(new XYChart.Data(excute/4, waittime));
                waittime = 0;
                excute = 0;
            }
            aantal++;
        }
    }
}
