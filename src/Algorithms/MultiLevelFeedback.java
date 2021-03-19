package Algorithms;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import Support.Process;

public class MultiLevelFeedback extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {
        Queue<Process> inputQue = new LinkedList<>();
        PriorityQueue<Process> waitingQue0 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue1 = new PriorityQueue<>();
        PriorityQueue<Process> waitingQue2 = new PriorityQueue<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();
        int slice2 = slice * 2;
        Process temp;
        int timeslot = 0;

        for (Process p : input) {
            inputQue.add(new Process(p));
        }


        int currentSlice = 0;
        int tempCounter = 0;
        boolean swap = false;

        while (finishedProcesses.size() != input.size()) {

            if (!currentProcess.isEmpty()) {
                currentProcess.peek().decreaseServicetime();
                tempCounter++;
            }

            if (currentSlice == tempCounter)
                swap = true;
            else if (!currentProcess.isEmpty()) {
                if (currentProcess.peek().getServicetime() == 0)
                    swap = true;
            }

            while (inputQue.peek() != null) {
                assert inputQue.peek() != null;
                if (!(inputQue.peek().getArrivaltime() <= timeslot)) break;
                waitingQue0.add(inputQue.poll());
            }

            if (swap) {
                if (!currentProcess.isEmpty()) {
                    temp = currentProcess.poll();
                    if (temp.getServicetime() == 0) {

                        temp.setEndtime(timeslot);
                        temp.calculate();
                        currentSlice = 0;
                        tempCounter = 0;
                        finishedProcesses.add(temp);
                        waittime += temp.getWaittime();
                        tatnorm += temp.getTatnorm();
                        tat += temp.getTat();

                    } else {
                        temp.increasePriority();
                        int tempPrior = temp.getPriority();
                        if (tempPrior == 1)
                            waitingQue1.add(temp);
                        else if (tempPrior == 2)
                            waitingQue2.add(temp);

                    }

                } else {
                    if (!waitingQue0.isEmpty()) {
                        temp = waitingQue0.poll();
                        temp.setStarttime(timeslot);
                        temp.setPriority(0);
                        currentSlice = slice;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    } else if (!waitingQue1.isEmpty()) {
                        temp = waitingQue1.poll();
                        temp.setPriority(1);
                        currentSlice = slice2;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    } else if (!waitingQue2.isEmpty()) {
                        temp = waitingQue2.poll();
                        temp.setPriority(2);
                        currentSlice = -1;
                        tempCounter = 0;
                        currentProcess.add(temp);
                    }
                }
                swap = false;
            }
            timeslot++;
        }

        //gemiddeldes berekenen
        waittime = waittime / input.size();
        tatnorm = tatnorm / input.size();
        tat = tat / input.size();

        System.out.println("MLF: \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return schedule(q, 1);
    }

}
