package tomorinmod.cards.forms.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.BaseCard;
import tomorinmod.powers.forms.AstronomyMinisterPower;
import tomorinmod.savedata.customdata.SavePermanentForm;
import tomorinmod.util.CardStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseFormCard extends BaseCard {

    public String formName;
    public static String curForm="";

    public static Map<FormInfo, Function<AbstractPlayer, AbstractPower>> powerMap = new HashMap<>();

    static {
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinister.MAGIC,false),
                (player) -> new AstronomyMinisterPower(player, 1, AstronomyMinister.MAGIC,false));
        powerMap.put(new FormInfo("AstronomyMinisterPower", AstronomyMinister.MAGIC+AstronomyMinister.UPG_MAGIC,true),
                (player) -> new AstronomyMinisterPower(player, 1, AstronomyMinister.MAGIC+AstronomyMinister.UPG_MAGIC,true));
    }

    public BaseFormCard(String ID, CardStats info) {
        super(ID, info);
    }

    abstract public void setFormPower();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!AbstractDungeon.player.hasRelic(makeID("SystemRelic"))){
            if(curForm!=null&&!curForm.isEmpty()) {
                addToBot(new RemoveSpecificPowerAction(p, p, makeID(curForm)));
            }
            curForm= formName;
        }else{
            SavePermanentForm.getInstance().getForms().add(formName);
        }
        addToBot(new ApplyPowerAction(p, p,
                powerMap.get(new FormInfo(formName, magicNumber,upgraded)).apply(p), 1));
    }

    public static void clear(){
        curForm="";
    }

    public static class FormInfo {

        private String powerName;
        private int magicNumber;
        private boolean upgraded;

        public FormInfo(String powerName, int magicNumber, boolean upgraded) {
            this.powerName = powerName;
            this.magicNumber = magicNumber;
            this.upgraded = upgraded;
        }

        @Override
        public String toString() {
            return "FormInfo{" +
                    "powerName='" + powerName + '\'' +
                    ", magicNumber=" + magicNumber +
                    ", upgraded=" + upgraded +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FormInfo formInfo = (FormInfo) o;
            return magicNumber == formInfo.magicNumber && upgraded == formInfo.upgraded && Objects.equals(powerName, formInfo.powerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(powerName, magicNumber, upgraded);
        }
    }
}



