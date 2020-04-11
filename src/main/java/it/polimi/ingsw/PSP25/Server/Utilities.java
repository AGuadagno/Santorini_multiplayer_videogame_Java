package it.polimi.ingsw.PSP25.Server;

import it.polimi.ingsw.PSP25.GodPower;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static List<String> deepCopyGodPowerNames(List<GodPower> original) {
        List<String> copied = new ArrayList<>();
        for (GodPower g : original) {
            copied.add(g.toString());
        }
        return copied;
    }
}
