package QueenMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import QueenMod.interfaces.CardAddedToDeck;

public abstract class AbstractCollectorCard
        extends AbstractCard implements CardAddedToDeck
{
    public int threshold;

    public AbstractCollectorCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public abstract boolean ReplaceCardWithCollector();
}