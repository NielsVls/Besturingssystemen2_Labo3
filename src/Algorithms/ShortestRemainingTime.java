package Algorithms;

import Support.Process;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The class ShortestRemainingTime is an extension class of the abstract class Scheduler
 * The three methods of the Scheduler have to be overwritten.
 * @author pellereyniers and wouterlegiest
 */
public class ShortestRemainingTime extends Scheduler {

    /**
     * Method for executing the algorithm based on a single input que
     * Preferred method for the ShortestRemainingTime algorithm.
     * @param input The input que, contains all processes
     * @return que of finished processes with information on their processing
     */
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> que = new LinkedList<>();

        for (Process p : input) {
            que.add(new Process(p));
        }


        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<Process>(10,(a, b)->a.getServicetime()-b.getServicetime());
        PriorityQueue<Process> currentProcess = new PriorityQueue<>();
        Process temp;

        //counter duidt op welk timeslot de processor zich bevindt
        int count = 0;

        //Loop blijft gaan tot alle processen afgerond zijn
        while(finishedProcesses.size()!=input.size()){

            if(!currentProcess.isEmpty()){
                //procces stond al van vorige doorgaan op de processor-> service time needed moet eerst verlaagd worden
                temp=currentProcess.peek();
                temp.decreaseServicetime();
                if (temp.getServicetime()==0){

                    temp=currentProcess.poll();

                    //lokale parameter instellen en andere uitrekenen
                    temp.setEndtime(count);
                    temp.calculate();

                    finishedProcesses.add(temp);

                    //globale parameters updaten
                    waittime += temp.getWaittime();
                    tatnorm += temp.getTatnorm();
                    tat += temp.getTat();
                }
            }

            //check of er processen zijn die aan de wachtrij mogen worden toegevoegd
            while(que.peek() != null && que.peek().getArrivaltime()<=count)
                waitingProcesses.add(que.poll());

          /*  if(waitingProcesses.isEmpty() && currentProcess.isEmpty()){


            }*/
            if(currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                //Uit te voeren process uit de wachtrij halen
                temp=waitingProcesses.poll();

                //Parmeters instellen
                temp.setStarttime(count);

                //SupportClasses.Process op de processor zetten
                currentProcess.add(temp);

            } else if (!currentProcess.isEmpty() && !waitingProcesses.isEmpty()){
                temp=currentProcess.peek();

                //wanneer er processen wachten met lagere servicetime needed -> switchen
                if(temp.getServicetimeneeded()>waitingProcesses.peek().getServicetimeneeded()){
                    temp=currentProcess.poll();
                    Process p=waitingProcesses.peek();
                    if(p.getStarttime()==0)
                        p.setStarttime(count);

                    currentProcess.add(waitingProcesses.poll());
                    waitingProcesses.add(temp);
                }
            }

            count++;

        }
        waittime = waittime / input.size();
        tatnorm = tatnorm / input.size();
        tat = tat / input.size();

        System.out.println("SRT: \tWachttijd: " + waittime + "   \tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
