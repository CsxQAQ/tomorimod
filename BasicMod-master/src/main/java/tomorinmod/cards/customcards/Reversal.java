package tomorinmod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.ReversalAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorinmod.BasicMod.imagePath;

public class Reversal extends BaseCard {
    public static final String ID = makeID(Reversal.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final ArrayList<String> imagePaths = new ArrayList<>();
    private float timeElapsed = 0f;
    private float interval = 0.05f;
    private int currentImageIndex = 0;
    private static final int totalImages = 43;


    static {
        for (int i = 0; i <= totalImages; i++) {

            imagePaths.add(imagePath("cards/Reversal/" + i + ".png"));
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
    public Reversal() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReversalAction(p));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Reversal();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // 更新卡牌名称，显示为“升级版”
            upgradeBaseCost(0); // 将费用从 1 降为 0
        }
    }
}
