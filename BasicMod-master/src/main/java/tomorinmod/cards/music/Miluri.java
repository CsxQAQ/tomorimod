package tomorinmod.cards.music;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class Miluri extends BaseMusicCard {
    public static final String ID = makeID(Miluri.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    public Miluri() {
        super(ID, info);
        damageInitialize();
    }

    private final static int DAMAG_COMMON=5;
    private final static int UPG_DAMAGE_COMMON=3;

    private final static int DAMAG_UNCOMMON=7;
    private final static int UPG_DAMAGE_UNCOMMON=4;

    private final static int DAMAG_RARE=7;
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


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int shineAmount=0;
        if(p.getPower(makeID("Shine"))!=null){
            shineAmount = p.getPower(makeID("Shine")).amount;
        }

        if(this.musicRarity.equals(MusicRarity.RARE)){
            for (int i = 0; i < shineAmount + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }else{
            for (int i = 0; i < shineAmount*2 + 1; i++) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

                if (target != null) {
                    addToBot(new DamageAction(target, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Miluri();
    }

}
