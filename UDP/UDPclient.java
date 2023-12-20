import java.net.*;
import java.util.Scanner;

public class UDPclient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Одберете хост и порта за клиентот
            String host = "127.0.0.1";
            int port = 12345;

            // Креирајте сокет објект
            socket = new DatagramSocket();

            while (true) {
                // Внесете порака од тастатура
                System.out.print("Внесете порака (KRAJ за крај): ");
                String message = scanner.nextLine();

                // Претворете го текстот во бајтови
                byte[] sendBuffer = message.getBytes();

                // Пратете ја пораката на серверот
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(host), port);
                socket.send(sendPacket);

                // Примете ја обработената порака од серверот
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // Декодирајте ги бајтовите во текст
                String modifiedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Печати ја обработената порака на екран
                System.out.println("Примена обработена порака од серверот: " + modifiedMessage);

                // Проверете дали корисникот ја внесе KRAJ за крај на врската
                if (message.toUpperCase().equals("KRAJ")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            scanner.close();
        }
    }
}
