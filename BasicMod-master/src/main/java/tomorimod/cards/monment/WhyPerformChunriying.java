package tomorimod.cards.monment;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;

public class WhyPerformChunriying extends BaseMonmentCard {

    public static final String ID = makeID(WhyPerformChunriying.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );


    public WhyPerformChunriying() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        this.exhaust=true;
    }

    public static final int MAGIC = 1;
    public static final int UPG_MAGIC = 1;

//    public static final ArrayList<String> imagePaths = new ArrayList<>();
//    public float timeElapsed = 0f;
//    public float interval = 0.04f;
//    public int currentImageIndex = 0;
//    public static final int totalImages = 61;


//    static {
//        for (int i = 1; i <= totalImages; i++) {
//
//            imagePaths.add(imagePath("cards/WhyPerformChunriying/" + i + ".png"));
//        }
//    }

//    @Override
//    public void update() {
//        super.update();
//        timeElapsed += graphics.getDeltaTime();
//
//        if (timeElapsed >= interval) {
//            timeElapsed = 0f;
//            currentImageIndex++;
//
//            if (currentImageIndex >= totalImages) {
//                currentImageIndex = 0;
//            }
//
//            loadCardImage(imagePaths.get(currentImageIndex));
//        }
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new StunMonsterAction(monster, p, magicNumber));
            }
        }
        super.use(p,m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new WhyPerformChunriying();
    }
}
