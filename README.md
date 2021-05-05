# Mininum Unique Bidding Auction

This is a simple implementation of a Mininum Unique Bid Auction.  

### Project Structure

This is a very straightfoward/minimal code. The implementation highlights are:
* A `Bid` class is provided in order to identify a user, represented by the `id` property, and its bidding `ammount`.

* The business logic itself is found in the `MinimumBidding` class, where a `findWinner` method can be called in order to determine the auction winner.

### Tests
A test class is found in `MinimumBiddingTest`. 
To execute tests, please run `mvn test`