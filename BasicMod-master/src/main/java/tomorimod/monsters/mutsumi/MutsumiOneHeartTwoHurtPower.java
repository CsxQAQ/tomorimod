package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class MutsumiOneHeartTwoHurtPower extends BasePower {
    public static final String POWER_ID = makeID(MutsumiOneHeartTwoHurtPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private SoyoMonster soyoMonster;

    public MutsumiOneHeartTwoHurtPower(AbstractCreature owner, SoyoMonster soyoMonster) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        loadRegion("surrounded");
        this.soyoMonster=soyoMonster;
    }

//    @Override
//    public void atEndOfTurn(boolean isPlaye) {
//        if(owner instanceof MutumiMonster){
//            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                if (m instanceof SoyoMonster && !m.isDeadOrEscaped()) {
//                    ((MutumiMonster) owner).target=m;
//                    if(m.hasPower(makeID("BehindAttackPower"))){
//                        addToBot(new RemoveSpecificPowerAction(m,owner,makeID("BehindAttackPower")));
//                        addToBot(new ApplyPowerAction(AbstractDungeon.player,owner,
//                                new BehindAttackPower(AbstractDungeon.player)));
//                    }
//                }
//            }
//        }
//    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            if(owner instanceof MutsumiMonster){
                ((MutsumiMonster) owner).target=info.owner;
                if(info.owner.hasPower(makeID("BehindAttackPower"))){
                    if(info.owner==AbstractDungeon.player){
                        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,makeID("BehindAttackPower")));
                        if(soyoMonster!=null&&!soyoMonster.isDeadOrEscaped()){
                            addToBot(new ApplyPowerAction(soyoMonster,owner, new BehindAttackPower(soyoMonster)));
                        }
                    }else if(info.owner==soyoMonster){
                        addToBot(new RemoveSpecificPowerAction(soyoMonster,owner,makeID("BehindAttackPower")));
                        addToBot(new ApplyPowerAction(AbstractDungeon.player,owner, new BehindAttackPower(AbstractDungeon.player)));
                    }

                }
            }
        }
        return damageAmount;
    }

//    @Override
//    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

//        if(card.type==AbstractCard.CardType.ATTACK){
//            if(owner instanceof MutumiMonster){
//                ((MutumiMonster) owner).target=AbstractDungeon.player;
//                if(AbstractDungeon.player.hasPower(makeID("BehindAttackPower"))){
//                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,makeID("BehindAttackPower")));
//
//                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                        if (m instanceof SoyoMonster && !m.isDeadOrEscaped()) {
//                            addToBot(new ApplyPowerAction(m,owner,
//                                    new BehindAttackPower(m)));
//                        }
//                    }
//                }
//            }
//        }
//        return damage;
//    }

}
