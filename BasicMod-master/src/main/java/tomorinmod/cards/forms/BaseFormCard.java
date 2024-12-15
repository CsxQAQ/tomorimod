package tomorinmod.cards.forms;

import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.cards.music.FailComposition;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.savedata.customdata.SaveForm;
import tomorinmod.util.CardStats;

public abstract class BaseFormCard extends BaseMonmentCard {

    public String formPower;

    public BaseFormCard(String ID, CardStats info) {
        super(ID, info);
        //tags.add(CardTags.HEALING);
    }

    abstract public void setFormPower();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String curForm=SaveForm.getInstance().getForm();

        if(curForm!=null&&!curForm.isEmpty()){
            if(!curForm.equals(formPower)) {
                if(AbstractDungeon.player.hasRelic(makeID("MicrophoneRelic"))) {
                    AbstractRelic relic = AbstractDungeon.player.getRelic(makeID("MicrophoneRelic"));
                    relic.flash();
                    HandleFormsMonitor.applyCurrentFormEffect(curForm);
                }
                addToBot(new RemoveSpecificPowerAction(p, p, makeID(curForm)));
                SaveForm.getInstance().changeForm(formPower);
            }
        }

        super.use(p,m);
    }
}
