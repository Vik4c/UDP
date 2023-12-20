import java.net.*;

public class UDPserver {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Одберете порта за серверот
            int port = 12345;

            // Креирајте сокет објект
            socket = new DatagramSocket(port);

            System.out.println("Серверот слуша на порта " + port);

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // Прими порака од клиентот
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // Декодирајте пораката од бајтови во текст
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Печати ја пораката на екран
                System.out.println("Примена порака од " + receivePacket.getAddress() + ": " + message);

                // Претвори ги сите букви во големи букви
                String modifiedMessage = message.toUpperCase();

                // Претвори го обработениот текст во бајтови
                byte[] sendBuffer = modifiedMessage.getBytes();

                // Прати го обработениот текст на клиентот
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);

                // Проверете дали корисникот ја внесе KRAJ за крај на врската
                if (message.equals("KRAJ")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
