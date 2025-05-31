import java.io.*;

public class Keytest {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		String line = null;
		while((line = reader.readLine())!=null)
			System.out.println(line+ "が押されました");

	}
}
