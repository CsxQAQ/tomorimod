package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import tomorimod.monsters.WarningUi;

public class SoyoWarningUi extends WarningUi {

    public MutsumiMonster monster;
    public SoyoMonster soyo;

    public SoyoWarningUi(MutsumiMonster monster,SoyoMonster soyo) {
        super(monster);
        this.monster=monster;
        this.soyo=soyo;
    }

    @Override
    public void setDamageForShow(){
        damageForShow=monster.calculateDamageSingle(getPublicField(monster, "intentBaseDmg", Integer.class),soyo);
        damageNum=getPublicField(monster, "intentMultiAmt", Integer.class);
    }

    @Override
    public void initializePositionAndHb(){
        // 先确定图标在屏幕上的坐标
        float iconX = soyo.hb.cX - 96.0f * Settings.scale;
        float iconY = soyo.hb.cY + 320.0f * Settings.scale;

        // 把 attackIntentHb 的中心移到图标正中央
        // 注意要加上宽/高的一半，以使其对准图标中心
        attackIntentHb.move(iconX + (128.0f * Settings.scale) / 2f,
                iconY + (128.0f * Settings.scale) / 2f);
        attackIntentHb.update(); // 必须调用，才能检测鼠标悬浮

        // 再确定伤害数字的坐标
//        float textX = AbstractDungeon.player.hb.cX - 32.0f * Settings.scale;
//        float textY = AbstractDungeon.player.hb.cY + 340.0f * Settings.scale;

        float textX=iconX+64.0f*Settings.scale;
        float textY=iconY+20.0f*Settings.scale;

        // 让 damageNumberHb 跟随伤害数字区域
        damageNumberHb.move(textX + (64.0f * Settings.scale) / 2f,
                textY + (32.0f * Settings.scale) / 2f);
        damageNumberHb.update();
    }

    @Override
    public void renderDamageNumber(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        float textX = soyo.hb.cX-32.0f* Settings.scale;
        float textY = soyo.hb.cY + 340.0f*Settings.scale;

        // 用红色来渲染伤害数字
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N,
                damageForShow+"x"+damageNum, textX, textY, Color.WHITE);
    }

    public void renderAttackIntent(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        // 拿到对应的纹理
        Texture intentTex = getAttackIntent(damageForShow);
        if (intentTex == null) {
            return;
        }

        // 设置渲染颜色和位置
        // 注意：玩家的 Hitbox 中心是 hb.cX, hb.cY，可以按需求微调
        sb.setColor(Color.WHITE.cpy());
        float iconX = soyo.hb.cX - 96.0f*Settings.scale;  // 让图标居中
        float iconY = soyo.hb.cY + 320.0f*Settings.scale;  // 高度可以根据需求调整

        // 画图标（64 x 64 大小）
        sb.draw(intentTex, iconX, iconY, 128.0f*Settings.scale, 128.0f*Settings.scale);
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
                    "敌人将使用群体攻击对 #y长崎素世 造成 #b" + damageForShow+ " 点伤害 #b"+damageNum+" 次。"
            );
        }
    }
}
