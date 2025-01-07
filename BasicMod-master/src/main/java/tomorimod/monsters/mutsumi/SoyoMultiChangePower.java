package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.patches.AbstractCardSetMaterialPatch;
import tomorimod.powers.BasePower;

import java.util.Objects;

import static tomorimod.TomoriMod.makeID;

public class SoyoMultiChangePower extends BasePower {
    public static final String POWER_ID = makeID(SoyoMultiChangePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private String color=null;
    public SoyoMultiChangePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+DESCRIPTIONS[1]+DESCRIPTIONS[2]+DESCRIPTIONS[3];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // 定义常量
        final String AQUARIUM_PASS = "aquariumpass";
        final String SOYO_FORM_POWER_ID = makeID("SoyoFormPower");

        // 提取材料和等级到局部变量
        String material = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.material.get(card);
        if (!material.equals("")) {
            int level = AbstractCardSetMaterialPatch.AbstractCardFieldPatch.level.get(card);

            if (this.color == null) {
                // 根据材料设置颜色
                if (!material.equals(AQUARIUM_PASS)) {
                    this.color = material;
                    addToBot(new ApplyPowerAction(owner, owner, new SoyoFormPower(owner, level, material)));
                }
            } else {
                if (!this.color.equals(material)) {
                    if (material.equals(AQUARIUM_PASS)) {
                        // 当材料为 aquariumpass 时，应用当前颜色的 SoyoFormPower
                        addToBot(new ApplyPowerAction(owner, owner, new SoyoFormPower(owner, level, this.color)));
                    } else {
                        // 移除现有的 SoyoFormPower 并应用新的
                        addToBot(new RemoveSpecificPowerAction(owner, owner, SOYO_FORM_POWER_ID));
                        this.color = material;
                        addToBot(new ApplyPowerAction(owner, owner, new SoyoFormPower(owner, level, material)));
                    }
                } else {
                    // 材料相同，直接应用 SoyoFormPower
                    addToBot(new ApplyPowerAction(owner, owner, new SoyoFormPower(owner, level, material)));
                }
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){
        if(owner.hasPower(makeID("SoyoFormPower"))){
            addToBot(new RemoveSpecificPowerAction(owner,owner,makeID("SoyoFormPower")));
        }
        this.color=null;
    }



}
