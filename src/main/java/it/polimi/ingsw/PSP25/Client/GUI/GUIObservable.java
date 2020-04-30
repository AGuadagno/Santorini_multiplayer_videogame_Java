package it.polimi.ingsw.PSP25.Client.GUI;

import it.polimi.ingsw.PSP25.Client.ViewObserver;

public interface GUIObservable {
    void subscribe(ViewObserver o);
}
