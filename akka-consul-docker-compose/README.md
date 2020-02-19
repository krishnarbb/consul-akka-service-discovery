akka-docker-cluster-example
===========================

Akka-cluster using Consul based service discovery .
Uses Docker and Docker compose to create an Akka Cluster and Consul Service registry.

### How to Run

mvn package
Above commands creates a local docker container. 


To run the cluster, run `docker-compose up`. This will create consul nodes and akka cluster nodes.

While running, try opening a new terminal and (from the same directory) try things like `docker-compose down c1` and watch the cluster nodes respond.
