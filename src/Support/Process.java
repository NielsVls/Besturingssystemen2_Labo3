package Support;

public class Process extends ReadXML implements Cloneable, Comparable {

    private int id, starttijd, omlooptijd, wachttijd, servicetijd, eindtijd, servicetijdNodig, aankomsttijd, prioriteit;
    private double normomlooptijd, responsRatio;

    public Process(int id, int aankomsttijd, int servicetijd) {
        this.id = id;
        this.aankomsttijd = aankomsttijd;
        this.servicetijd = servicetijd;
        this.servicetijdNodig = servicetijd;
        this.responsRatio = 0;
        this.prioriteit = 0;
        this.starttijd = -1;
    }
    public Process(Process p) {
        this.aankomsttijd = p.aankomsttijd;
        this.servicetijd = p.servicetijd;
        this.servicetijdNodig = p.servicetijdNodig;
        this.id = p.id;
        this.responsRatio = p.responsRatio;
    }

    //getters
    public int getAankomsttijd() {
        return aankomsttijd;
    }
    public int getServicetijd() {
        return servicetijd;
    }
    public int getStarttijd() {
        return starttijd;
    }
    public int getOmlooptijd() {
        return omlooptijd;
    }
    public double getNormomlooptijd() {
        return normomlooptijd;
    }
    public int getWachttijd() {
        return wachttijd;
    }
    public int getServicetijdNodig() {
        return servicetijdNodig;
    }
    public double getResponsRatio() {
        return responsRatio;
    }
    public int getPrioriteit() {
        return prioriteit;
    }

    //setters
    public void setAankomsttijd(int aankomsttijd) {
        this.aankomsttijd = aankomsttijd;
    }
    public void setServicetijd(int servicetijd) {
        this.servicetijd = servicetijd;
    }
    public void setStarttijd(int starttijd) {
        this.starttijd = starttijd;
    }
    public void setEindtijd(int eindtijd) {
        this.eindtijd = eindtijd;
    }
    public void setResponsRatio(double responsRatio) {
        this.responsRatio = responsRatio;
    }
    public void setPrioriteit(int prioriteit) {
        this.prioriteit = prioriteit;
    }

    //other methods
    public void decreaseServicetime() {
        servicetijd--;
    }
    public void increasePriority() {
        prioriteit++;
    }
    public void calculate() {
        this.omlooptijd = eindtijd - aankomsttijd;
        this.normomlooptijd = (double) this.omlooptijd / servicetijdNodig;
        this.wachttijd = eindtijd - aankomsttijd - servicetijdNodig;
    }

    //compare method
    @Override
    public int compareTo(Object o) {
        Process p = (Process) o;
        return this.servicetijdNodig < p.servicetijdNodig ? -1 : 1;
    }
}

