package xyz.codebeaconlab;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    /**
     * `curl --http1.0 http://localhost:18888/`
     * */
    public static void main(String[] args) throws IOException {
        int port = 18888;
        try(ServerSocket server = new ServerSocket(port, 0, InetAddress.getByName("localhost"))) { // 서버를 특정 포트로 열어두는 역할
            Socket socket;
            while ((socket = server.accept()) != null) { // 개별 client과 서버에 connection이 생성되는 경우
                try (OutputStream out = socket.getOutputStream()) {
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] result = "hello".getBytes();

                    setResponseHeader(dos, result);

                    dos.flush();
                }
            }
        }
    }

    private static void setResponseHeader(DataOutputStream dos, byte[] result) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + result.length + "\r\n");
        dos.writeBytes("\r\n");

        dos.write(result, 0, result.length);
    }
}