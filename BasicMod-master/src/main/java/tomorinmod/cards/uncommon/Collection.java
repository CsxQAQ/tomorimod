package tomorinmod.cards.uncommon;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Collection extends BaseCard {
    public static final String ID = makeID(Collection.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private int previewIndex = 0;
    private float previewTimer = 1.0f;
    private static final float PREVIEW_INTERVAL = 1.5f; // 每张卡显示的时间
    private ArrayList<AbstractCard> relatedCards=new ArrayList<>();

    @Override
    public void update() {
        super.update();

        // 鼠标悬浮时执行轮换逻辑
        if (this.hb.hovered && !relatedCards.isEmpty()) {
            previewTimer += Gdx.graphics.getDeltaTime();
            if (previewTimer >= PREVIEW_INTERVAL) {
                previewTimer = 0.0f;
                previewIndex = (previewIndex + 1) % relatedCards.size();
                this.cardsToPreview = relatedCards.get(previewIndex);
            }
        } else {
            // 鼠标未悬浮，重置
            this.cardsToPreview = null;
            previewTimer = 1.0f;
        }
    }

    public Collection() {
        super(ID, info);
        this.cardsToPreview=new Band();
        relatedCards.add(new Band(){});
        relatedCards.add(new Stone());
        relatedCards.add(new Watermelonworm());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        cardGroup.add(new Band(){
            @Override
            public void onChoseThisOption() {
                p.hand.addToHand(this.makeStatEquivalentCopy());
            }
        });
        cardGroup.add(new Stone(){
            @Override
            public void onChoseThisOption() {
                p.hand.addToHand(this.makeStatEquivalentCopy());
            }
        });
        cardGroup.add(new Watermelonworm(){
            @Override
            public void onChoseThisOption() {
                p.hand.addToHand(this.makeStatEquivalentCopy());
            }
        });
        if (this.upgraded){
            for (AbstractCard card : cardGroup){
                card.upgrade();
            }
        }
        addToBot(new ChooseOneAction(cardGroup));
    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new Collection();
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            upgradeName();
            for (AbstractCard card : relatedCards){
                card.upgrade();
            }
        }
    }

    @Override
    public void setMaterialAndLevel(){

    }
}
