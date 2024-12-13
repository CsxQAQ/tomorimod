package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Mixingjiao extends BaseMusicCard {
    public static final String ID = makeID(Mixingjiao.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public Mixingjiao() {
        super(ID, info);
        damageInitialize();
    }

    private final static int DAMAG_COMMON=6;
    private final static int UPG_DAMAGE_COMMON=3;

    private final static int DAMAG_UNCOMMON=10;
    private final static int UPG_DAMAGE_UNCOMMON=4;

    private final static int DAMAG_RARE=10;
    private final static int UPG_DAMAGE_RARE=4;

    public void damageInitialize(){
        if(musicRarity==null){
            setDamage(DAMAG_COMMON,UPG_DAMAGE_COMMON);
            musicUpgradeDamage=UPG_DAMAGE_COMMON;
            return;
        }
        switch (musicRarity){
            case COMMON:
                setDamage(DAMAG_COMMON,UPG_DAMAGE_COMMON);
                musicUpgradeDamage=UPG_DAMAGE_COMMON;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
                break;
            case UNCOMMON:
                setDamage(DAMAG_UNCOMMON,UPG_DAMAGE_UNCOMMON);
                musicUpgradeDamage=UPG_DAMAGE_UNCOMMON;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
                break;
            case RARE:
                setDamage(DAMAG_RARE,UPG_DAMAGE_RARE);
                musicUpgradeDamage=UPG_DAMAGE_RARE;
                this.rawDescription = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid rarity: " + musicRarity);
        }
        initializeDescription();
    }

    public void autoUse() {
        AbstractCard copy = makeSameInstanceOf();
        copy.purgeOnUse = true;
        copy.current_x = Settings.WIDTH + copy.hb.width + 50.0F * Settings.scale;
        copy.current_y = Settings.HEIGHT + copy.hb.height + 50.0F * Settings.scale;
        copy.target_x = Settings.WIDTH / 2.0F;
        copy.target_y = Settings.HEIGHT / 2.0F;
        addToBot(new NewQueueCardAction(copy, true, true, true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.contains(this)){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }else{
            if(this.musicRarity.equals(MusicRarity.RARE)){
                addToBot(new DamageAction(m, new DamageInfo(p, damage*3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }else{
                addToBot(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mixingjiao();
    }


}
