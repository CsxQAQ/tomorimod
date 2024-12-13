package tomorinmod.cards.music;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.monitors.LunfuyuMonitor;
import tomorinmod.util.CardStats;

public class Lunfuyu extends BaseMusicCard {
    public static final String ID = makeID(Lunfuyu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    public Lunfuyu() {
        super(ID, info);
        setDamage(0,0);
        damageInitialize();
    }

    private final static int MAGIC_COMMON =6;
    private final static int UPG_MAGIC_COMMON =3;

    private final static int MAGIC_UNCOMMON=8;
    private final static int UPG_MAGIC_UNCOMMON =4;

    private final static int MAGIC_RARE =8;
    private final static int UPG_MAGIC_RARE =4;

    public void damageInitialize(){
        if(musicRarity==null){
            setMagic(MAGIC_COMMON, UPG_MAGIC_COMMON);
            musicUpgradeDamage= UPG_MAGIC_COMMON;
            return;
        }
        switch (musicRarity){
            case COMMON:
                setMagic(MAGIC_COMMON, UPG_MAGIC_COMMON);
                musicUpgradeMagic= UPG_MAGIC_COMMON;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
                break;
            case UNCOMMON:
                setMagic(MAGIC_UNCOMMON, UPG_MAGIC_UNCOMMON);
                musicUpgradeMagic= UPG_MAGIC_UNCOMMON;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
                break;
            case RARE:
                setMagic(MAGIC_RARE, UPG_MAGIC_RARE);
                musicUpgradeMagic= UPG_MAGIC_RARE;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid rarity: " + musicRarity);
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void update(){
        super.update();
        if(musicRarity.equals(MusicRarity.RARE)){
            baseDamage= LunfuyuMonitor.hpChangeNum*magicNumber;
        }else{
            baseDamage=LunfuyuMonitor.hpIncreaseNum*magicNumber;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lunfuyu();
    }

}
