package Algorithms;

import Support.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MultiRR extends Scheduler{

    int aantal;
    ArrayList<Integer> processors;

    public MultiRR(int aantal){this.aantal = aantal;
         processors = new ArrayList<Integer>();
         for (int i = 0;i<aantal;i++){
             processors.add(i,0);
         }
    }


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

        int Mintijdslot = processors.indexOf(Collections.min(processors));
        

        boolean processVoltooid = false;
        boolean swap =true;

        while(voltooid.size()!=inputSize){

            Mintijdslot = processors.indexOf(Collections.min(processors));

            //processen toevoegen die gearriveerd zijn.
            while(!queue.isEmpty() && queue.peek().getAankomsttijd()<=processors.get(Mintijdslot) ){
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
            if(processors.get(Mintijdslot)%slice==0){
                swap = true;
            }

            //processor leeg maken als swap toegestaan is
            if(swap && huidige!=null){
                hulp=huidige;
                huidige=null;
                if(processVoltooid){
                    hulp.setEindtijd(processors.get(Mintijdslot));
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
                        hulp.setStarttijd(processors.get(Mintijdslot));
                    }
                    huidige=hulp;
                }else if(!queue.isEmpty()){
                    hulp=queue.poll();
                    processors.set(Mintijdslot, hulp.getAankomsttijd());
                    hulp.setStarttijd(processors.get(Mintijdslot));
                    huidige=hulp;
                }
                swap=false;
            }
            processors.set(Mintijdslot,processors.get(Mintijdslot)+1);
        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        omlooptijd = omlooptijd / aantal;

        System.out.println("RR " + slice + ": \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

}
