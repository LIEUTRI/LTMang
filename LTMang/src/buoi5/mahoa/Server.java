package mahoa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Server {
	private static String SECRET_KEY = "cit";
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] key = SECRET_KEY.getBytes();
	    //MessageDigest sha  = MessageDigest.getInstance("SHA-1");
	    //key = sha.digest(key);
	    key = Arrays.copyOf(key, 32); 
	    SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	    
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		
	    
		ServerSocket ss = null;
		Socket s = null;
		try {
			ss = new ServerSocket(77);
			System.out.println("Server is listening...........");
			while (true) {
				String task = "";
				s = ss.accept();
				System.out.println("Connected to " + s.getInetAddress());
				
				InputStream is = s.getInputStream();
				OutputStream os = s.getOutputStream();
				byte[] b = new byte[6000];
				is.read(b);
				String data = new String(b).trim();
				task = data.substring(0, data.indexOf(" "));
				data = data.substring(data.indexOf(" ")+1);
				System.out.println("task: "+task+"\ndata: " + data);
				
				switch(task) {
					case "MaHoa":
						cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
					    byte[] byteEncrypted = cipher.doFinal(data.getBytes());
					    String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
						os.write(encrypted.getBytes());
						break;
					case "GiaiMa":
						cipher.init(Cipher.DECRYPT_MODE, skeySpec);
					    byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(data));
					    String decrypted = new String(byteDecrypted);
						os.write(decrypted.getBytes());
						break;
					default: os.write("Nhap sai phuong thuc! (MaHoa hoac GiaiMa)".getBytes());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
