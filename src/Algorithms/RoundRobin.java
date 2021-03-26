package Algorithms;

import Support.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin extends Scheduler{

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q) {
        return null;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> queue, int slice) {
        int inputSize = queue.size();

        Queue<Process> que = new LinkedList<>();

        for (Process process : queue) {
            que.add(new Process(process));
        }

        PriorityQueue<Process> readyQue = new PriorityQueue<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        Process currentScheduledProcess = null;
        Process tempProcess;

        int counter = 0;

        boolean processFinished = false;
        boolean swap =true;

        while(finishedProcesses.size()!=inputSize){

            //processen toevoegen die gearriveerd zijn.
            while(!que.isEmpty() && que.peek().getArrivaltime()<=counter ){
                readyQue.add(que.poll());
            }

            //voorwaarden worden gecheckt voor een swap
            if (currentScheduledProcess!=null){
                currentScheduledProcess.decreaseServicetime();
                if(currentScheduledProcess.getServicetime()==0){
                    processFinished = true;
                    swap= true;
                }
            }
            if(counter%slice==0){
                swap = true;
            }

            //processor leeg maken als swap toegestaan is
            if(swap && currentScheduledProcess!=null){
                tempProcess=currentScheduledProcess;
                currentScheduledProcess=null;
                if(processFinished){
                    tempProcess.setEndtime(counter);
                    tempProcess.calculate();

                    finishedProcesses.add(tempProcess);

                    //globale parameters updaten
                    waittime += tempProcess.getWaittime();
                    tatnorm += tempProcess.getTatnorm();
                    tat += tempProcess.getTat();
                    processFinished=false;
                }else {
                    readyQue.add(tempProcess);
                }
                tempProcess=null;
            }

            //process op de processor zetten en counter juist zetten
            if(swap){
                if(!readyQue.isEmpty()){
                    tempProcess=readyQue.poll();
                    if(tempProcess.getStarttime()==-1){
                        tempProcess.setStarttime(counter);
                    }
                    currentScheduledProcess=tempProcess;
                }else if(!que.isEmpty()){
                    tempProcess=que.poll();
                    counter=tempProcess.getArrivaltime();
                    tempProcess.setStarttime(counter);
                    currentScheduledProcess=tempProcess;
                }
                tempProcess=null;
                swap=false;
            }
            counter++;
        }

        waittime = waittime / queue.size();
        tatnorm = tatnorm / queue.size();
        tat = tat / queue.size();

        System.out.println("RR " + slice + ": \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

}
