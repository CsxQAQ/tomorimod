package tomorinmod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class RenderUtils {
    public static float RenderBadge(SpriteBatch sb, AbstractCard card, Texture texture, float offset_y, float alpha) {
        Vector2 offset = new Vector2(135.0F, 189.0F + offset_y);
        DrawOnCardAuto(sb, card, texture, offset, 64.0F, 64.0F, Color.WHITE, alpha, 1.0F);
        return 38.0F;
    }

    public static void DrawOnCardAuto(SpriteBatch sb, AbstractCard card, Texture img, Vector2 offset, float width, float height, Color color, float alpha, float scaleModifier) {
        if (card.angle != 0.0F) {
            offset.rotate(card.angle);
        }
        offset.scl(Settings.scale * card.drawScale);
        DrawOnCardCentered(sb, card, new Color(color.r, color.g, color.b, alpha), img, card.current_x + offset.x, card.current_y + offset.y, width, height, scaleModifier);
    }

    public static void DrawOnCardCentered(SpriteBatch sb, AbstractCard card, Color color, Texture img, float drawX, float drawY, float width, float height, float scaleModifier) {
        float scale = card.drawScale * Settings.scale * scaleModifier;
        sb.setColor(color);
        sb.draw(img, drawX - width / 2.0F, drawY - height / 2.0F, width / 2.0F, height / 2.0F, width, height, scale, scale, card.angle, 0, 0, img.getWidth(), img.getHeight(), false, false);
    }
}
