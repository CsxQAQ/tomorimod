package tomorimod.cards.uikacard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import tomorimod.cards.BaseCard;
import tomorimod.monsters.CustomColorTrailEffectPatch;
import tomorimod.monsters.uika.ExhaustCardDelayEffect;
import tomorimod.monsters.uika.UikaMonster;
import tomorimod.util.CardStats;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static tomorimod.TomoriMod.imagePath;

public abstract class UikaCard extends BaseCard {

    public UikaCard(String ID, CardStats info) {
        super(ID, info);
        switch (info.cardType){
            default:
            case SKILL:
                setBackgroundTexture(imagePath("character/specialcardback/uika_skill.png"),
                        imagePath("character/specialcardback/uika_skill_p.png"));
                break;
            case ATTACK:
                setBackgroundTexture(imagePath("character/specialcardback/uika_attack.png"),
                        imagePath("character/specialcardback/uika_attack_p.png"));
                break;
            case POWER:
                setBackgroundTexture(imagePath("character/specialcardback/uika_power.png"),
                        imagePath("character/specialcardback/uika_power_p.png"));
                break;
        }
        setOrbTexture(imagePath("monsters/uikaorbs/energy_orb.png"),
                imagePath("monsters/uikaorbs/energy_orb_p.png"));

    }

    public void uikaUse(UikaMonster uikaMonster){
        showCardsDiscard(this);
        addToBot(new TrueWaitAction(UikaMonster.WAIT_TIME));
    }

    public void showCardsDiscard(UikaCard card){
        if(card.type.equals(AbstractCard.CardType.POWER)){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    card.untip();
                    card.unhover();
                    card.darken(true);
                    card.shrink(true);

                    Soul soul = new Soul();
                    CustomColorTrailEffectPatch.SoulFieldPatch.isUika.set(soul,true);
                    soul.empower(card);
                    try {
                        Field soulsField = SoulGroup.class.getDeclaredField("souls");
                        soulsField.setAccessible(true); // 绕过访问限制
                        ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(UikaMonster.goldenSouls);
                        souls.add(soul); // 修改字段
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    isDone=true;
                }
            });
        }
        else if(card.exhaust){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    showCardsExhaust(card);
                    isDone=true;
                }
            });
        }else{
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    card.untip();
                    card.unhover();
                    card.darken(true);
                    card.shrink(true);

                    // 创建新的 Soul
                    Soul soul = new Soul();
                    CustomColorTrailEffectPatch.SoulFieldPatch.isUika.set(soul,true);
                    soul.discard(card, false);
                    try {
                        Field soulsField = SoulGroup.class.getDeclaredField("souls");
                        soulsField.setAccessible(true); // 绕过访问限制
                        ArrayList<Soul> souls = (ArrayList<Soul>) soulsField.get(UikaMonster.goldenSouls);
                        souls.add(soul); // 修改字段
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    isDone=true;
                }
            });
        }

    }

    public void showCardsExhaust(UikaCard card){
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

        for (int i = 0; i < 90; i++) {
            AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(card.current_x, card.current_y));
        }
        for (int i = 0; i < 50; i++) {
            AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(card.current_x, card.current_y));
        }

        AbstractDungeon.effectsQueue.add(new ExhaustCardDelayEffect(card,0.5f));
    }

}
