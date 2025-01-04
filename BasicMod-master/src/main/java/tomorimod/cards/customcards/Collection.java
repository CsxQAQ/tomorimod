package tomorimod.cards.customcards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

public class Collection extends BaseCard implements WithoutMaterial {
    public static final String ID = makeID(Collection.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public int previewIndex = 0;
    public float previewTimer = 1.0f;
    public static final float PREVIEW_INTERVAL = 1.5f; // 每张卡显示的时间
    public ArrayList<AbstractCard> relatedCards=new ArrayList<>();

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
        this.cardsToPreview=new Green();
        relatedCards.add(new Green());
        relatedCards.add(new Yellow());
        relatedCards.add(new Red());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardGroup=new ArrayList<>();
        cardGroup.add(new Green());
        cardGroup.add(new Yellow());
        cardGroup.add(new Red());

        if (this.upgraded){
            for (AbstractCard card : cardGroup){
                card.upgrade();
            }
        }
        for (AbstractCard card : cardGroup){
            card.upgrade();
            card.exhaust=true;
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


}
