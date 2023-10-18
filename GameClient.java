import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    public static void main ( String... args ) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter server socket's port: ");
            String userInput = scanner.nextLine(); 
            int port = Integer.parseInt(userInput);
            Socket clientSocket = new Socket("localhost", port); // Connect to the server


            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String serverIP = in.readLine();
            System.out.println("Server socket: "+serverIP);

            System.out.println("Waiting for player 1.");
            String player1 = in.readLine();
            System.out.println("Player2, you will be playing with "+ player1 +" please enter your name: ");
            String player2 = scanner.nextLine(); 
            out.println(player2);

            String message;
            for (int round = 1; round <= 3; round++) {
                message = in.readLine();
                System.out.println(message);

                message = in.readLine();
                System.out.println(message);
                //System.out.println(player2+", please enter your x and y guesses, separated by one space.");
                String player2Guess = scanner.nextLine();
                out.println(player2Guess);
                message = in.readLine();
                System.out.println(message);
            }
            message = in.readLine();
            System.out.println(message);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}