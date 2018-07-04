package in.co.hadooptutorials.data.generator.cdr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.fluttercode.datafactory.impl.DataFactory;
import org.joda.time.DateTime;
/**
 * 
 * Telecom Call Data/Detail Record Generator.
 * @author Tanmay Deshpande
 * 
 */

public class App {
	public static void main(String[] args) {
		DataFactory df = new DataFactory();
		Random r = new Random();
        ArrayList<String> providers = new ArrayList<String>(Arrays.asList("MT","HT","ET","BA"));
        for (int i = 0; i < 1000; i++) {

            // cdr ID
            UUID id = UUID.randomUUID();

			// calling party
			String calling_num = df.getNumberText(10);
			String calling_tower_id = "DA"+ r.nextInt(10000);

			// recipient
			String called_num = df.getNumberText(10);
			String recipient_tower_id = providers.get(r.nextInt(4)) + r.nextInt(10000) ;

			// date and time
		    long t1 = System.currentTimeMillis() + r.nextInt();
		    DateTime d1 = new DateTime(t1);

		    // duration in seconds
            int duration = r.nextInt(200);

			System.out.println(id.toString()+"|"+calling_num+"|"+calling_tower_id+"|"+called_num+"|"+recipient_tower_id+"|"+d1.toString()+"|"+duration);
		}
	}
}
