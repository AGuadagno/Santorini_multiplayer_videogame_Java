package it.polimi.ingsw.PSP25;

public class Board {
    private Space[][] spaceMatrix = new Space[5][5];

    public Board() {
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                spaceMatrix[i][j] = new Space(i,j);
            }
        }
    }

    public Space getSpace(int x, int y){
        return spaceMatrix[x][y];
    }

    public void print(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){

                int cellnum = (5*i)+j;

                System.out.print( "#cell " + cellnum + " - TH " + spaceMatrix[i][j].getTowerHeight() + " "
                                   + (spaceMatrix[i][j].getWorker()!=null?spaceMatrix[i][j].getWorker().getPlayer().getName():"") + " "
                                   + (spaceMatrix[i][j].hasDome()?"D":"") + "  ||  ");

            }
            System.out.println("");
        }
        System.out.println();
    }

}
