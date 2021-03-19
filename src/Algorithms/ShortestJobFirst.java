package Algorithms;

import Support.Process;

import java.util.*;

public class ShortestJobFirst extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }


        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10,(a, b)->a.getServicetime()-b.getServicetime());

        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;

        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());

            //process uitvoeren als er één klaar is om uitgevoerd te worden, vervolgens de count aanpassen
            if (!waitingProcesses.isEmpty()) {
                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parmeters instellen
                temp.setStarttime(count);
                count += temp.getServicetime();
                temp.setEndtime(count);

                //lokale parameters berekenen
                temp.calculate();

                //process toevoegen aan de finished lijst
                finishedProcesses.add(temp);

                //globale parameters updaten
                waittime += temp.getWaittime();
                tatnorm += temp.getTatnorm();
                tat += temp.getTat();
            }else {
                count++;
            }

        }

        waittime = waittime / input.size();
        tatnorm = tatnorm / input.size();
        tat = tat / input.size();

        System.out.println("SJF: \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
