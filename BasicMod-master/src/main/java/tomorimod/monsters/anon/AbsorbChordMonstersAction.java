package tomorimod.monsters.anon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.Settings;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import tomorimod.monsters.anon.ChordMonster;

import java.util.ArrayList;
import java.util.List;

public class AbsorbChordMonstersAction extends AbstractGameAction {
    private AbstractMonster anonMonster;          // 吸收者 (AnonMonster)
    private List<ChordMonster> chordMonsters;     // 被吸收的 ChordMonster 列表

    // 记录每只怪物的初始半径 & 初始角度 & 旋转速度
    private float[] startRadius;  // 初始半径 r0
    private float[] startAngle;   // 初始角度 theta0
    private float[] rotSpeed;     // 每只怪物的旋转速度(可随机)

    private float duration = 0.5f; // 整个动画时长，可以自行调节
    private float time = 0f;

    private float centerX, centerY; // AnonMonster 的坐标

    public AbsorbChordMonstersAction(AbstractMonster anonMonster, List<ChordMonster> chordMonsters) {
        this.anonMonster = anonMonster;
        this.chordMonsters = chordMonsters;
        this.actionType = ActionType.SPECIAL;

        // 记录中心
        this.centerX = anonMonster.drawX;
        this.centerY = anonMonster.drawY;

        // 分配数组
        int count = chordMonsters.size();
        this.startRadius = new float[count];
        this.startAngle = new float[count];
        this.rotSpeed = new float[count];

        // 计算每只 ChordMonster 相对中心的初始值
        for (int i = 0; i < count; i++) {
            ChordMonster cm = chordMonsters.get(i);

            float dx = cm.drawX - centerX;
            float dy = cm.drawY - centerY;

            // 初始半径
            float r0 = (float)Math.sqrt(dx*dx + dy*dy);
            this.startRadius[i] = r0;

            // 初始角度：atan2(y, x)
            float theta0 = (float)Math.atan2(dy, dx);
            this.startAngle[i] = theta0;

            // 随机一个旋转速度（可以正负，从而逆时针/顺时针）
            // 也可以用固定值
            float speed = 3.0f + (float)(Math.random() * 2.0f); // 3.0 ~ 5.0
            // 让部分概率逆时针
            if (Math.random() < 0.5) {
                speed = -speed;
            }
            this.rotSpeed[i] = speed;
        }
    }

    @Override
    public void update() {
        time += Gdx.graphics.getDeltaTime();
        float progress = time / duration;
        if (progress > 1.0f) {
            progress = 1.0f;
        }

        int count = chordMonsters.size();

        for (int i = 0; i < count; i++) {
            ChordMonster cm = chordMonsters.get(i);

            // 如果已死/离场，就不必处理
            if (cm.isDeadOrEscaped()) {
                continue;
            }

            // 1. 计算当前半径：从 r0 渐渐过渡到 0
            float r0 = startRadius[i];
            float radius = Interpolation.linear.apply(r0, 0f, progress);
            // 也可以换成更平滑的插值，比如 Interpolation.pow2In

            // 2. 计算当前角度
            float theta0 = startAngle[i];
            float speed = rotSpeed[i];
            // 这里做一个简单的：angle = theta0 + speed * progress * (some factor)
            // 想非匀速可以用 progress^2 等
            float angleNow = theta0 + speed * (progress * progress);

            // 3. 得到 x,y
            float newX = centerX + radius * (float)Math.cos(angleNow);
            float newY = centerY + radius * (float)Math.sin(angleNow) + 20.0f*Settings.scale;

            // 4. 更新怪物位置
            cm.drawX = newX;
            cm.drawY = newY;
        }

        // 当动画结束时，让所有 ChordMonster die()
        if (time >= duration) {
            for (ChordMonster cm : chordMonsters) {
                if (!cm.isDeadOrEscaped()) {
                    cm.hideHealthBar();
                    cm.die();
                }
            }
            this.isDone = true;
        }
    }
}
