package com.vmourac;

import com.spotify.hamcrest.optional.OptionalMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MinimumBiddingTest {

  MinimumBidding mBidding;

  @Before
  public void setup() {
    this.mBidding = new MinimumBidding();
  }

  @Test
  public void removeDuplicates_noneShouldBeRemoved() {
    List<Bid> bids = Arrays.asList(new Bid(1, 1), new Bid(2, 2), new Bid(3, 3));
    List<Bid> cleanList = this.mBidding.removeDupplicates(bids).collect(Collectors.toList());

    assertThat(cleanList, hasSize(3));
  }

  @Test
  public void removeDuplicates_sizeChangeOnDupRemove() {
    double DUPE_BID = 5.16;
    List<Bid> bids = Arrays.asList(new Bid(1, DUPE_BID), new Bid(2, DUPE_BID), new Bid(3, 3));
    List<Bid> cleanList = this.mBidding.removeDupplicates(bids).collect(Collectors.toList());

    assertThat(cleanList, hasSize(bids.size() - 2));
  }

  @Test
  public void removeDuplicates_validateListSizeOnDupeRemove() {
    double DUPE_BID = 1.01;
    List<Bid> bids = Arrays.asList(new Bid(1, DUPE_BID), new Bid(2, DUPE_BID), new Bid(3, 3));
    List<Bid> cleanList = this.mBidding.removeDupplicates(bids).collect(Collectors.toList());

    assertThat(cleanList, hasSize(bids.size() - 2));
  }

  @Test
  public void removeDuplicates_shouldRemoveDupBid() {
    Bid dupeBid = new Bid(1, 2);
    List<Bid> bids = Arrays.asList(dupeBid, dupeBid, new Bid(3, 3));
    List<Bid> cleanList = this.mBidding.removeDupplicates(bids).collect(Collectors.toList());

    assertThat(cleanList, not(contains(dupeBid)));
  }

  @Test
  public void findDuplicates_shouldReturnDupes() {
    Bid dupeBid = new Bid(1, 2);
    Bid anotherDupeBid = new Bid(5, 7);

    List<Bid> bids = Arrays.asList(dupeBid, dupeBid, new Bid(3, 3), anotherDupeBid, anotherDupeBid);
    List<Bid> dupes = this.mBidding.findDuplicates(bids).collect(Collectors.toList());

    assertThat(dupes, hasSize(2));
    assertThat(dupes, hasItem(dupeBid));
    assertThat(dupes, hasItem(anotherDupeBid));
  }

  @Test
  public void findWinner_noDupes() {
    double winningBid = 2.16;
    List<Bid> bids = Arrays.asList(new Bid(1, 3.12), new Bid(2, winningBid), new Bid(3, 4.15));

    Optional<Bid> optWinningBid = this.mBidding.findWinner(bids);
    assertTrue(optWinningBid.isPresent());

    Bid winner = optWinningBid.get();
    assertThat(winner.getAmmount(), equalTo(winningBid));
  }

  @Test
  public void findWinner_withDupeMinBid() {
    double minimumBid = 3.14;
    double winningBid = 4.89;
    List<Bid> bids = Arrays.asList(new Bid(1, winningBid), new Bid(2, minimumBid), new Bid(3, 5.73),
        new Bid(4, minimumBid));

    Optional<Bid> optWinner = mBidding.findWinner(bids);
    assertTrue(optWinner.isPresent());

    Bid winner = optWinner.get();
    assertThat(winner.getAmmount(), is(equalTo(winningBid)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void findWinner_nullInput() {
    mBidding.findWinner(null);
  }

  @Test
  public void findWinner_emptyInput() {
    Optional<Bid> winner = mBidding.findWinner(Arrays.asList());
    assertThat(winner, OptionalMatchers.emptyOptional());
  }

  @Test
  public void findWinner_minimumBidAfterBidLimit() {
    Bid minBid = new Bid(1000, 1);
    double minAmmount = 2.232390;
    List<Bid> bids = Stream.iterate(new Bid(0, minAmmount), bid -> new Bid(bid.getId() + 1, bid.getAmmount() + 1))
        .limit(999).collect(Collectors.toList());
    bids.add(minBid);

    Optional<Bid> winner = mBidding.findWinner(bids);
    Bid winnnigBid = winner.get();

    assertThat("Minimum Bid Cast after bit limit should not be considered", winnnigBid.getAmmount(), is(minAmmount));
  }

}
