# Consul based Service Discovery in an AKKA Cluster
Consul based Service Discovery in an AKKA Cluster.

Uses Docker and Docker compose to create an Akka Cluster and Consul Service registry.

### How to Run
Step 1:
cd akka-consul-docker-compose

To create a local docker image, run `mvn package`

Step 2:

To run the cluster, run `docker-compose up`. This will create consul nodes and akka cluster nodes.

While running, try opening a new terminal and (from the same directory) try things like `docker-compose down c1` and wat
ch the cluster nodes respond.
~
