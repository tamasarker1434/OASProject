import java.util.*;

public class Main {
/**
 * Abbreviation of each owner/readers:=
 * customer = C,
 * Auction = A,
 * Online Auction Solutions = OAS,
 * Other Auction House = OAH
 */
// set customer parameters for DB
    public static class Customer{
        String bidder_number;//unique number of customer that keeps pseudonymous to each other
        String name;
        String status;//new customer with ref will be "good customer" or new customer
        String reference; //if have reference from other auction house then auction house name or null
        // Constructor to initialize fields
        public Customer(String bidder_number, String name, String status, String reference) {
            this.bidder_number = bidder_number;
            this.name = name;
            this.status = status;
            this.reference = reference;
        }
    }
    //Auction Product details
    public static class Product{
        String id;
        int starting_price;
        int bid_rise;
        public Product(String id, int starting_price, int bid_rise) {
            this.id = id; // Assigning the provided ID
            this.starting_price = starting_price; // Assigning the provided starting price
            this.bid_rise = bid_rise; // Assigning the provided bid rise increment
        }
    }
    //DB for customers {OAS : OAS}
    public HashMap<String, Customer> CustomerDB = new HashMap<>();
    //Commission Bids {C: C,A}
    public static List<Map<String, Object>> commissionBids = new ArrayList<>();
    //Online Bids {C: C,A}
    public static List<Map<String, Object>> liveBids = new ArrayList<>();
    //Products Details{OAS : OAS}
    public static HashMap<Integer, Integer> ProductDB = new HashMap<>();
    //Auction Result
    public static HashSet<String> AuctionResult = new HashSet<>();
    //Set bid limit for new Customers
    public final int BidLimit =500;
    public static void main(String[] args) {
        System.out.println("Welcome to Online Auction Solutions!");
        //customer registration {Ci : Ci , OAS, OAH}
        //customer with reference
        Customer customer1 = new Customer( "A","Alice","Good Customer","OAH-B");
        //new customer with no reference
        Customer customer2 = new Customer( "B","Bob","New Customer",null);
        //old customer with no reference
        Customer customer3 = new Customer( "C","Charlie","Good Customer",null);
        //Product Details {OAS : OAS}
        Product product = new Product("item1",500,50);
        //commission bids of customer A and B {C: C,A}
        Map<String, Object> commissionBid1 = new HashMap<>();
        commissionBid1.put("bidderName", "A");
        commissionBid1.put("amount", 500);
        commissionBids.add(commissionBid1);

        Map<String, Object> commissionBid2 = new HashMap<>();
        commissionBid2.put("bidderName", "B");
        commissionBid2.put("amount", 700);
        commissionBids.add(commissionBid2);

        // Live bids of customer C {C: C,A}
        Map<String, Object> liveBid1 = new HashMap<>();
        liveBid1.put("bidderName", "C");
        liveBid1.put("amount", 600);
        liveBids.add(liveBid1);

        Map<String, Object> liveBid2 = new HashMap<>();
        liveBid2.put("bidderName", "C");
        liveBid2.put("amount", 700);
        liveBids.add(liveBid2);

        Map<String, Object> liveBid3 = new HashMap<>();
        liveBid3.put("bidderName", "C");
        liveBid3.put("amount", 750);
        liveBids.add(liveBid3);

        // Conduct auction {A, C: A, C, OAS}
        int currentPrice = product.starting_price;
        String current_owner = "N/A";
        for (Map<String, Object> commissionBid : commissionBids) {
            int amount = (int) commissionBid.get("amount");
            if (amount >= currentPrice) {
                System.out.println("Auction house bids for " + commissionBid.get("bidderName") + ": " + currentPrice);
                currentPrice += product.bid_rise; // Increment by step size
            }
        }
        for (Map<String, Object> liveBid : liveBids) {
            int amount = (int) liveBid.get("amount");
            if (amount > currentPrice) {
                currentPrice = amount;
                current_owner = (String) liveBid.get("bidderName");
                System.out.println(current_owner + " bids " + currentPrice);
            }
        }
        System.out.println("Going once... Going twice... Sold!!!!");
        // Auction Result {}
        String aucRes = "Customer:" + current_owner + " Product Price:" + currentPrice;
        AuctionResult.add(aucRes);
        System.out.println("Auction Result::====>>> " + aucRes );
    }
}