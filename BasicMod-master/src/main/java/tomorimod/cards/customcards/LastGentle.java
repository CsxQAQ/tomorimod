package tomorimod.cards.customcards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.cardactions.ReversalAction;
import tomorimod.cards.BaseCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static tomorimod.TomoriMod.imagePath;

public class LastGentle extends BaseCard {
    public static final String ID = makeID(LastGentle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
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
    public LastGentle() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReversalAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LastGentle();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
