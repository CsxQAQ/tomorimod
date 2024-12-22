package tomorinmod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import tomorinmod.screens.Renderable;

import java.util.ArrayList;

import static tomorinmod.BasicMod.imagePath;

public class MaterialUi implements Renderable {

    private static final Texture TextureStoneCommon = new Texture(imagePath("materials/card/stone_common.png"));
    private static final Texture TextureBandCommon = new Texture(imagePath("materials/card/band_common.png"));
    private static final Texture TextureFlowerCommon = new Texture(imagePath("materials/card/flower_common.png"));
    private static final Texture TextureStoneUncommon = new Texture(imagePath("materials/card/stone_uncommon.png"));
    private static final Texture TextureBandUncommon = new Texture(imagePath("materials/card/band_uncommon.png"));
    private static final Texture TextureFlowerUncommon = new Texture(imagePath("materials/card/flower_uncommon.png"));
    private static final Texture TextureStoneRare = new Texture(imagePath("materials/card/stone_rare.png"));
    private static final Texture TextureBandRare = new Texture(imagePath("materials/card/band_rare.png"));
    private static final Texture TextureFlowerRare = new Texture(imagePath("materials/card/flower_rare.png"));

    ArrayList<MaterialInfo> materials=new ArrayList<>();

    private static final float SIZE = 100.0F;
    private static final float X_OFFSET = FrameUi.X_OFFSET+(FrameUi.SIZE-SIZE)/2;
    private static final float Y_OFFSET = FrameUi.Y_OFFSET+(FrameUi.SIZE-SIZE)/2;
    private static final float INTERVAL = FrameUi.INTERVAL;

    private MaterialUi(){
    }

    private static MaterialUi instance;

    public static MaterialUi getInstance() {
        if (instance == null) {
            instance = new MaterialUi();
        }
        return instance;

    }

    public void setMaterial(String material,int level){
        materials.add(new MaterialInfo(material,level));
    }

    public Texture getMaterialTexture(MaterialInfo info){
        switch (info.getName()) {
            case "stone":
                if (info.getLevel() == 1) {
                    return TextureStoneCommon;
                } else if (info.getLevel() == 2) {
                    return TextureStoneUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureStoneRare;
                }
                break;

            case "band":
                if (info.getLevel() == 1) {
                    return TextureBandCommon;
                } else if (info.getLevel() == 2) {
                    return TextureBandUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureBandRare;
                }
                break;

            case "flower":
                if (info.getLevel() == 1) {
                    return TextureFlowerCommon;
                } else if (info.getLevel() == 2) {
                    return TextureFlowerUncommon;
                } else if (info.getLevel() == 3) {
                    return TextureFlowerRare;
                }
                break;
        }
        return null;
    }

    @Override
    public void render(SpriteBatch sb) {
        float yoffset=Y_OFFSET;
        for(int i=0;i<materials.size();i++){
            sb.draw(getMaterialTexture(materials.get(i)), X_OFFSET*Settings.scale,
                    yoffset*Settings.scale,SIZE*Settings.scale,SIZE*Settings.scale);
            yoffset=yoffset+INTERVAL;
        }
    }

    public void clear(){
        materials.clear();
    }

    public class MaterialInfo {
        private final String name;
        private final int level;

        public MaterialInfo(String name, int level) {
            this.name = name;
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }
}
