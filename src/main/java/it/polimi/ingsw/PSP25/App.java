package it.polimi.ingsw.PSP25;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println( "The game begins." );
        int x1;
        int y1;
        int x2;
        int y2;

        //Players creation
        Player p1 = new Player("Federico");
        p1.initializeGodPower(new GodPower());
        Player p2 = new Player("Claudio");
        p2.initializeGodPower(new GodPower());

        //Board creation
        Board board = new Board();
        board.getSpace(0,0).setBoard(board);
        board.print();

        System.out.println(p1.getName() + " it's your turn! Choose the position of your first worker");
        x1=scanner.nextInt();
        y1=scanner.nextInt();
        System.out.println("And now chose the position of your second worker");
        x2=scanner.nextInt();
        y2=scanner.nextInt();

        p1.initializeWorkers(board.getSpace(x1,y1), board.getSpace(x2,y2));

        System.out.println("Player 1, worker 1 : " + p1.getWorker1().getSpace().toString());
        System.out.println("Player 1, worker 2 : " + p1.getWorker2().getSpace().toString());
        System.out.println();
        board.print();


        System.out.println(p2.getName() + " it's your turn! Choose the position of your first worker");
        x1=scanner.nextInt();
        y1=scanner.nextInt();
        System.out.println("And now chose the position of your second worker");
        x2=scanner.nextInt();
        y2=scanner.nextInt();

        p2.initializeWorkers(board.getSpace(x1,y1), board.getSpace(x2,y2));

        System.out.println("Player 2, worker 1 : " + p2.getWorker1().getSpace().toString());
        System.out.println("Player 2, worker 2 : " + p2.getWorker2().getSpace().toString());
        System.out.println();
        board.print();

        boolean endGame = false;
        Player nowPlaying = p1;

        while(!endGame){
            endGame = nowPlaying.getGodPower().turnSequence(nowPlaying);
            System.out.println();
            board.print();
            System.out.println();
            if(nowPlaying==p1){
                nowPlaying=p2;
            }
            else{
                nowPlaying=p1;
            }
        }

    }
}
