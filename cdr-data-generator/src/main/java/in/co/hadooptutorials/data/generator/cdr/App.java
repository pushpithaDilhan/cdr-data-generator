package in.co.hadooptutorials.data.generator.cdr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.fluttercode.datafactory.impl.DataFactory;
import org.joda.time.DateTime;
/**
 * 
 * Telecom Call Data/Detail Record Generator.
 * @author Tanmay Deshpande
 * 
 */

public class App {
	public static void main(String[] args) throws IOException {
		DataFactory df = new DataFactory();
		Random r = new Random();
        ArrayList<String> providers = new ArrayList<String>(Arrays.asList("MT","HT","ET","BA"));
        // Instantiating Configuration class
        Configuration config = HBaseConfiguration.create();

        // Instantiating HTable class
        HTable hTable = new HTable(config, "cdr_test");

        for (int i = 0; i < 1000; i++) {

            // cdr ID
            UUID id = UUID.randomUUID();

			// calling party
			String calling_num = df.getNumberText(10);
			String calling_tower_id = providers.get(r.nextInt(4)) + r.nextInt(10000);

			// recipient
			String called_num = df.getNumberText(10);
			String recipient_tower_id = providers.get(r.nextInt(4)) + r.nextInt(10000) ;

			// date and time
		    long t1 = System.currentTimeMillis() + r.nextInt();
		    DateTime d1 = new DateTime(t1);

		    // duration in seconds
            int duration = r.nextInt(200);

//			System.out.println(id.toString()+"|"+calling_num+"|"+calling_tower_id+"|"+called_num+"|"+recipient_tower_id+"|"+d1.toString()+"|"+duration);

            // Instantiating Put class
            // accepts a row name.
            Put p = new Put(Bytes.toBytes("row"+i));

            // adding values using add() method
            // accepts column family name, qualifier/row name ,value
            p.add(Bytes.toBytes("id"), Bytes.toBytes("cdr-id"),Bytes.toBytes(id.toString()));

            p.add(Bytes.toBytes("call_detail"), Bytes.toBytes("calling_num"),Bytes.toBytes(calling_num));
            p.add(Bytes.toBytes("call_detail"), Bytes.toBytes("calling_tower"),Bytes.toBytes(calling_tower_id));
            p.add(Bytes.toBytes("call_detail"), Bytes.toBytes("called_num"),Bytes.toBytes(called_num));
            p.add(Bytes.toBytes("call_detail"), Bytes.toBytes("called_tower"),Bytes.toBytes(recipient_tower_id));

            p.add(Bytes.toBytes("time"), Bytes.toBytes("date_time"),Bytes.toBytes(d1.toString()));
            p.add(Bytes.toBytes("time"), Bytes.toBytes("duration"),Bytes.toBytes(duration));

            // Saving the put Instance to the HTable.
            hTable.put(p);
        }
        // closing HTable
        hTable.close();
	}
}
