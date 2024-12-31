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
    private static final CardStats info = new CardStats(
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

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final ArrayList<String> imagePaths = new ArrayList<>();
    private float timeElapsed = 0f;
    private float interval = 0.04f;
    private int currentImageIndex = 0;
    private static final int totalImages = 61;


    static {
        for (int i = 1; i <= totalImages; i++) {

            imagePaths.add(imagePath("cards/WhyPerformChunriying/" + i + ".png"));
        }
    }

    @Override
    public void update() {
        super.update();
        timeElapsed += graphics.getDeltaTime();

        if (timeElapsed >= interval) {
            timeElapsed = 0f;
            currentImageIndex++;

            if (currentImageIndex >= totalImages) {
                currentImageIndex = 0;
            }

            loadCardImage(imagePaths.get(currentImageIndex));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()&&!monster.hasPower(makeID("FriendlyMonsterPower"))) {
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
