package Algorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import Support.Process;

public class MultiLevelFeedback extends Scheduler {

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
        int tijdslot = 0;

        for (Process process : input) {
            queue.add(new Process(process));
        }


        int huidigeSlice = 0;
        int hulpteller = 0;
        boolean swap = false;

        while (voltooid.size() != input.size()) {

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
                if (!(queue.peek().getAankomsttijd() <= tijdslot)) break;
                wachtende0.add(queue.poll());
            }

            if (swap) {
                if (!huidige.isEmpty()) {
                    hulp = huidige.poll();
                    if (hulp.getServicetijd() == 0) {

                        hulp.setEindtijd(tijdslot);
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
                        hulp.setStarttijd(tijdslot);
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
            tijdslot++;
        }

        //gemiddeldes berekenen
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("MLF: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q, 1);
    }

}
