package it.polimi.ingsw.PSP25;

public class Athena extends GodPower {
    public Athena(ActiveEffects activeEffects) {
        super(activeEffects);
    }

    @Override
    public boolean canMove(Worker worker, Space space) {
        //DEBUG
        System.out.println("AthenaEffect.canMove() WorkerPos: " + worker.getSpace().getNumber() + " TargetSpace: " +
                space.getNumber());
        //END DEBUG
        if (worker.getSpace().getTowerHeight() < space.getTowerHeight())
            return false;
        else
            return true;
    }

    @Override
    protected void addActiveEffects(ActiveEffects activeEffects, Worker worker1, Worker worker2, Worker selectedWorker) {
        if (selectedWorker.getHeightBeforeMove() < selectedWorker.getSpace().getTowerHeight())
            activeEffects.pushEffect(this);
        else
            activeEffects.pushEffect(new GodPower(activeEffects));
    }
}
