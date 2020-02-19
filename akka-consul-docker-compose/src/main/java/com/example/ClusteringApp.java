package com.example;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.discovery.ServiceDiscovery;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.AllDirectives;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import scala.collection.immutable.Seq;

public class ClusteringApp extends AllDirectives { 

	ClusteringApp() {
  
    ActorSystem system = ActorSystem.create(ClusteringConfig.CLUSTER_NAME);
    AkkaManagement.get(system).start();
    ClusterBootstrap.get(system).start();

    //ActorRef clusterListener = system.actorOf(Props.create(ClusterListener.class), "clusterListener");
    
    Cluster cluster = Cluster.get(system);
    system.log().info("Started [" + system + "], cluster.selfAddress = " + cluster.selfAddress() + ")");
    cluster.registerOnMemberUp(() -> {
        system.log().info("Cluster member is up!");
    });

    cluster
    .subscribe(system.actorOf(Props.create(ClusterWatcher.class)), ClusterEvent.initialStateAsEvents(), ClusterEvent.ClusterDomainEvent.class);
    
    
		/*
		 * List<Address> seedNodes = new ArrayList<>(); Address seed1 = new
		 * Address("akka.tcp", "clustering-cluster", "c1", 2552); Address seed2 = new
		 * Address("akka.tcp", "clustering-cluster", "c2", 2552); Address seed3 = new
		 * Address("akka.tcp", "clustering-cluster", "c3", 2552); seedNodes.add(seed1);
		 * seedNodes.add(seed2); seedNodes.add(seed3); cluster.joinSeedNodes(seedNodes);
		 */

    
    // Start a Http server in the Akka Cluster
    Materializer mat = ActorMaterializer.create(system);
    Http.get(system).bindAndHandle(complete("Hello world").flow(system, mat), ConnectHttp.toHost("0.0.0.0", 8282), mat);
    
    
    
  }
	public static void main(String[] args) {
		new ClusteringApp();
	}

}
