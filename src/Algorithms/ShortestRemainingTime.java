package Algorithms;

import Support.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestRemainingTime extends Scheduler {


    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {

        int tijdslot = 0;

        Queue<Process> queue = new LinkedList<>();

        for (Process process : input) {
            queue.add(new Process(process));
        }

        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10,(a, b)->a.getServicetijd()-b.getServicetijd());
        PriorityQueue<Process> huidige = new PriorityQueue<>();
        Process hulp;

        while(voltooid.size()!= input.size()){

            if(!huidige.isEmpty()){

                hulp=huidige.peek();
                hulp.decreaseServicetime();

                if (hulp.getServicetijd()==0){
                    hulp=huidige.poll();

                    assert hulp != null;
                    hulp.setEindtijd(tijdslot);
                    hulp.calculate();

                    voltooid.add(hulp);

                    wachttijd += hulp.getWachttijd();
                    normomlooptijd += hulp.getNormomlooptijd();
                    omlooptijd += hulp.getOmlooptijd();
                }
            }

            while(queue.peek() != null && queue.peek().getAankomsttijd()<=tijdslot)
                wachtende.add(queue.poll());

            if(huidige.isEmpty() && !wachtende.isEmpty()){

                hulp=wachtende.poll();

                hulp.setStarttijd(tijdslot);

                huidige.add(hulp);

            } else if (!huidige.isEmpty() && !wachtende.isEmpty()){
                hulp=huidige.peek();


                if(hulp.getServicetijdNodig()>wachtende.peek().getServicetijdNodig()){
                    hulp=huidige.poll();
                    Process process=wachtende.peek();
                    assert process != null;
                    if(process.getStarttijd()==0)
                        process.setStarttijd(tijdslot);

                    huidige.add(wachtende.poll());
                    wachtende.add(hulp);
                }
            }

            tijdslot++;

        }
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("SRT: \tWachttijd: " + wachttijd + "   \tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
