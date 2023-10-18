import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class GameServer {
    public static void main ( String... args ) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter welcoming socket's port: ");
            String userInput = scanner.nextLine(); 
            int port = Integer.parseInt(userInput);

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for a client..."); 
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            Random random = new Random();
            int x = random.nextInt(256);
            int y = random.nextInt(256);

            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            
            InetAddress clientAddress = clientSocket.getInetAddress();
            String clientIP = clientAddress.getHostAddress();
            System.out.println("Client socket: "+clientIP);

            InetAddress serverAddress = clientSocket.getLocalAddress();
            String serverIP = serverAddress.getHostAddress();
            out.println(serverIP);

            System.out.println("Player 1, please enter your name: ");
            String player1 = scanner.nextLine(); 
            out.println(player1);
            System.out.println("Waiting for player 2 name...");
            String player2 = in.readLine();
            System.out.println("You are playing with "+player2);
            
            int p1points = 0;
            int p2points = 0;

            String message;
            String waitMessage;
            for (int round = 1; round <= 3; round++) {
                waitMessage= "Waiting for player 1 guess...";
                out.println(waitMessage);
                System.out.println(player1+", please enter your x and y guesses, separated by one space.");
                String player1Guess = scanner.nextLine();
                
                String[] player1Guesses = player1Guess.split(" ");
                int player1X = Integer.parseInt(player1Guesses[0]);
                int player1Y = Integer.parseInt(player1Guesses[1]);

                waitMessage= player2+", please enter your x and y guesses, separated by one space.";
                out.println(waitMessage);

                System.out.println("Waiting for player 2 guess..."); 
                String player2Guess = in.readLine();
                String[] player2Guesses = player2Guess.split(" ");
                int player2X = Integer.parseInt(player2Guesses[0]);
                int player2Y = Integer.parseInt(player2Guesses[1]);

                int dist1 = distance(x, y, player1X, player1Y);
                int dist2 = distance(x, y, player2X, player2Y);
                
                if(dist1==dist2){
                    p1points++;
                    p2points++;
                    message="Winner for round "+round+" is both players";
                    System.out.println(message);
                    out.println(message);
                    
                }
                else if(dist1>dist2){
                    p2points++;
                    message="Winner for round "+round+" is "+player2;
                    System.out.println(message);
                    out.println(message);
                }
                else if(dist1<dist2){
                    p1points++;
                    message="Winner for round "+round+" is "+player1;
                    System.out.println(message);
                    out.println(message);
                }
            }
            if(p1points>p2points){
                message="Game Winner is "+player1;
                System.out.println(message);
                out.println(message);
            }
            else if(p1points<p2points){
                message="Game Winner is "+player2;
                System.out.println(message);
                out.println(message);
            }
            else if(p1points==p2points){
                message="Game Winner is both players";
                System.out.println(message);
                out.println(message);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
}