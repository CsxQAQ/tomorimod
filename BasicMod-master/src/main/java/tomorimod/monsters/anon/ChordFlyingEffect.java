package tomorimod.monsters.anon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChordFlyingEffect extends AbstractGameEffect {
    private static final float DURATION = 0.5f;  // 总时长 1 秒
    private float elapsedTime = 0.0f;

    // 坐标相关
    private float bossX;
    private float bossY;
    private float playerX;
    private float playerY;

    // 运动轨迹相关
    private float radius;              // 绕圈的半径（可自行调节）
    private float startAngle;          // 绕圈开始角度（弧度制 or 角度制都行，下面以弧度为例）
    private float endAngle;            // 绕圈结束角度
    private float circleDuration = 0.25f; // 绕圈占用前 0.5 秒
    // 记录绕圈结束时的坐标，用于后面直线飞往玩家
    private float circleEndX;
    private float circleEndY;

    // 贴图相关
    private Texture noteImg;

    public ChordFlyingEffect(float bossX, float bossY, float playerX, float playerY, Texture noteImg) {
        this.bossX = bossX;
        this.bossY = bossY;
        this.playerX = playerX;
        this.playerY = playerY;
        this.noteImg = noteImg;

        this.color=Color.WHITE.cpy();

        // 你可以在这里自由调节音符的初始半径、角度
        this.radius = 200.0f * Settings.scale; // 假设一个半径
        this.startAngle = (float) Math.toRadians(0);   // 从 0 度开始
        this.endAngle   = (float) Math.toRadians(180); // 转半圈到 180 度

        this.duration = DURATION;  // 父类的 duration
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        elapsedTime += delta;

        // 如果音符运动时间超了，我们标记结束
        if (elapsedTime > duration) {
            isDone = true;
            return;
        }

        // 进度条(0 ~ 1)
        float t = elapsedTime / duration;

        if (t <= circleDuration / duration) {
            // 第一段：绕 Boss 转圈
            float circleT = t / (circleDuration / duration); // 0 ~ 1
            // 计算当前角度
            float currentAngle = startAngle + (endAngle - startAngle) * circleT;
            // 计算 x, y 坐标
            float noteX = bossX + (float) Math.cos(currentAngle) * radius;
            float noteY = bossY + (float) Math.sin(currentAngle) * radius;

            // 将此刻的 x,y 作为“绕圈结束点”
            circleEndX = noteX;
            circleEndY = noteY;
        } else {
            // 第二段：直线飞向玩家
            float linearT = (elapsedTime - circleDuration) / (duration - circleDuration);
            // 做一个插值，Interpolation.linear 或者 swingOut 之类都可以
            float noteX = Interpolation.linear.apply(circleEndX, playerX, linearT);
            float noteY = Interpolation.linear.apply(circleEndY, playerY, linearT);

            // 当 noteX,noteY 非常接近 playerX, playerY 时，可在此帧触发伤害 or 与攻击同步
            // if (linearT >= 1.0f) { // 到玩家了
            //     触发伤害 or 伤害结算
            // }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (isDone) return;

        // 计算一下当前的进度，获取实时坐标
        float t = elapsedTime / duration;

        float noteX, noteY;
        if (t <= circleDuration / duration) {
            // 绕圈部分
            float circleT = t / (circleDuration / duration);
            float currentAngle = startAngle + (endAngle - startAngle) * circleT;
            noteX = bossX + (float) Math.cos(currentAngle) * radius;
            noteY = bossY + (float) Math.sin(currentAngle) * radius;
        } else {
            // 直线部分
            float linearT = (elapsedTime - circleDuration) / (duration - circleDuration);
            noteX = Interpolation.linear.apply(circleEndX, playerX, linearT);
            noteY = Interpolation.linear.apply(circleEndY, playerY, linearT);
        }

        sb.setColor(this.color);
        sb.draw(noteImg,
                noteX - noteImg.getWidth() * 0.5f,
                noteY - noteImg.getHeight() * 0.5f,
                noteImg.getWidth() * 0.5f,
                noteImg.getHeight() * 0.5f,
                noteImg.getWidth(),
                noteImg.getHeight(),
                Settings.scale,
                Settings.scale,
                0.0f,
                0,
                0,
                noteImg.getWidth(),
                noteImg.getHeight(),
                false,
                false);
    }

    @Override
    public void dispose() {
        // 如果你的贴图是在外面加载复用的，就不在这里 dispose
        // 如果是每次 new Texture，则需要在这里 dispose
    }
}
