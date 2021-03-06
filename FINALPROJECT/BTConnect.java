import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import lejos.hardware.lcd.LCD;

public class BTConnect {

	private static String IPaddress = "10.0.1.8";
	private static int port = 1234;
	public static Socket connection = new Socket();
	private static BufferedInputStream in = null;
	private static DataOutputStream out = null;
	private boolean isConnected = false;
	
	public boolean connect() throws IOException{
		SocketAddress sa = new InetSocketAddress(IPaddress, port);
		try {
			connection.connect(sa, 1500); // Timeout possible
		} catch (Exception ex) {
			LCD.drawString(ex.getMessage(), 0,6);
			connection = null;
		}
		if (connection != null) {
			in = new BufferedInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());
			isConnected = true;
		}
		return isConnected;
	}
	
	public BufferedInputStream getIn() {
		return in;
	}
	
	public DataOutputStream getOut() {
		return out;
	}
}
