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
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10,(a, b)->a.getServicetime()-b.getServicetime());
        PriorityQueue<Process> huidige = new PriorityQueue<>();
        Process hulp;

        while(voltooid.size()!= input.size()){

            if(!huidige.isEmpty()){

                hulp=huidige.peek();
                hulp.decreaseServicetime();

                if (hulp.getServicetime()==0){
                    hulp=huidige.poll();

                    assert hulp != null;
                    hulp.setEndtime(tijdslot);
                    hulp.calculate();

                    voltooid.add(hulp);

                    waittime += hulp.getWaittime();
                    tatnorm += hulp.getTatnorm();
                    tat += hulp.getTat();
                }
            }

            while(queue.peek() != null && queue.peek().getArrivaltime()<=tijdslot)
                wachtende.add(queue.poll());

            if(huidige.isEmpty() && !wachtende.isEmpty()){

                hulp=wachtende.poll();

                hulp.setStarttime(tijdslot);

                huidige.add(hulp);

            } else if (!huidige.isEmpty() && !wachtende.isEmpty()){
                hulp=huidige.peek();


                if(hulp.getServicetimeneeded()>wachtende.peek().getServicetimeneeded()){
                    hulp=huidige.poll();
                    Process process=wachtende.peek();
                    assert process != null;
                    if(process.getStarttime()==0)
                        process.setStarttime(tijdslot);

                    huidige.add(wachtende.poll());
                    wachtende.add(hulp);
                }
            }

            tijdslot++;

        }
        waittime = waittime / input.size();
        tatnorm = tatnorm / input.size();
        tat = tat / input.size();

        System.out.println("SRT: \tWachttijd: " + waittime + "   \tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
