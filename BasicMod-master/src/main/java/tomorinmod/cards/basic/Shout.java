package tomorinmod.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CardStats;

import java.util.Iterator;

import static tomorinmod.BasicMod.imagePath;

public class Shout extends BaseCard {
    public static final String ID = makeID(Shout.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 5;

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;

    private int isFlipped = 0;


    public Shout() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
    }

    private void updateCardTypeAndTarget() {
        if (this.isFlipped == 0) {
            this.type = CardType.ATTACK;  // 攻击卡
            this.target=CardTarget.ENEMY;
        } else {
            this.type = CardType.SKILL;   // 技能卡
            this.target=CardTarget.SELF;
        }
    }

    private void updateDescription() {
        if (this.isFlipped == 0) {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
        } else {
            this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.isFlipped = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() % 2;
        this.updateDescription();
        this.updateCardTypeAndTarget();
        this.updateCardImage(); 
    }

    private void updateCardImage() {
        if (this.isFlipped == 0) {
            this.loadCardImage(imagePath("cards/attack/default.png")); // 加载正面图片
        } else {
            this.loadCardImage(imagePath("cards/attack/Shout.png"));  // 加载反面图片
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.isFlipped == 0){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }else {
            addToBot(new GainBlockAction(p, block));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Shout();
    }

}