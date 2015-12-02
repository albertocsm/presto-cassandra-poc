package com.github.albertocsm.example;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraClient {

  // members
  private static final Logger log = LoggerFactory.getLogger(CassandraClient.class);

  private Cluster cluster;

  // public API
  public void initialize(String node) {

    cluster = Cluster.builder().addContactPoint(node).build();
    final Metadata metadata = cluster.getMetadata();
    log.info("Connected to cluster: {}", metadata.getClusterName());

    metadata
        .getAllHosts()
        .stream()
        .forEach((host) ->
            log.info(
                "Datacenter: {}; Host: {}; Rack: {}",
                host.getDatacenter(),
                host.getAddress(),
                host.getRack()));
  }

  public Session openSession() {

    return cluster.connect();
  }
}
