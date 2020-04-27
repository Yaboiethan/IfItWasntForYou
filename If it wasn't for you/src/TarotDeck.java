import java.util.ArrayList;
import java.util.Random;
/*
The tarot deck is responsible for maintaining, storing, and dealing the tarot cards and other methods with all of them
 */
public class TarotDeck
{
    //Private variables
    ArrayList<TarotCard> cards = new ArrayList<>();

    public TarotDeck()
    {
        InitializeCards();
    }

    private void InitializeCards()
    {
        //Create all the names in an array
        String[] names = new String[] {"Fool", "Magician", "Priestess", "Empress", "Emperor", "Heirophant", "Lovers", "Chariot"
                , "Justice", "Hermit", "Fortune", "Justice", "Hanged", "Death", "Temperance", "Devil", "Tower", "Star",
                "Moon", "Sun", "Judgement", "World"};
        //Create all the cards
        for (String name : names)
        {
            TarotCard nCard = new TarotCard(Position.ORIGIN, name);
            cards.add(nCard);
        }
    }

    public TarotCard GetCard(int index)
    {
        try
        {
            return cards.get(index);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Out of bounds");
        }
        return null;
    }

    public TarotCard GetRandomCard()
    {
        Random rand = new Random();
        return cards.get(rand.nextInt(cards.size()));
    }

    public void Shuffle()
    {
        Random rand = new Random();
        for(TarotCard card: cards)
        {
            int toReplace = rand.nextInt(cards.size());
            TarotCard temp = cards.get(toReplace);
            cards.set(toReplace, card);
            card = temp;
        }
    }
}
