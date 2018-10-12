import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.Map;
import java.util.UUID;

public class EntryPrinter {

    public static void main(String[] args) {

        /* Create a new HazelcastInstance for demonstration.
         * remove this block when connecting to an existing Hazelcast
         * cluster.
         */
        Config cfg = new Config();
        cfg.setProperty("hazelcast.logging.type", "none");
        HazelcastInstance hc = Hazelcast.newHazelcastInstance(cfg);

        /* If you'd like to run this on the same JVM your Hazelcast instance
         * is running on, Hazelcast.getHazelcastInstanceByName(String instanceName)
         * can be used in place of Hazelcast.newHazelcastInstance(); This is useful
         * if you'd like to create a web service that prints entries, as opposed
         * to a console app.
         */

        /* Normally, your Hazelcast members will be running in a separate cluster
         * from this command line application. If this is the case, only a client
         * needs to be configured and instantiated.
         */
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setProperty("hazelcast.logging.type", "none");
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        /* For the purpose of demonstration, I have created a serializable class
         * MapEntry that I use to store my values.
         */
        IMap<Integer, Object> map = client.getMap(args[0]);

        /* Populate the map before printing for demonstration. This step is not
         * needed for printing the entries of an already existing map.
         */
        for (int i = 0; i <= 5; i++) {

            map.set(i, new MapEntry(i, UUID.randomUUID().toString()));
        }

        StringBuilder output = new StringBuilder();

        for (Map.Entry<Integer, Object> entry : map.entrySet())
        {
            output.append("[").append(entry.getKey().toString()).append("] ");
            output.append(entry.getValue().toString()).append("\n");
        }

        System.out.println(output.toString());

        client.shutdown();

        /* Only necessary for creating the HazelcastInstance in this
         * example before instantiating the HazelcastClient.
         */
        hc.shutdown();
    }
}
