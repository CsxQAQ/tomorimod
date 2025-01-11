package tomorimod.monsters.uika;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.customcards.LightAndShadow;
import tomorimod.cards.customcards.NeedAnon;
import tomorimod.cards.uikacard.UikaLastOne;
import tomorimod.cards.uikacard.UikaLiveForever;
import tomorimod.monsters.WarningUi;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static tomorimod.TomoriMod.makeID;

public class UikaWarningUi extends WarningUi {
    public UikaMonster uikaMonster;
    public int gravityDamageForShow;

    public UikaWarningUi(UikaMonster monster) {
        super(monster);
        uikaMonster=monster;
    }

    @Override
    public void setDamageForShow(){
        if(!damageFrozen){
            damageForShow =calculate().get(0);
            gravityDamageForShow =calculate().get(1);
        }
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
                    "敌人将对你造成 #b" + damageForShow+ " 点伤害。 NL #b"+(damageForShow-gravityDamageForShow) +
                            " 点来自攻击。 NL #b"+gravityDamageForShow+" 点来自 #y重力 。"
            );
        }
    }


    public ArrayList<Integer> calculate(){
        int damageNum=0;
        int gravityDamageNum=0;
        int monsterDamage = getPublicField(monster, "intentDmg", Integer.class);
        int attackCount = getPublicField(monster, "intentMultiAmt", Integer.class);
        if(attackCount==-1){
            attackCount=1;
        }
        int gravityAmount= MonsterUtils.getPowerNum(monster, "GravityPower");
        int divergeWorldAmount=MonsterUtils.getPowerNum(monster, "divergeWorldPower");
        int shineAmount=MonsterUtils.getPowerNum(monster, "shineAmount");

        if(uikaMonster.cardForShow1!=null){
            if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaMygoTogether"))){
                gravityAmount++;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLiveForever"))){
                gravityAmount+= UikaLiveForever.MAGIC;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLightAndShadow"))){
                if(gravityAmount>=shineAmount){
                    gravityAmount+= LightAndShadow.MAGIC;
                }else{
                    shineAmount+=LightAndShadow.MAGIC;
                }
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaNeedAnon"))){
                gravityAmount+=gravityAmount* NeedAnon.MAGIC;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaDivergeWorld"))){
                divergeWorldAmount++;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLastGentle"))){
                int tmp=gravityAmount;
                gravityAmount=shineAmount;
                shineAmount=tmp;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaStrike"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaLastOne"))){
                shineAmount+= UikaLastOne.MAGIC;
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaPoemInsteadOfSong"))){
                shineAmount+=uikaMonster.getDebuffNum();
            }else if(uikaMonster.cardForShow1.cardID.equals(makeID("UikaTwoMoon"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }
        }

        if(uikaMonster.cardForShow2!=null){
            if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaMygoTogether"))){
                gravityAmount++;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLiveForever"))){
                gravityAmount+=UikaLiveForever.MAGIC;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLightAndShadow"))){
                if(gravityAmount>=shineAmount){
                    gravityAmount+=LightAndShadow.MAGIC;
                }else{
                    shineAmount+=LightAndShadow.MAGIC;
                }
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaNeedAnon"))){
                gravityAmount+=gravityAmount*NeedAnon.MAGIC;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaDivergeWorld"))){
                divergeWorldAmount++;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLastGentle"))){
                int tmp=gravityAmount;
                gravityAmount=shineAmount;
                shineAmount=tmp;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaStrike"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaLastOne"))){
                shineAmount+=UikaLastOne.MAGIC;
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaPoemInsteadOfSong"))){
                shineAmount+=uikaMonster.getDebuffNum();
            }else if(uikaMonster.cardForShow2.cardID.equals(makeID("UikaTwoMoon"))){
                damageNum+=monsterDamage;
                damageNum+=divergeWorldAmount*gravityAmount;
                gravityDamageNum+=divergeWorldAmount*gravityAmount;
            }
        }

        damageNum+=gravityAmount;
        gravityDamageNum+=gravityAmount;

        return new ArrayList<>(Arrays.asList(damageNum,gravityDamageNum));
    }
}
