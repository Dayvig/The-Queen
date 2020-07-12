package QueenMod.characters;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class noAnimation extends AbstractAnimation {
    @Override
    public Type type() {
        return Type.NONE;
    }
}
