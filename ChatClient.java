import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost"; // Endereço do servidor
    private static final int SERVER_PORT = 12345; // Porta do servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);

            // Thread para ler mensagens do servidor
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao ler do servidor: " + e.getMessage());
                }
            }).start();

            // Enviar nome do usuário
            System.out.println("Digite seu nome:");
            String name = scanner.nextLine();
            out.println(name);

            // Ler e enviar mensagens para o servidor
            String userMessage;
            while (scanner.hasNextLine()) {
                userMessage = scanner.nextLine();
                out.println(userMessage);
            }
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}
