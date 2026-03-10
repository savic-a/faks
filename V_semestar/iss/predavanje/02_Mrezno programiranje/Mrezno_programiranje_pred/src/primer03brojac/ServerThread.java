package primer03brojac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	public ServerThread(Socket sock, int value) {
		this.sock = sock;
		this.value = value;
		try {
			// inicijalizuj ulazni stream
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream(), "UTF-8"));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream(), "UTF-8")), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		start();
	}

	public void run() {
		try {
			// procitaj zahtev
			String request = in.readLine();
			System.out.println(request);

			// odgovori na zahtev
			out.println("(" + value + ")");
			
			out.flush();

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Socket sock;
	private int value;
	private BufferedReader in;
	private PrintWriter out;
}