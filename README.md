# Small POC with PrestoDB and Cassandra

## Pre-requisites

You should have an instance of PrestoDB and Cassandra running locally

See [my other repo](https://github.com/albertocsm/docker-presto for setting) for setting up a PrestoDb cluster with docker. You can also find a Cassandra docker to setup the environment but its not included in the repo mentioned before.


## Notes

Run the tests with
```sh
mvn test
```

Query Cassandra via Presto using its CLI (see PrestoDB CLI [documentation](https://prestodb.io/docs/current/installation/cli.html) for details)

```sql
select count(id) as count from trips group by day;
```

