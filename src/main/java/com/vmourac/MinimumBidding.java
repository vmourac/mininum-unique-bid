package com.vmourac;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinimumBidding {

  private static final int MAX_BIDS = 999;

  public Optional<Bid> findWinner(List<Bid> bids) {
    if (bids == null) {
      throw new IllegalArgumentException("A list of Bids must be provided");
    }

    List<Bid> sanitizedInput = bids.size() > MAX_BIDS ? bids.subList(0, MAX_BIDS) : bids;
    Optional<Bid> winner = removeDupplicates(sanitizedInput).min(Comparator.comparing(Bid::getAmmount));

    winner.ifPresent(bid -> {
      System.out.println("Winner is: " + bid.toString());
    });
    return winner;
  }

  protected Stream<Bid> removeDupplicates(List<Bid> bids) {
    List<Bid> dupeBids = findDuplicates(bids).collect(Collectors.toList());
    return bids.stream().filter(bid -> !dupeBids.contains(bid));
  }

  protected Stream<Bid> findDuplicates(List<Bid> bids) {
    Set<Bid> items = new HashSet<>();
    return bids.stream().filter(n -> !items.add(n)).distinct();
  }

}
