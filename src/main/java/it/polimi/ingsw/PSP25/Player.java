package it.polimi.ingsw.PSP25;

public class Player {
    private String name;
    private Worker worker1;
    private Worker worker2;
    private GodPower godPower;

    public Player(String name) {
        this.name = name;
    }

    public void initializeWorkers(Space space1, Space space2) {
        this.worker1 = new Worker(space1, this);
        space1.setWorker(worker1);
        this.worker2 = new Worker(space2, this);
        space2.setWorker(worker2);
    }

    public Worker getWorker1() {
        return worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    public void initializeGodPower(GodPower godPower) {
        this.godPower = godPower;
    }

    public GodPower getGodPower() {
        return this.godPower;
    }

    public String getName() {
        return this.name;
    }

    public String getID() {
        try {
            return name.substring(0, 3);
        } catch (Exception e) {
            return name + "00";
        }
    }
}
