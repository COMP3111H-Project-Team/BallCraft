package hkust.comp3111h.ballcraft.server.bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.bluetooth.BluetoothSocket;

public abstract class BluetoothChannel {
	private static final int kInt32Size = 4;
	private BluetoothSocket socket;
	private InputStream rfile;
	private OutputStream wfile;
	private BlockingQueue<byte[]> toBeSent = new LinkedBlockingQueue<byte[]>();
	
	private Thread messageReceiver = new Thread() {
		public void run() {
			while (true) {
				try {
					receiveOnce();
				} catch (IOException e) {
					onClose();
					try {
						socket.close();
					} catch (IOException e1) { }
					break;
				}
			}
		}
		
		void receiveOnce() throws IOException {
			int messageLength = readInt32();
			byte[] message = new byte[messageLength];
			int numBytesRead = 0;
			while (numBytesRead < messageLength) {
				numBytesRead += rfile.read(message, numBytesRead, messageLength - numBytesRead);
			}
			onMessage(message);
		}
		
		int readInt32() throws IOException {
			byte[] results = new byte[kInt32Size];
			for (int i = 0; i < kInt32Size; ++i) {
				int byteGot = rfile.read();
				if (byteGot == -1) {
					throw new IOException();
				}
				results[i] = (byte) byteGot;
			}
			return decodeInt32(results);
		}
	};
	
	private Thread messageSender = new Thread() {
		public void run() {
			byte[] message;
			while (true) {
				try {
					message = toBeSent.take();
				} catch (InterruptedException e) {
					break;
				}
				try {
					wfile.write(encodeInt32(message.length));
					wfile.write(message);
				} catch (IOException e) {
					onClose();
					try {
						socket.close();
					} catch (IOException e1) { }
					break;
				}
			}
		}
	};
	
	/**
	 * Should be called to wrap a socket in this channel.
	 * @param socket The socket to be wrapped
	 * @throws IOException
	 */
	public void initialize(BluetoothSocket socket) throws IOException {
		this.socket = socket;
		rfile = this.socket.getInputStream();
		wfile = this.socket.getOutputStream();
		
		messageSender.start();
		messageReceiver.start();
	}
	
	/**
	 * Asynchronous method that sends the given message through the socket,
	 * to the remote host.
	 * @param message The message to be sent
	 */
	public void sendMessage(byte[] message) {
		try {
			toBeSent.put(message);
		} catch (InterruptedException e) { }
	}
	
	/**
	 * Asynchronous callback method, called when the channel is able to send message.
	 */
	abstract void onOpen();
	
	/**
	 * Asynchronous callback method, called when a piece of message is received.
	 * @param message The raw message received
	 */
	abstract void onMessage(byte[] message);
	
	/**
	 * Asynchronous callback method, called when an error is occurred.
	 * Currently it can only occur when the client failed to connect to a server.
	 * @param e The exception thrown
	 */
	abstract void onError(Exception e);
	
	/**
	 * Asynchronous callback method, called when the connection is closed.
	 * Currently it is mostly caused by the other side closing the connection.
	 */
	abstract void onClose();
	
	private int decodeInt32(byte[] data) {
		int decoded = 0;
		for (int i = 0; i < kInt32Size; ++i) {
			decoded <<= 8;
			decoded |= data[kInt32Size - i - 1];
		}
		return decoded;
	}
	
	private byte[] encodeInt32(int value) {
		byte[] encoded = new byte[kInt32Size];
		for (int i = 0; i < kInt32Size; ++i) {
			encoded[i] = (byte) (value & 0xff);
			value >>= 8;
		}
		return encoded;
	}
}
