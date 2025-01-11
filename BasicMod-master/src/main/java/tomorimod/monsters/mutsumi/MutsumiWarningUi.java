package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.monsters.WarningUi;

public class MutsumiWarningUi extends WarningUi {

    public MutsumiMonster monster;

    public MutsumiWarningUi(MutsumiMonster monster) {
        super(monster);
        this.monster=monster;
    }

    @Override
    public void setDamageForShow(){
        damageForShow=monster.calculateDamageSingle(getPublicField(monster, "intentBaseDmg", Integer.class),AbstractDungeon.player);
        damageNum=getPublicField(monster, "intentMultiAmt", Integer.class);
    }

    @Override
    public void renderDamageNumber(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        float textX = AbstractDungeon.player.hb.cX-32.0f* Settings.scale;
        float textY = AbstractDungeon.player.hb.cY + 340.0f*Settings.scale;

        // 用红色来渲染伤害数字
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N,
                damageForShow+"x"+damageNum, textX, textY, Color.WHITE);
    }

    @Override
    public void renderTip(){
        if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
            // 这类方法可以渲染一个简单的提示
            // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
            TipHelper.renderGenericTip(
                    InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                    InputHelper.mY - 50.0F * Settings.scale,
                    "预警",
                    "敌人将使用群体攻击对你造成 #b" + damageForShow+ " 点伤害 #b"+damageNum+" 次。"
            );
        }
    }
}
