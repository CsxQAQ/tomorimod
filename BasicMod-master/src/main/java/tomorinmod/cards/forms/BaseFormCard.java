package tomorinmod.cards.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.actions.FormPowerChangeBlueAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.powers.forms.*;
import tomorinmod.savedata.customdata.FormsSaveData;
import tomorinmod.util.CardStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseFormCard extends BaseCard {

    public String formName;
    public static String curForm="";

    public static Map<FormInfo, Function<AbstractPlayer, AbstractPower>> powerMap = new HashMap<>();

    public BaseFormCard(String ID, CardStats info) {
        super(ID, info);
    }

    abstract public void setPowerName();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!AbstractDungeon.player.hasRelic(makeID("SystemRelic"))){
            if(curForm!=null&&!curForm.isEmpty()) {
                addToBot(new RemoveSpecificPowerAction(p, p, makeID(curForm)));
            }
        }else{
            FormsSaveData.getInstance().getForms().add(new FormInfo(formName,magicNumber));
            if(!curForm.equals(formName)&&AbstractDungeon.player.hasPower(makeID("DarkTomorinPower"))){
                for(AbstractPower power:p.powers){
                    if(power instanceof DarkTomorinPower){
                        ((DarkTomorinPower)power).applyEffectPower();
                        break;
                    }
                }
            }
        }
        curForm= formName;
        if(powerMap.get(new FormInfo(formName, magicNumber))!=null){
            addToBot(new ApplyPowerAction(p, p,
                    powerMap.get(new FormInfo(formName, magicNumber)).apply(p), magicNumber));
        }
        addToBot(new FormPowerChangeBlueAction(formName));
    }

    public static void clear(){
        curForm="";
    }

    static {
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinister.MAGIC),
                (player) -> new AstronomyMinisterPower(player,  AstronomyMinister.MAGIC));
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinister.MAGIC+AstronomyMinister.UPG_MAGIC),
                (player) -> new AstronomyMinisterPower(player,  AstronomyMinister.MAGIC+AstronomyMinister.UPG_MAGIC));
        powerMap.put(new FormInfo("DarkTomorinPower", DarkTomorin.MAGIC),
                (player) -> new DarkTomorinPower(player, DarkTomorin.MAGIC));
        powerMap.put(new FormInfo("DarkTomorinPower", DarkTomorin.MAGIC+DarkTomorin.UPG_MAGIC),
                (player) -> new DarkTomorinPower(player, DarkTomorin.MAGIC+DarkTomorin.UPG_MAGIC));
        powerMap.put(new FormInfo("DomainExpansionPower", DomainExpansion.MAGIC),
                (player) -> new DomainExpansionPower(player, DomainExpansion.MAGIC));
        powerMap.put(new FormInfo("DomainExpansionPower", DomainExpansion.MAGIC+DomainExpansion.UPG_MAGIC),
                (player) -> new DomainExpansionPower(player, DomainExpansion.MAGIC+DomainExpansion.UPG_MAGIC));
        powerMap.put(new FormInfo("MascotPower", Mascot.MAGIC),
                (player) -> new MascotPower(player, Mascot.MAGIC));
        powerMap.put(new FormInfo("MascotPower", Mascot.MAGIC+Mascot.UPG_MAGIC),
                (player) -> new MascotPower(player, Mascot.MAGIC+Mascot.UPG_MAGIC));
        powerMap.put(new FormInfo("PantPower", Pant.MAGIC),
                (player) -> new PantPower(player,Pant.MAGIC));
        powerMap.put(new FormInfo("PantPower", Pant.MAGIC+Pant.UPG_MAGIC),
                (player) -> new PantPower(player,  Pant.MAGIC+Pant.UPG_MAGIC));
        powerMap.put(new FormInfo("SingerPower", Singer.MAGIC),
                (player) -> new SingerPower(player,  Singer.MAGIC));
        powerMap.put(new FormInfo("SingerPower", Singer.MAGIC+Singer.UPG_MAGIC),
                (player) -> new SingerPower(player,  Singer.MAGIC+Singer.UPG_MAGIC));
        powerMap.put(new FormInfo("StrengthTomorinPower", StrengthTomorin.MAGIC),
                (player) -> new StrengthTomorinPower(player,  StrengthTomorin.MAGIC));
        powerMap.put(new FormInfo("StrengthTomorinPower", StrengthTomorin.MAGIC+StrengthTomorin.UPG_MAGIC),
                (player) -> new StrengthTomorinPower(player,  StrengthTomorin.MAGIC+StrengthTomorin.UPG_MAGIC));
    }

    public static class FormInfo {

        public String powerName;
        public int amount;

        @Override
        public String toString() {
            return "FormInfo{" +
                    "powerName='" + powerName + '\'' +
                    ", amount=" + amount +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FormInfo formInfo = (FormInfo) o;
            return amount == formInfo.amount && Objects.equals(powerName, formInfo.powerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(powerName, amount);
        }

        public FormInfo(String powerName, int amount) {
            this.powerName = powerName;
            this.amount = amount;
        }
    }
}



