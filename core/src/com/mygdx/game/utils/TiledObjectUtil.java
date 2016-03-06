package com.mygdx.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainScreen;

public class TiledObjectUtil {
    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }
    private static ChainShape createPolyline(PolylineMapObject polyline){
        float[] verticles = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticles = new Vector2[verticles.length/2];

        for (int i = 0; i < worldVerticles.length; i++){
            worldVerticles[i] = new Vector2(verticles[2*i] /Constants.PPM, verticles[2*i+1]/Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticles);
        return cs;

    }
}
