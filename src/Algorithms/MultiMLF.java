package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import Support.Process;

public class MultiMLF extends Scheduler {

    int aantal;
    ArrayList<Integer> processors;

    public MultiMLF(int aantal){this.aantal = aantal;
         processors = new ArrayList<Integer>();
         for (int i = 0;i<aantal;i++){
             processors.add(i,0);
         }
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {
        Queue<Process> queue = new LinkedList<>();
        PriorityQueue<Process> wachtende0 = new PriorityQueue<>();
        PriorityQueue<Process> wachtende1 = new PriorityQueue<>();
        PriorityQueue<Process> wachtende2 = new PriorityQueue<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> huidige = new PriorityQueue<>();
        int slice2 = slice * 2;
        Process hulp;

        for (Process process : input) {
            queue.add(new Process(process));
        }


        int huidigeSlice = 0;
        int hulpteller = 0;
        boolean swap = false;

        int Mintijdslot = processors.indexOf(Collections.min(processors));

        while (voltooid.size() != input.size()) {

            Mintijdslot = processors.indexOf(Collections.min(processors));

            if (!huidige.isEmpty()) {
                huidige.peek().decreaseServicetime();
                hulpteller++;
            }

            if (huidigeSlice == hulpteller)
                swap = true;
            else if (!huidige.isEmpty() && huidige.peek().getServicetijd() == 0) {
                    swap = true;
            }

            while (queue.peek() != null) {
                assert queue.peek() != null;
                if (!(queue.peek().getAankomsttijd() <= processors.get(Mintijdslot))) break;
                wachtende0.add(queue.poll());
            }

            if (swap) {
                if (!huidige.isEmpty()) {
                    hulp = huidige.poll();
                    if (hulp.getServicetijd() == 0) {

                        hulp.setEindtijd(processors.get(Mintijdslot));
                        hulp.calculate();
                        huidigeSlice = 0;
                        hulpteller = 0;
                        voltooid.add(hulp);
                        wachttijd += hulp.getWachttijd();
                        normomlooptijd += hulp.getNormomlooptijd();
                        omlooptijd += hulp.getOmlooptijd();

                    } else {
                        hulp.increasePriority();
                        int tempPrior = hulp.getPrioriteit();
                        if (tempPrior == 1)
                            wachtende1.add(hulp);
                        else if (tempPrior == 2)
                            wachtende2.add(hulp);

                    }

                } else {
                    if (!wachtende0.isEmpty()) {
                        hulp = wachtende0.poll();
                        hulp.setStarttijd(processors.get(Mintijdslot));
                        hulp.setPrioriteit(0);
                        huidigeSlice = slice;
                        hulpteller = 0;
                        huidige.add(hulp);
                    } else if (!wachtende1.isEmpty()) {
                        hulp = wachtende1.poll();
                        hulp.setPrioriteit(1);
                        huidigeSlice = slice2;
                        hulpteller = 0;
                        huidige.add(hulp);
                    } else if (!wachtende2.isEmpty()) {
                        hulp = wachtende2.poll();
                        hulp.setPrioriteit(2);
                        huidigeSlice = -1;
                        hulpteller = 0;
                        huidige.add(hulp);
                    }
                }
                swap = false;
            }
            processors.set(Mintijdslot,processors.get(Mintijdslot)+1);
        }

        //gemiddeldes berekenen
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        omlooptijd = omlooptijd / aantal;

        System.out.println("MLF: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q, 1);
    }

}
