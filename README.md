# Small POC with PrestoDB and Cassandra

## Pre-requisites

You should have an instance of PrestoDB running locally

See [my other repo](https://github.com/albertocsm/docker-presto for setting) up a cluster with docker

## Notes

Run the tests with
```sh
mvn test
```

Query Presto using the CLI (see PrestoDB CLI [documentation](https://prestodb.io/docs/current/installation/cli.html) for details)

```sql
select count(id) as count from trips group by day;
```

