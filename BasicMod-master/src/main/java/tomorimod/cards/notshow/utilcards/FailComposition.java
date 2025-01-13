
package tomorimod.cards.notshow.utilcards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import tomorimod.actions.PoofAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

//这个设置为BaseMusicCard可能会出问题
public class FailComposition extends BaseCard implements WithoutMaterial, SpecialCard {
    public static final String ID = makeID(FailComposition.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public final int MAGIC=5;
    public final int UPG_MAGIC=0;


    public FailComposition() {
        super(ID, info);
        purgeOnUse=true;
        //isEthereal=true;
        setMagic(MAGIC,UPG_MAGIC);
        //updateDescription();
    }

//    @Override
//    public void updateDescription(){
//        if(AbstractDungeon.player!=null){
//            if(AbstractDungeon.player.hasRelic(makeID("VocalMicrophoneRelic"))){
//                this.rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
//            }else{
//                this.rawDescription=cardStrings.DESCRIPTION;
//            }
//        }
//        initializeDescription();
//    }

    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public void upgrade(){

    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return super.canUse(p,m)&&p.hasRelic(makeID("VocalMicrophoneRelic"));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new PoofAction(this));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p,p,magicNumber));
        if(p.hasRelic(makeID("VocalMicrophoneRelic"))){
            p.getRelic(makeID("VocalMicrophoneRelic")).flash();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FailComposition();
    }

}
