package tomorimod.cards.notshow.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.actions.FormPowerChangeBlueAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.powers.forms.*;
import tomorimod.savedata.customdata.FormsSaveData;
import tomorimod.util.CardStats;
import tomorimod.util.PlayerUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseFormCard extends BaseCard implements SpecialCard {

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
            if(!curForm.equals(formName)&&AbstractDungeon.player.hasPower(makeID("DarkTomoriPower"))){
                PlayerUtils.getFormPower(makeID("DarkTomoriPower")).applyEffectPower();
            }
            BaseMonmentCard.removeFromMasterDeck(this);
        }
        curForm= formName;
        if(powerMap.get(new FormInfo(formName, magicNumber))!=null){
            addToBot(new ApplyPowerAction(p, p,
                    powerMap.get(new FormInfo(formName, magicNumber)).apply(p), magicNumber));
        }
        addToBot(new FormPowerChangeBlueAction(formName));
    }

    @Override
    public void update(){
        super.update();
        if(AbstractDungeon.player!=null){
            if(AbstractDungeon.player.hasRelic(makeID("SystemRelic"))){
                purgeOnUse=true;
            }
        }
    }

    public static void clear(){
        curForm="";
    }

    static {
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinisterForm.MAGIC),
                (player) -> new AstronomyMinisterFormPower(player,  AstronomyMinisterForm.MAGIC));
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinisterForm.MAGIC+ AstronomyMinisterForm.UPG_MAGIC),
                (player) -> new AstronomyMinisterFormPower(player,  AstronomyMinisterForm.MAGIC+ AstronomyMinisterForm.UPG_MAGIC));
        powerMap.put(new FormInfo("DarkTomoriPower", DarkTomoriForm.MAGIC),
                (player) -> new DarkTomoriFormPower(player, DarkTomoriForm.MAGIC));
        powerMap.put(new FormInfo("DarkTomoriPower", DarkTomoriForm.MAGIC+ DarkTomoriForm.UPG_MAGIC),
                (player) -> new DarkTomoriFormPower(player, DarkTomoriForm.MAGIC+ DarkTomoriForm.UPG_MAGIC));
        powerMap.put(new FormInfo("DomainExpansionPower", GravityDomainForm.MAGIC),
                (player) -> new GravityDomainFormPower(player, GravityDomainForm.MAGIC));
        powerMap.put(new FormInfo("DomainExpansionPower", GravityDomainForm.MAGIC+ GravityDomainForm.UPG_MAGIC),
                (player) -> new GravityDomainFormPower(player, GravityDomainForm.MAGIC+ GravityDomainForm.UPG_MAGIC));
        powerMap.put(new FormInfo("MascotPower", MascotForm.MAGIC),
                (player) -> new MascotFormPower(player, MascotForm.MAGIC));
        powerMap.put(new FormInfo("MascotPower", MascotForm.MAGIC+ MascotForm.UPG_MAGIC),
                (player) -> new MascotFormPower(player, MascotForm.MAGIC+ MascotForm.UPG_MAGIC));
        powerMap.put(new FormInfo("PantPower", PantForm.MAGIC),
                (player) -> new PantFormPower(player, PantForm.MAGIC));
        powerMap.put(new FormInfo("PantPower", PantForm.MAGIC+ PantForm.UPG_MAGIC),
                (player) -> new PantFormPower(player,  PantForm.MAGIC+ PantForm.UPG_MAGIC));
        powerMap.put(new FormInfo("SingerPower", SingerForm.MAGIC),
                (player) -> new SingerFormPower(player,  SingerForm.MAGIC));
        powerMap.put(new FormInfo("SingerPower", SingerForm.MAGIC+ SingerForm.UPG_MAGIC),
                (player) -> new SingerFormPower(player,  SingerForm.MAGIC+ SingerForm.UPG_MAGIC));
        powerMap.put(new FormInfo("StrengthTomoriPower", StrengthTomoriForm.MAGIC),
                (player) -> new StrengthTomoriFormPower(player,  StrengthTomoriForm.MAGIC));
        powerMap.put(new FormInfo("StrengthTomoriPower", StrengthTomoriForm.MAGIC+ StrengthTomoriForm.UPG_MAGIC),
                (player) -> new StrengthTomoriFormPower(player,  StrengthTomoriForm.MAGIC+ StrengthTomoriForm.UPG_MAGIC));
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



