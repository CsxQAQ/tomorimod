
package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

import java.util.ArrayList;

public class Yinyihui extends BaseMusic {
    public static final String ID = makeID(Yinyihui.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Yinyihui() {
        super(ID, info);
        this.musicUpgradeDamage=UPG_BLOCK;
        this.setBlock(BLOCK,UPG_BLOCK);
    }


    private final static int BLOCK=10;
    private final static int UPG_BLOCK=4;


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int tempBlock = AbstractDungeon.player.currentBlock;
                addToBot(new DamageAllEnemiesAction(p, tempBlock, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yinyihui();
    }

}
