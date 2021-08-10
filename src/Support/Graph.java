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
        stage.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 1 processor");

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
        Graph.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 1 processor");
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
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes,4);
        vulSeriesNorm(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }
    
    public void TatNorm2(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 2 processoren");

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
        Graph.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 2 processoren");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();                    //S -> series
        Sfcfs.setName("FCFS");                                          //Q -> Queue
        Scheduler SCfcfs = new MultiFCFS(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesNorm(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new MultiSJF(2);
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesNorm(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new MultiSRT(2);
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesNorm(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR");
        Scheduler SCrr4 = new MultiRR(2);
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesNorm(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new MultiHRRN(2);
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesNorm(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiMLF(2);
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes,4);
        vulSeriesNorm(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void TatNorm4(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 4 processoren");

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
        Graph.setTitle("Genormaliseerde omlooptijd bij " + amount + " processen met 4 processoren");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();                    //S -> series
        Sfcfs.setName("FCFS");                                          //Q -> Queue
        Scheduler SCfcfs = new MultiFCFS(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesNorm(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new MultiSJF(4);
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesNorm(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new MultiSRT(4);
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesNorm(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR");
        Scheduler SCrr4 = new MultiRR(4);
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesNorm(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new MultiHRRN(4);
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesNorm(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiMLF(4);
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes,4);
        vulSeriesNorm(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }


    //functie om Wachttijd uit te zetten in functie van de bedieningstijd
    public void Wacht(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
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
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes, 4);
        vulSeriesWait(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void Wacht2(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Wachttijd bij " + amount + " processen met 2 processoren");

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
        Graph.setTitle("Wachttijd bij " + amount + " processen met 2 processoren");
        Graph.setCreateSymbols(false);

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();
        Sfcfs.setName("FCFS");
        Scheduler SCfcfs = new MultiFCFS(2);
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesWait(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new MultiSJF(2);
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesWait(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new MultiSRT(2);
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesWait(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR");
        Scheduler SCrr4 = new MultiRR(2);
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesWait(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new MultiHRRN(2);
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesWait(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiMLF(2);
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes, 4);
        vulSeriesWait(Qmlf, Smlf);
        Graph.getData().add(Smlf);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void Wacht4(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Wachttijd bij " + amount + " processen met 4 processoren");

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
        Graph.setTitle("Wachttijd bij " + amount + " processen met 4 processoren");
        Graph.setCreateSymbols(false);

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();
        Sfcfs.setName("FCFS");
        Scheduler SCfcfs = new MultiFCFS(4);
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesWait(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs);

        //SJF
        XYChart.Series Ssjf = new XYChart.Series();
        Ssjf.setName("SJF");
        Scheduler SCsjf = new MultiSJF(4);
        PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
        vulSeriesWait(Qsjf, Ssjf);
        Graph.getData().add(Ssjf);

        //SRT
        XYChart.Series Ssrt = new XYChart.Series();
        Ssrt.setName("SRT");
        Scheduler SCsrt = new MultiSRT(4);
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesWait(Qsrt, Ssrt);
        Graph.getData().add(Ssrt);

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();
        Srr4.setName("RR");
        Scheduler SCrr4 = new MultiRR(4);
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes, 4);
        vulSeriesWait(Qrr4, Srr4);
        Graph.getData().add(Srr4);

        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();
        Shrrn.setName("HRRN");
        Scheduler SChrrn = new MultiHRRN(4);
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesWait(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn);

        //MLF
        XYChart.Series Smlf = new XYChart.Series();
        Smlf.setName("MLF");
        Scheduler SCmlf = new MultiMLF(4);
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes, 4);
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

        double normomlooptijd = 0;
        double serviceTijd = 0;

        while (!queue.isEmpty()) {
            p = queue.poll();
            normomlooptijd += p.getNormomlooptijd();
            serviceTijd += p.getServicetijdNodig();

            if (aantal % percentielSize == 0 && aantal != 0) {
                normomlooptijd = normomlooptijd / percentielSize ;
                serviceTijd = serviceTijd / percentielSize;
                series.getData().add(new XYChart.Data(serviceTijd/4, normomlooptijd));
                normomlooptijd = 0;
                serviceTijd = 0;
            }
            aantal++;
        }
    }

    //functie om de grafieken mee op te stellen voor de Wachttijd
    public void vulSeriesWait(PriorityQueue<Process> queue, XYChart.Series series) {
        Process p;
        int aantal = 0;
        int percentielSize = queue.size() / 100;

        double wachttijd = 0;
        double serviceTijd = 0;

        while (!queue.isEmpty()) {

            p = queue.poll();
            wachttijd += p.getWachttijd();
            serviceTijd += p.getServicetijdNodig();

            //per percentiel toevoegen
            if (aantal % percentielSize == 0 && aantal != 0) {
                wachttijd = wachttijd / percentielSize;
                serviceTijd = serviceTijd / percentielSize;
                series.getData().add(new XYChart.Data(serviceTijd/4, wachttijd));
                wachttijd = 0;
                serviceTijd = 0;
            }
            aantal++;
        }
    }

    public void FCFS(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("First Come First Served bij " + amount + " processen");

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
        Graph.setTitle("First Come First Served bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();                    //S -> series
        Sfcfs.setName("FCFS");                                          //Q -> Queue
        Scheduler SCfcfs = new MultiFCFS(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesNorm(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs); 

        //FCFS2
        XYChart.Series Sfcfs2 = new XYChart.Series();                    //S -> series
        Sfcfs2.setName("FCFS2");                                          //Q -> Queue
        Scheduler SCfcfs2 = new MultiFCFS(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs2 = SCfcfs2.schedule(processes);
        vulSeriesNorm(Qfcfs2, Sfcfs2);
        Graph.getData().add(Sfcfs2);

        //FCFS4
        XYChart.Series Sfcfs4 = new XYChart.Series();                    //S -> series
        Sfcfs4.setName("FCFS4");                                          //Q -> Queue
        Scheduler SCfcfs4 = new MultiFCFS(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs4 = SCfcfs4.schedule(processes);
        vulSeriesNorm(Qfcfs4, Sfcfs4);
        Graph.getData().add(Sfcfs4);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void SJF(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
    processes = p.createProcess(amount);
    stage.setTitle("Shortest Job First bij " + amount + " processen");

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
    Graph.setTitle("Shortest Job First bij " + amount + " processen");
    Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

    //algoritmes uitvoeren en toevoegen aan de grafiek
    //SJF
    XYChart.Series Ssjf = new XYChart.Series();                    //S -> series
    Ssjf.setName("SJF");                                          //Q -> Queue
    Scheduler SCsjf = new MultiSJF(1);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
    vulSeriesNorm(Qsjf, Ssjf);
    Graph.getData().add(Ssjf); 

    //SJF2
    XYChart.Series Ssjf2 = new XYChart.Series();                    //S -> series
    Ssjf2.setName("SJF2");                                          //Q -> Queue
    Scheduler SCsjf2 = new MultiSJF(2);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf2 = SCsjf2.schedule(processes);
    vulSeriesNorm(Qsjf2, Ssjf2);
    Graph.getData().add(Ssjf2);

    //SJF4
    XYChart.Series Ssjf4 = new XYChart.Series();                    //S -> series
    Ssjf4.setName("SJF4");                                          //Q -> Queue
    Scheduler SCsjf4 = new MultiSJF(4);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf4 = SCsjf4.schedule(processes);
    vulSeriesNorm(Qsjf4, Ssjf4);
    Graph.getData().add(Ssjf4);

    Scene scene = new Scene(Graph, 1000, 800);
    stage.setScene(scene);
}

    public void HRRN(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Highest Response Ratio Next bij " + amount + " processen");

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
        Graph.setTitle("Highest Response Ratio Next bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();                    //S -> series
        Shrrn.setName("HRRN");                                          //Q -> Queue
        Scheduler SChrrn = new MultiHRRN(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesNorm(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn); 

        //HRRN2
        XYChart.Series Shrrn2 = new XYChart.Series();                    //S -> series
        Shrrn2.setName("HRRN2");                                          //Q -> Queue
        Scheduler SChrrn2 = new MultiHRRN(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn2 = SChrrn2.schedule(processes);
        vulSeriesNorm(Qhrrn2, Shrrn2);
        Graph.getData().add(Shrrn2);

        //HRRN4
        XYChart.Series Shrrn4 = new XYChart.Series();                    //S -> series
        Shrrn4.setName("HRRN4");                                          //Q -> Queue
        Scheduler SChrrn4 = new MultiHRRN(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn4 = SChrrn4.schedule(processes);
        vulSeriesNorm(Qhrrn4, Shrrn4);
        Graph.getData().add(Shrrn4);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void MLF(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Multi Level Feedback bij " + amount + " processen");

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
        Graph.setTitle("Multi Level Feedback bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //MLF
        XYChart.Series Smlf = new XYChart.Series();                    //S -> series
        Smlf.setName("MLF");                                          //Q -> Queue
        Scheduler SCmlf = new MultiMLF(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes,4);
        vulSeriesNorm(Qmlf, Smlf);
        Graph.getData().add(Smlf); 

        //MLF2
        XYChart.Series Smlf2 = new XYChart.Series();                    //S -> series
        Smlf2.setName("MLF2");                                          //Q -> Queue
        Scheduler SCmlf2 = new MultiMLF(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf2 = SCmlf2.schedule(processes,4);
        vulSeriesNorm(Qmlf2, Smlf2);
        Graph.getData().add(Smlf2); 

        //MLF4
        XYChart.Series Smlf4 = new XYChart.Series();                    //S -> series
        Smlf4.setName("MLF4");                                          //Q -> Queue
        Scheduler SCmlf4 = new MultiMLF(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf4 = SCmlf4.schedule(processes,4);
        vulSeriesNorm(Qmlf4, Smlf4);
        Graph.getData().add(Smlf4); 

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void RR(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("ROund Robin bij " + amount + " processen");

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
        Graph.setTitle("Round Robin bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //RR
        XYChart.Series Srr = new XYChart.Series();                    //S -> series
        Srr.setName("RR");                                          //Q -> Queue
        Scheduler SCrr = new MultiRR(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr = SCrr.schedule(processes,4);
        vulSeriesNorm(Qrr, Srr);
        Graph.getData().add(Srr); 

        //RR2
        XYChart.Series Srr2 = new XYChart.Series();                    //S -> series
        Srr2.setName("RR2");                                          //Q -> Queue
        Scheduler SCrr2 = new MultiRR(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr2 = SCrr2.schedule(processes,4);
        vulSeriesNorm(Qrr2, Srr2);
        Graph.getData().add(Srr2); 

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();                    //S -> series
        Srr4.setName("RR4");                                          //Q -> Queue
        Scheduler SCrr4 = new MultiRR(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes,4);
        vulSeriesNorm(Qrr4, Srr4);
        Graph.getData().add(Srr4);  

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void SRT(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Shortest Remaining Time bij " + amount + " processen");
    
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
        Graph.setTitle("Shortest Remaining Time bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)
    
        //algoritmes uitvoeren en toevoegen aan de grafiek
        //SRT
        XYChart.Series Ssrt = new XYChart.Series();                    //S -> series
        Ssrt.setName("SRT");                                          //Q -> Queue
        Scheduler SCsrt = new MultiSRT(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesNorm(Qsrt, Ssrt);
        Graph.getData().add(Ssrt); 
    
        //SRT2
        XYChart.Series Ssrt2 = new XYChart.Series();                    //S -> series
        Ssrt2.setName("SRT2");                                          //Q -> Queue
        Scheduler SCsrt2 = new MultiSRT(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt2 = SCsrt2.schedule(processes);
        vulSeriesNorm(Qsrt2, Ssrt2);
        Graph.getData().add(Ssrt2);
    
        //SRT4
        XYChart.Series Ssrt4 = new XYChart.Series();                    //S -> series
        Ssrt4.setName("SRT4");                                          //Q -> Queue
        Scheduler SCsrt4 = new MultiSRT(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt4 = SCsrt4.schedule(processes);
        vulSeriesNorm(Qsrt4, Ssrt4);
        Graph.getData().add(Ssrt4);
    
        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }
    

    public void WaitFCFS(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("First Come First Served bij " + amount + " processen");

        //assen grafiek opstellen
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
        Graph.setTitle("First Come First Served bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //FCFS
        XYChart.Series Sfcfs = new XYChart.Series();                    //S -> series
        Sfcfs.setName("FCFS");                                          //Q -> Queue
        Scheduler SCfcfs = new MultiFCFS(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs = SCfcfs.schedule(processes);
        vulSeriesWait(Qfcfs, Sfcfs);
        Graph.getData().add(Sfcfs); 

        //FCFS2
        XYChart.Series Sfcfs2 = new XYChart.Series();                    //S -> series
        Sfcfs2.setName("FCFS2");                                          //Q -> Queue
        Scheduler SCfcfs2 = new MultiFCFS(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs2 = SCfcfs2.schedule(processes);
        vulSeriesWait(Qfcfs2, Sfcfs2);
        Graph.getData().add(Sfcfs2);

        //FCFS4
        XYChart.Series Sfcfs4 = new XYChart.Series();                    //S -> series
        Sfcfs4.setName("FCFS4");                                          //Q -> Queue
        Scheduler SCfcfs4 = new MultiFCFS(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qfcfs4 = SCfcfs4.schedule(processes);
        vulSeriesWait(Qfcfs4, Sfcfs4);
        Graph.getData().add(Sfcfs4);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void WaitSJF(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
    processes = p.createProcess(amount);
    stage.setTitle("Shortest Job First bij " + amount + " processen");

    //assen grafiek opstellen
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
    Graph.setTitle("Shortest Job First bij " + amount + " processen");
    Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

    //algoritmes uitvoeren en toevoegen aan de grafiek
    //SJF
    XYChart.Series Ssjf = new XYChart.Series();                    //S -> series
    Ssjf.setName("SJF");                                          //Q -> Queue
    Scheduler SCsjf = new MultiSJF(1);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf = SCsjf.schedule(processes);
    vulSeriesWait(Qsjf, Ssjf);
    Graph.getData().add(Ssjf); 

    //SJF2
    XYChart.Series Ssjf2 = new XYChart.Series();                    //S -> series
    Ssjf2.setName("SJF2");                                          //Q -> Queue
    Scheduler SCsjf2 = new MultiSJF(2);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf2 = SCsjf2.schedule(processes);
    vulSeriesWait(Qsjf2, Ssjf2);
    Graph.getData().add(Ssjf2);

    //SJF4
    XYChart.Series Ssjf4 = new XYChart.Series();                    //S -> series
    Ssjf4.setName("SJF4");                                          //Q -> Queue
    Scheduler SCsjf4 = new MultiSJF(4);                  //SC -> Scheduler
    PriorityQueue<Process> Qsjf4 = SCsjf4.schedule(processes);
    vulSeriesWait(Qsjf4, Ssjf4);
    Graph.getData().add(Ssjf4);

    Scene scene = new Scene(Graph, 1000, 800);
    stage.setScene(scene);
}

    public void WaitHRRN(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Highest Response Ratio Next bij " + amount + " processen");

        //assen grafiek opstellen
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
        Graph.setTitle("Highest Response Ratio Next bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //HRRN
        XYChart.Series Shrrn = new XYChart.Series();                    //S -> series
        Shrrn.setName("HRRN");                                          //Q -> Queue
        Scheduler SChrrn = new MultiHRRN(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn = SChrrn.schedule(processes);
        vulSeriesWait(Qhrrn, Shrrn);
        Graph.getData().add(Shrrn); 

        //HRRN2
        XYChart.Series Shrrn2 = new XYChart.Series();                    //S -> series
        Shrrn2.setName("HRRN2");                                          //Q -> Queue
        Scheduler SChrrn2 = new MultiHRRN(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn2 = SChrrn2.schedule(processes);
        vulSeriesWait(Qhrrn2, Shrrn2);
        Graph.getData().add(Shrrn2);

        //HRRN4
        XYChart.Series Shrrn4 = new XYChart.Series();                    //S -> series
        Shrrn4.setName("HRRN4");                                          //Q -> Queue
        Scheduler SChrrn4 = new MultiHRRN(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qhrrn4 = SChrrn4.schedule(processes);
        vulSeriesWait(Qhrrn4, Shrrn4);
        Graph.getData().add(Shrrn4);

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void WaitMLF(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Multi Level Feedback bij " + amount + " processen");

        //assen grafiek opstellen
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
        Graph.setTitle("Multi Level Feedback bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //MLF
        XYChart.Series Smlf = new XYChart.Series();                    //S -> series
        Smlf.setName("MLF");                                          //Q -> Queue
        Scheduler SCmlf = new MultiMLF(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf = SCmlf.schedule(processes,4);
        vulSeriesWait(Qmlf, Smlf);
        Graph.getData().add(Smlf); 

        //MLF2
        XYChart.Series Smlf2 = new XYChart.Series();                    //S -> series
        Smlf2.setName("MLF2");                                          //Q -> Queue
        Scheduler SCmlf2 = new MultiMLF(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf2 = SCmlf2.schedule(processes,4);
        vulSeriesWait(Qmlf2, Smlf2);
        Graph.getData().add(Smlf2); 

        //MLF4
        XYChart.Series Smlf4 = new XYChart.Series();                    //S -> series
        Smlf4.setName("MLF4");                                          //Q -> Queue
        Scheduler SCmlf4 = new MultiMLF(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qmlf4 = SCmlf4.schedule(processes,4);
        vulSeriesWait(Qmlf4, Smlf4);
        Graph.getData().add(Smlf4); 

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void WaitRR(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Round Robin bij " + amount + " processen");

        //assen grafiek opstellen
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
        Graph.setTitle("Round Robin bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)

        //algoritmes uitvoeren en toevoegen aan de grafiek
        //RR
        XYChart.Series Srr = new XYChart.Series();                    //S -> series
        Srr.setName("RR");                                          //Q -> Queue
        Scheduler SCrr = new MultiRR(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr = SCrr.schedule(processes,4);
        vulSeriesWait(Qrr, Srr);
        Graph.getData().add(Srr); 

        //RR2
        XYChart.Series Srr2 = new XYChart.Series();                    //S -> series
        Srr2.setName("RR2");                                          //Q -> Queue
        Scheduler SCrr2 = new MultiRR(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr2 = SCrr2.schedule(processes,4);
        vulSeriesWait(Qrr2, Srr2);
        Graph.getData().add(Srr2); 

        //RR4
        XYChart.Series Srr4 = new XYChart.Series();                    //S -> series
        Srr4.setName("RR4");                                          //Q -> Queue
        Scheduler SCrr4 = new MultiRR(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qrr4 = SCrr4.schedule(processes,4);
        vulSeriesWait(Qrr4, Srr4);
        Graph.getData().add(Srr4); 

        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }

    public void WaitSRT(Stage stage, int amount) throws ParserConfigurationException, SAXException, IOException {
        processes = p.createProcess(amount);
        stage.setTitle("Shortest Remaining Time bij " + amount + " processen");
    
        //assen grafiek opstellen
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
        Graph.setTitle("Shortest Remaining Time bij " + amount + " processen");
        Graph.setCreateSymbols(false); //dit zorgt ervoor dat er geen bollen worden uitgezet (maakt de grafiek overzichtelijker)
    
        //algoritmes uitvoeren en toevoegen aan de grafiek
        //SRT
        XYChart.Series Ssrt = new XYChart.Series();                    //S -> series
        Ssrt.setName("SRT");                                          //Q -> Queue
        Scheduler SCsrt = new MultiSRT(1);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt = SCsrt.schedule(processes);
        vulSeriesWait(Qsrt, Ssrt);
        Graph.getData().add(Ssrt); 
    
        //SRT2
        XYChart.Series Ssrt2 = new XYChart.Series();                    //S -> series
        Ssrt2.setName("SRT2");                                          //Q -> Queue
        Scheduler SCsrt2 = new MultiSRT(2);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt2 = SCsrt2.schedule(processes);
        vulSeriesWait(Qsrt2, Ssrt2);
        Graph.getData().add(Ssrt2);
    
        //SRT4
        XYChart.Series Ssrt4 = new XYChart.Series();                    //S -> series
        Ssrt4.setName("SRT4");                                          //Q -> Queue
        Scheduler SCsrt4 = new MultiSRT(4);                  //SC -> Scheduler
        PriorityQueue<Process> Qsrt4 = SCsrt4.schedule(processes);
        vulSeriesWait(Qsrt4, Ssrt4);
        Graph.getData().add(Ssrt4);
    
        Scene scene = new Scene(Graph, 1000, 800);
        stage.setScene(scene);
    }
    
}

