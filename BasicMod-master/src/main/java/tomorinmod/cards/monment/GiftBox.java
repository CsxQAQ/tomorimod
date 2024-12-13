package tomorinmod.cards.monment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.rewards.AnonReward;
import tomorinmod.rewards.RanaReward;
import tomorinmod.rewards.SoyoReward;
import tomorinmod.rewards.TakiReward;
import tomorinmod.savedata.SaveGifts;
import tomorinmod.util.CardStats;

import java.util.ArrayList;
import java.util.Iterator;

public class GiftBox extends BaseMonmentCard {
    public static final String ID = makeID(GiftBox.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public boolean isFlipped = false;
    private int giftFrom = -1;
    private boolean giftsFull = true;

    public GiftBox() {
        super(ID, info);

        this.selfRetain = true;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void autoUse() {
        switch (giftFrom) {
            case 0:
                AbstractDungeon.getCurrRoom().rewards.add(new AnonReward());
                break;
            case 1:
                AbstractDungeon.getCurrRoom().rewards.add(new SoyoReward());
                break;
            case 2:
                AbstractDungeon.getCurrRoom().rewards.add(new TakiReward());
                break;
            case 3:
                AbstractDungeon.getCurrRoom().rewards.add(new RanaReward());
                break;
            default:
                break;
        }

        Iterator<AbstractCard> iterator = AbstractDungeon.player.masterDeck.group.iterator();

        while (iterator.hasNext()) {
            AbstractCard card = iterator.next();
            if (this.uuid.equals(card.uuid)) {
                iterator.remove();
                break;
            }
        }
        //CustomUtils.addTags(this, CustomTags.MOMENT);
        this.isEthereal = true;
        this.selfRetain = false;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GiftBox();
    }

    private void updateDescription() {
        switch (giftFrom) {
            case 0:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
                break;
            case 1:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[1];
                break;
            case 2:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[2];
                break;
            case 3:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[3];
                break;
            default:
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[4];
                break;

        }
        initializeDescription();
    }

    public void flipCard() {
        this.isFlipped = true;
        ArrayList<Integer> giftGeted = SaveGifts.getInstance().getGiftGeted();

        for (int i = 0; i < 4; i++) {
            if (giftGeted.get(i) == 0) {
                this.giftsFull = false;
                break;
            }
        }

        if (!this.giftsFull) {
            int randomResult = AbstractDungeon.miscRng.random(3); // 随机生成 0, 1, 或 2
            while (giftGeted.get(randomResult) == 1) {
                randomResult = AbstractDungeon.miscRng.random(3);
            }
            giftGeted.set(randomResult, 1);
            this.giftFrom = randomResult;
        }


        updateDescription();
        autoUse();
    }

    @Override
    public void setMaterialAndLevel(){

    }

}
