package Support;

public class Process extends ReadXML implements Cloneable, Comparable {

    private int id, starttime, tat, waittime, servicetime, endtime, servicetimeneeded, arrivaltime, priority;
    private double tatnorm, responseRatio;

    public Process(int id, int arrivaltime, int servicetime) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.servicetimeneeded = servicetime;
        this.responseRatio = 0;
        this.priority = 0;
        this.starttime= -1;
    }
    public Process(Process p) {
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.servicetimeneeded = p.servicetimeneeded;
        this.id = p.id;
        this.responseRatio = p.responseRatio;
    }

    //getters
    public int getArrivaltime() {
        return arrivaltime;
    }
    public int getServicetime() {
        return servicetime;
    }
    public int getStarttime() {
        return starttime;
    }
    public int getTat() {
        return tat;
    }
    public double getTatnorm() {
        return tatnorm;
    }
    public int getWaittime() {
        return waittime;
    }
    public int getServicetimeneeded() {
        return servicetimeneeded;
    }
    public double getResponseRatio() {
        return responseRatio;
    }
    public int getPriority() {
        return priority;
    }

    //setters
    public void setArrivaltime(int arrivaltime) {
        this.arrivaltime = arrivaltime;
    }
    public void setServicetime(int servicetime) {
        this.servicetime = servicetime;
    }
    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }
    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }
    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    //other methods
    public void decreaseServicetime() {
        servicetime--;
    }
    public void increasePriority() {
        priority++;
    }
    public void calculate() {
        this.tat = endtime - arrivaltime;
        this.tatnorm = (double) this.tat / servicetimeneeded;
        this.waittime = endtime - arrivaltime - servicetimeneeded;
    }

    //compare method
    @Override
    public int compareTo(Object o) {
        Process p = (Process) o;
        return this.servicetimeneeded < p.servicetimeneeded ? -1 : 1;
    }
}

