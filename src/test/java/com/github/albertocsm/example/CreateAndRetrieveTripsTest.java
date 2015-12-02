package com.github.albertocsm.example;

import com.datastax.driver.core.Session;
import com.google.common.base.Stopwatch;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateAndRetrieveTripsTest {

  // members
  public static final String CASSANDRA_KEYSPACE = "example";
  public static final String CASSANDRA_TABLE_NAME = "trips";
  public static final String CASSANDRA_KEYSPACE_TABLE_NAME = CASSANDRA_KEYSPACE + "." + CASSANDRA_TABLE_NAME;
  private static final Logger log = LoggerFactory.getLogger(CreateAndRetrieveTripsTest.class);
  private Session session;
  private CassandraClient client;

  @BeforeClass
  public void setUpClass()
    throws Exception {

    client = new CassandraClient();
    client.initialize("172.17.0.2");

    session = client.openSession();

    session.execute(
        String.format(
            "CREATE KEYSPACE IF NOT EXISTS %s WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};",
            CASSANDRA_KEYSPACE));

    session.execute(
        String.format(
            "CREATE TABLE IF NOT EXISTS %s (id bigint PRIMARY KEY, day bigint);", CASSANDRA_KEYSPACE_TABLE_NAME));

    session.close();
  }

  @AfterClass
  public void tearDownClass()
    throws Exception {

  }

  @Test(groups = "setup")
  public void create_trips() {

    int totalTrips = 2000000;
    int batchSize = 10000;

    int id = 1000000;
    for (int i = 0; i < totalTrips / batchSize; i++) {

      id = create_batch(batchSize, id);
    }
  }

  private int create_batch(int batchTotalTrips, int tripId) {

    Session session = client.openSession();

    Trips t;
    Random r = new Random();
    int low = 1;
    int high = 30;

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; i < batchTotalTrips; i++) {

      t = new Trips(tripId, r.nextInt(high - low) + low);
      session.execute(
          String.format("INSERT INTO %s (id, day) VALUES (%d,%d);",
              CASSANDRA_KEYSPACE_TABLE_NAME,
              t.getId(),
              t.getDay()));

      tripId++;
    }

    session.close();

    // log the latency
    stopwatch.stop();
    long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    log.info(
        String.format(
            "time to generate %s trips in: %s ms",
            batchTotalTrips,
            millis));

    return tripId;
  }
}
