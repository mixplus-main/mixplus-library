package com.mixplus.library.minecraft.rcon;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RconClient {
    private static final int REQUEST_ID = 1;
    private static final int EXECUTECOMMAND = 2;
    private static final int AUTH = 3;

    private final RconInfo info;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public RconClient(RconInfo info) {
        this.info = info;
    }


    public synchronized void connect() throws IOException {
       this.connect(5000, 5000);
    }

    public synchronized void connect(int connectTimeout, int readTimeout) throws IOException{
        close();


        socket = new Socket();

        try {
            socket.connect(
                    new InetSocketAddress(info.host(), info.port()),
                    connectTimeout
            );

            socket.setTcpNoDelay(true);
            socket.setKeepAlive(true);
            socket.setSoTimeout(readTimeout);

            input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream())
            );

            output = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream())
            );

            if (!login()) {
                throw new IOException("RCON authentication failed.");
            }

        } catch (IOException e) {
            close();
            throw e;
        }


    }

    public boolean login() throws IOException {
        writePacket(
                AUTH,
                info.password()
        );


        RconPacket packet = readPacket();

        return packet.id() == REQUEST_ID;

    }

    public synchronized void close() throws IOException {
        if (socket != null) {
            socket.close();
        }

        socket = null;
        input = null;
        output = null;
    }

    public boolean isConnected() {
        return socket != null
                && socket.isConnected()
                && !socket.isClosed()
                && !socket.isInputShutdown()
                && !socket.isOutputShutdown();
    }

    public synchronized String sendCommand(String command) throws IOException {

        if (!isConnected()) {
            connect();
        }

        try {
            writePacket(EXECUTECOMMAND, command);

            RconPacket packet = readPacket();

            return packet.body();

        } catch (IOException e) {
            close();
            throw e;
        }

    }

    private void writePacket(int type, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        int length = 4 + 4 + bytes.length + 2;

        writeIntLE(length);
        writeIntLE(RconClient.REQUEST_ID);
        writeIntLE(type);


        output.write(bytes);


        output.writeByte(0);
        output.writeByte(0);

        output.flush();

    }

    private void writeIntLE(int value) throws IOException {
        output.writeByte(value & 0xFF);
        output.writeByte((value >> 8) & 0xFF);
        output.writeByte((value >> 16) & 0xFF);
        output.writeByte((value >> 24) & 0xFF);
    }

    private RconPacket readPacket() throws IOException {

        int length = readIntLE();

        int id = readIntLE();
        int type = readIntLE();

        int bodyLength = length - 4 - 4 - 2;

        byte[] bodyBytes = input.readNBytes(bodyLength);
        String body = new String(bodyBytes, StandardCharsets.UTF_8);

        input.readUnsignedByte();
        input.readUnsignedByte();

        return  new RconPacket(
                id,
                type,
                body
        );

    }

    private int readIntLE() throws  IOException {
        int b0 = input.readUnsignedByte();
        int b1 = input.readUnsignedByte();
        int b2 = input.readUnsignedByte();
        int b3 = input.readUnsignedByte();

        return b0
                | (b1 << 8)
                | (b2 << 16)
                | (b3 << 24);
    }


}
