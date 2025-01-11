package tomorimod.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;

public class WarningUi {
    public Hitbox attackIntentHb;
    public Hitbox damageNumberHb;
    public int damageForShow;
    public int damageNum;

    public AbstractMonster monster;
    //private float x;
    //private float y;


    public WarningUi(AbstractMonster monster){
        this.attackIntentHb = new Hitbox(128.0f * Settings.scale, 128.0f * Settings.scale);
        this.damageNumberHb = new Hitbox(64.0f * Settings.scale, 32.0f * Settings.scale);
        this.monster=monster;
    }

    public void update(){
        damageForShow=getPublicField(monster, "intentDmg", Integer.class);
        //damageNum=getPublicField(this, "intentMultiAmt", Integer.class);

        // 先确定图标在屏幕上的坐标
        float iconX = AbstractDungeon.player.hb.cX - 96.0f * Settings.scale;
        float iconY = AbstractDungeon.player.hb.cY + 320.0f * Settings.scale;

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

    public void render(SpriteBatch sb){
        renderAttackIntent(sb);
        renderDamageNumber(sb);
        renderTip();

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
        float iconX = AbstractDungeon.player.hb.cX - 96.0f*Settings.scale;  // 让图标居中
        float iconY = AbstractDungeon.player.hb.cY + 320.0f*Settings.scale;  // 高度可以根据需求调整

        // 画图标（64 x 64 大小）
        sb.draw(intentTex, iconX, iconY, 128.0f*Settings.scale, 128.0f*Settings.scale);
    }

    public void renderDamageNumber(SpriteBatch sb) {
        if (damageForShow <= 0) {
            return;
        }

        float textX = AbstractDungeon.player.hb.cX-32.0f*Settings.scale;
        float textY = AbstractDungeon.player.hb.cY + 340.0f*Settings.scale;

        // 用红色来渲染伤害数字
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N,
                damageForShow+"", textX, textY, Color.WHITE);
    }

    public void renderTip(){
        if (this.attackIntentHb.hovered||this.damageNumberHb.hovered) {
            // 这类方法可以渲染一个简单的提示
            // 参数：Tip的左下角X, Tip的左下角Y, 标题, 内容
            TipHelper.renderGenericTip(
                    InputHelper.mX + 50.0F * Settings.scale,  // Tip往右下方一点
                    InputHelper.mY - 50.0F * Settings.scale,
                    "预警",
                    "敌人将对你造成 #b" + damageForShow+ " 点伤害。"
            );
        }
    }


    public static <T> T getPublicField(Object instance, String fieldName, Class<T> fieldType) {
        try {
            Field field = AbstractMonster.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Texture getAttackIntent(int dmg) {
        if (dmg < 5) {
            return ImageMaster.INTENT_ATK_1;
        } else if (dmg < 10) {
            return ImageMaster.INTENT_ATK_2;
        } else if (dmg < 15) {
            return ImageMaster.INTENT_ATK_3;
        } else if (dmg < 20) {
            return ImageMaster.INTENT_ATK_4;
        } else if (dmg < 25) {
            return ImageMaster.INTENT_ATK_5;
        } else {
            return dmg < 30 ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
        }
    }


}
