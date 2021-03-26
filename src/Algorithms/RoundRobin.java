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
    public PriorityQueue<Process> schedule(Queue<Process> input, int slice) {
        int inputSize = input.size();

        Queue<Process> queue = new LinkedList<>();

        for (Process process : input) {
            queue.add(new Process(process));
        }

        PriorityQueue<Process> gereed = new PriorityQueue<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        Process huidige = null;
        Process hulp;

        int tijdslot = 0;

        boolean processVoltooid = false;
        boolean swap =true;

        while(voltooid.size()!=inputSize){

            //processen toevoegen die gearriveerd zijn.
            while(!queue.isEmpty() && queue.peek().getAankomsttijd()<=tijdslot ){
                gereed.add(queue.poll());
            }

            //voorwaarden worden gecheckt voor een swap
            if (huidige!=null){
                huidige.decreaseServicetime();
                if(huidige.getServicetijd()==0){
                    processVoltooid = true;
                    swap= true;
                }
            }
            if(tijdslot%slice==0){
                swap = true;
            }

            //processor leeg maken als swap toegestaan is
            if(swap && huidige!=null){
                hulp=huidige;
                huidige=null;
                if(processVoltooid){
                    hulp.setEindtijd(tijdslot);
                    hulp.calculate();

                    voltooid.add(hulp);

                    //globale parameters updaten
                    wachttijd += hulp.getWachttijd();
                    normomlooptijd += hulp.getNormomlooptijd();
                    omlooptijd += hulp.getOmlooptijd();
                    processVoltooid=false;
                }else {
                    gereed.add(hulp);
                }
            }

            //process op de processor zetten en counter juist zetten
            if(swap){
                if(!gereed.isEmpty()){
                    hulp=gereed.poll();
                    if(hulp.getStarttijd()==-1){
                        hulp.setStarttijd(tijdslot);
                    }
                    huidige=hulp;
                }else if(!queue.isEmpty()){
                    hulp=queue.poll();
                    tijdslot=hulp.getAankomsttijd();
                    hulp.setStarttijd(tijdslot);
                    huidige=hulp;
                }
                swap=false;
            }
            tijdslot++;
        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("RR " + slice + ": \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

}
