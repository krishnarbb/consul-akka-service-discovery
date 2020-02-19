
package com.example;

import akka.actor.AbstractActor;
//import akka.actor.AbstractActor.Receive;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
//import akka.japi.pf.ReceiveBuilder;

public class ClusterWatcher extends AbstractActor {
  LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  Cluster cluster = Cluster.get(context().system());

  /*
  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
      .matchAny(msg -> {
        log.info("Cluster " + cluster.selfAddress() + " >>> " + msg);
      })
      .build();
  }
  */
  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(ClusterEvent.CurrentClusterState.class, state -> {
          log.debug("Current members: {}", state.members());
        })
        .match(ClusterEvent.MemberUp.class, event -> {
          log.debug("Member is Up: {}", event.member().address());
        })
        .match(ClusterEvent.UnreachableMember.class, event -> {
          log.debug("Member detected as unreachable: {}", event.member());

        })
        .match(ClusterEvent.MemberRemoved.class, event -> {
          log.debug("Member is Removed: {} after {}", event.member().address(), event.previousStatus());
        })
        .match(ClusterEvent.MemberEvent.class, event -> {
          log.info("Member Event: " + event.toString());
        })
        .build();
  }
}