package tomorimod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorimod.relics.SystemRelic;

import static tomorimod.TomoriMod.imagePath;
import static tomorimod.TomoriMod.makeID;

public class SystemEvent extends PhasedEvent {
    public static final String ID = makeID(SystemEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String IMG = imagePath("events/AnonMonster.png");

    public SystemEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0])
                        .setOptionResult((i)->{
                            AbstractRelic relic = new SystemRelic();
                            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
                            //不要卡牌奖励要手动设置，真是日狗
                            AbstractDungeon.getCurrRoom().event.noCardsInRewards=true;
                            AbstractDungeon.combatRewardScreen.open();
                            this.imageEventText.clearRemainingOptions();
                            transitionKey("ConnectSystem");
                        })
                )

                .addOption(OPTIONS[1], (i)->{
                    openMap();
                }));

        registerPhase("ConnectSystem", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2], (i)->openMap()));

        transitionKey("start");
    }
}