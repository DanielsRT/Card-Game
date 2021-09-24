import java.util.*;
public class CardGame {
    
    static Scanner keyb = new Scanner(System.in);
    static Random rand = new Random();
    static String[] DESC = {"Ace of Clubs", "2 of Clubs", "3 of Clubs", "4 of Clubs", "5 of Clubs",
                            "6 of Clubs", "7 of Clubs", "8 of Clubs", "9 of Clubs", "10 of Clubs",
                            "Jack of Clubs", "Queen of Clubs", "King of Clubs", "Ace of Diamonds",
                            "2 of Diamonds", "3 of Diamonds", "4 of Diamonds", "5 of Diamonds", "6 of Diamonds",
                            "7 of Diamonds", "8 of Diamonds", "9 of Diamonds", "10 of Diamonds", "Jack of Diamonds",
                            "Queen of Diamonds", "King of Diamonds", "Ace of Hearts", "2 of Hearts",
                            "3 of Hearts", "4 of Hearts", "5 of Hearts", "6 of Hearts", "7 of Hearts",
                            "8 of Hearts", "9 of Hearts", "10 of Hearts", "Jack of Hearts", "Queen of Hearts",
                            "King of Hearts", "Ace of Spades", "2 of Spades", "3 of Spades", "4 of Spades",
                            "5 of Spades", "6 of Spades", "7 of Spades", "8 of Spades", "9 of Spades",
                            "10 of Spades", "Jack of Spades", "Queen of Spades", "King of Spades"};
    static String[] ABBREV = {"A of C", "2 of C", "3 of C", "4 of C", "5 of C",
                            "6 of C", "7 of C", "8 of C", "9 of C", "T of C",
                            "J of C", "Q of C", "K of C", "A of D",
                            "2 of D", "3 of D", "4 of D", "5 of D", "6 of D",
                            "7 of D", "8 of D", "9 of D", "T of D", "J of D",
                            "Q of D", "K of D", "A of H", "2 of H",
                            "3 of H", "4 of H", "5 of H", "6 of H", "7 of H",
                            "8 of H", "9 of H", "T of H", "J of H", "Q of H",
                            "K of H", "A of S", "2 of S", "3 of S", "4 of S",
                            "5 of S", "6 of S", "7 of S", "8 of S", "9 of S",
                            "T of S", "J of S", "Q of S", "K of S"};
    static Boolean[] CARD_DECK = new Boolean[52];
    static ArrayList<Integer> PLAYER1 = new ArrayList<>();
    static ArrayList<Integer> PLAYER2 = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Integer> PLAYER1_BOOKS = new ArrayList<>();
        ArrayList<Integer> PLAYER2_BOOKS = new ArrayList<>();
        getCardDeck();
        int handSize = getHandSize();
        
        System.out.println("Dealing " + handSize + " cards for player 1's hand.");
        PLAYER1 = dealPlayerHand(handSize);
        int cardsLeft = getCardsLeft();
        System.out.println("\nThere are " + cardsLeft + " cards left in the deck\n");
        System.out.println("Here's 1's starting hand in order:");
        Collections.sort(PLAYER1);
        displayPlayerHand(PLAYER1);
        for (int rank = 1; rank <= 13; rank++) {
            if (countRankInHand(rank, PLAYER1) >= 4) {
                PLAYER1_BOOKS.add(rank);
                System.out.println("Player 1 completes the '" + rank + "' book");
            }
        }
        
        System.out.println("\nDealing " + handSize + " cards for player 2's hand.");
        PLAYER2 = dealPlayerHand(handSize);
        cardsLeft = getCardsLeft();
        System.out.println("\nThere are " + cardsLeft + " cards left in the deck\n");
        System.out.println("Here's 2's starting hand in order:");
        Collections.sort(PLAYER2);
        displayPlayerHand(PLAYER2);
        for (int rank = 1; rank <= 13; rank++) {
            if (countRankInHand(rank, PLAYER2) >= 4) {
                PLAYER2_BOOKS.add(rank);
                System.out.println("Player 2 completes the '" + rank + "' book");
            }
        }
        
        displayAbbreviatedHand("Player 1", PLAYER1);
        displayAbbreviatedHand("Player 2", PLAYER2);
        
        Boolean player1Turn = true;
        do {
            if (player1Turn == true) {
                System.out.println("\nIt's Player 1's turn\n");
                int cardRequest = makeCardRequest("Player 1", PLAYER1);
                // opponent has card
                int rankCount = countRankInHand(cardRequest, PLAYER2);
                if (rankCount > 0) {
                    int cardsMoved = playerTakesCard(cardRequest, "Player 1", "Player 2", PLAYER1, PLAYER2);
                    System.out.println("Player 1 gets " + cardsMoved + " card(s) from Player 2");
                    if (countRankInHand(cardRequest, PLAYER1) >= 4) {
                        PLAYER1_BOOKS.add(cardRequest);
                        System.out.println("Player 1 completes the '" + cardRequest + "' book");
                    }
                }
                // opponent doesn't have card
                if (rankCount == 0) {
                    int drawnCard = playerDrawsCardFromDeck("Player 1", PLAYER1);
                    int drawnRank = getCardRank(drawnCard);
                    int drawnRankCount = countRankInHand(drawnRank, PLAYER1);
                    if (drawnRankCount == 0) {
                        System.out.println("Player 1 drew a card, turn ends.");
                        System.out.println("There are " + getCardsLeft() + " cards left");
                        player1Turn = false;
                    } else {
                        System.out.println("Player 1 drew " + DESC[drawnCard] + ", gets another turn.");
                    }
                    PLAYER1.add(drawnCard);
                    if (countRankInHand(drawnRank, PLAYER1) >= 4) {
                        PLAYER1_BOOKS.add(drawnRank);
                        System.out.println("Player 1 completes the '" + drawnRank + "' book");
                    }
                }
            } else {
                System.out.println("\nIt's Player 2's turn\n");
                int cardRequest = makeCardRequest("Player 2", PLAYER2);
                // opponent has card
                int rankCount = countRankInHand(cardRequest, PLAYER1);
                if (rankCount > 0) {
                    int cardsMoved = playerTakesCard(cardRequest, "Player 2", "Player 1", PLAYER2, PLAYER1);
                    System.out.println("Player 2 gets " + cardsMoved + " card(s) from Player 1");
                    if (countRankInHand(cardRequest, PLAYER2) >= 4) {
                        PLAYER2_BOOKS.add(cardRequest);
                        System.out.println("Player 2 completes the '" + cardRequest + "' book");
                    }
                }
                // opponent doesn't have card
                if (rankCount == 0) {
                    int drawnCard = playerDrawsCardFromDeck("Player 2", PLAYER2);
                    int drawnRank = getCardRank(drawnCard);
                    int drawnRankCount = countRankInHand(drawnRank, PLAYER2);
                    if (drawnRankCount == 0) {
                        System.out.println("Player 2 drew a card, turn ends.");
                        System.out.println("There are " + getCardsLeft() + " cards left");
                        player1Turn = true;
                    } else {
                        System.out.println("Player 2 drew " + DESC[drawnCard] + ", gets another turn.");
                    }
                    PLAYER2.add(drawnCard);
                    if (countRankInHand(drawnRank, PLAYER2) >= 4) {
                        PLAYER2_BOOKS.add(drawnRank);
                        System.out.println("Player 2 completes the '" + drawnRank + "' book");
                    }
                }
            }
            displayAbbreviatedHand("Player 1", PLAYER1);
            displayAbbreviatedHand("Player 2", PLAYER2);
        } while (PLAYER1.size() > 0 && PLAYER2.size() > 0 && getCardsLeft() > 0);
        
        System.out.println("\nGAME OVER! Here's the results:\n");
        System.out.printf("Player 1 has %2d books: ", PLAYER1_BOOKS.size());
        System.out.println(PLAYER1_BOOKS);
        System.out.printf("Player 2 has %2d books: ", PLAYER2_BOOKS.size());
        System.out.println(PLAYER2_BOOKS);
        if (PLAYER1_BOOKS.size() > PLAYER2_BOOKS.size()) {
            System.out.println("Player 1 wins");
        } else {
            System.out.println("Player 2 wins");
        }
        System.out.println("\nThanks for playing 'Go Fish'");
    }
    
    // playerDrawsCardFromDeck("player 1", PLAYER1);
    public static int playerDrawsCardFromDeck(String name, ArrayList<Integer> playerHand) {
        int dealtCount = 0;
        int nextCard = 0;
        while (dealtCount == 0) {
            nextCard = rand.nextInt(52);
            if (CARD_DECK[nextCard] == false) {
                //playerHand.add(nextCard);
                CARD_DECK[nextCard] = true;
                dealtCount++;
            }
        }
        return nextCard;
    }
    
    // int cardsMoved = playerTakesCard("player 1", "player 2", PLAYER1, PLAYER2);
    public static int playerTakesCard(int cardRequest, String recieverName, String giverName,
                                     ArrayList<Integer> recieverHand, ArrayList<Integer> giverHand) {
        int cardsMoved = 0;
        for (int ndx = 0; ndx < giverHand.size(); ndx++) {
            int card = giverHand.get(ndx);
            int rank = getCardRank(card);
            if (rank == cardRequest) {
                giverHand.remove(ndx);
                recieverHand.add(card);
                cardsMoved++;
            }
        }
        
        if (recieverName.equals("Player 1")) {
            PLAYER1 = recieverHand;
        } else {
            PLAYER2 = recieverHand;
        }
        
        if (giverName.equals("Player 1")) {
            PLAYER1 = giverHand;
        } else {
            PLAYER2 = giverHand;
        }
        return cardsMoved;
    }
    
    // int rank = getCardRank(player2.get(ndx));
    public static int getCardRank(int card) {
        int rank = card % 13 + 1;
        return rank;
    }
    
    // int cardRequest = makeCardRequest("Player 1", PLAYER1);
    public static int makeCardRequest(String name, ArrayList<Integer> playerHand) {
        System.out.print("Enter a request (1 for Ace up to 13 for King): ");
        int cardRequest = 0;
        while (cardRequest == 0) {
            cardRequest = keyb.nextInt(); 
            int rankCount = countRankInHand(cardRequest, playerHand);
            if (rankCount == 0) { // hand doesn't have rank
                System.out.println(name + " doesn't hold any " + cardRequest + "'s");
                System.out.print("Enter a request (1 for Ace up to 13 for King): ");
                cardRequest = 0;
            } else { // hand has rank
                return cardRequest;
            }
        }
        return cardRequest;
     }
    
    // int rankCount = countRankInHand(cardRequest, PLAYER2);
    public static int countRankInHand(int parameter, ArrayList<Integer> playerHand) {
        int rankCount = 0;
            for (int ndx = 0; ndx < playerHand.size(); ndx++) {
                int card = playerHand.get(ndx);
                int rank = getCardRank(card);
                if (rank == parameter) {
                    rankCount++;
                }
           }
        return rankCount;
    }
    
    // displayAbbreviatedHand("Player 1", PLAYER1);
    public static void displayAbbreviatedHand(String name, ArrayList<Integer> playerHand) {
        System.out.println("\n" + name + "'s hand:");
        for (int ndx = 0; ndx < playerHand.size(); ndx++) {
            int card = playerHand.get(ndx);
            System.out.printf("    %s", ABBREV[card]);
        }
        System.out.println();
    }
    
    // getCardDeck();
    public static void getCardDeck() {
        for (int ndx = 0; ndx < CARD_DECK.length; ndx++) {
            CARD_DECK[ndx] = false;
        }
    }
    
    // displayPlayerHand(PLAYER1);
    public static void displayPlayerHand(ArrayList<Integer> playerHand) {
        for (int ndx = 0; ndx < playerHand.size(); ndx++) {
            String card = DESC[playerHand.get(ndx)];
            System.out.println("\t" + card);
        }
    }
    
    // ArrayList<Integer> PLAYER1 = dealPlayerHand(handSize);
    public static ArrayList<Integer> dealPlayerHand(int handSize) {
        ArrayList<Integer> playerHand = new ArrayList<Integer>();
        int dealtCount = 0;
        while (dealtCount < handSize) {
            int nextCard = rand.nextInt(52);
            if (CARD_DECK[nextCard] == false) {
                playerHand.add(nextCard);
                CARD_DECK[nextCard] = true;
                dealtCount++;
            }
        }    
        System.out.println("Dealt " + handSize + " different cards.");
        return playerHand;
    }
    
    // int handSize = getHandSize();
    public static int getHandSize() {
        System.out.print("\nHow many cards to deal? (1 or more): ");
        int handSize = 0;
        while (handSize < 1) {
            handSize = keyb.nextInt();
            System.out.println();
            if (handSize < 1) {
                handSize = 0;
                System.out.print("Must be 1 or more: ");
            }
        }
        return handSize;
    }
    
    // int cardsLeft = getCardsLeft();
    public static int getCardsLeft() {
        int cardCount = 0;
        for (int ndx = 0; ndx < CARD_DECK.length; ndx++) {
            if (CARD_DECK[ndx] == false) {
                cardCount++;
            }
        }
        return cardCount;
    }
    
    
}
    
    