package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.screen.gamescreen.Enemy;
import ru.geekbrains.stargame.screen.pool.EnemyPool;

public class EnemyEmitter {

    private static final int ENEMY_SMALL = 0;
    private static final int ENEMY_MIDDLE = 1;
    private static final int ENEMY_LARGE = 2;

    private TextureRegion[] enemySmallRegion;
    private Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_SMALL_HP = 1;

    private TextureRegion[] enemyMiddleRegion;
    private Vector2 enemyMiddleV = new Vector2(0f, -0.1f);
    private static final float ENEMY_MIDDLE_HEIGHT = 0.2f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.2f;
    private static final int ENEMY_MIDDLE_BULLET_DAMAGE = 1;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MIDDLE_HP = 1;

    private TextureRegion[] enemyLargeRegion;
    private Vector2 enemyLargeV = new Vector2(0f, -0.05f);
    private static final float ENEMY_LARGE_HEIGHT = 0.3f;
    private static final float ENEMY_LARGE_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_LARGE_BULLET_VY = -0.1f;
    private static final int ENEMY_LARGE_BULLET_DAMAGE = 1;
    private static final float ENEMY_LARGE_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_LARGE_HP = 1;

    private Rect worldBounds;

    private float generateInterval = 3f;
    private float generateTimer;

    private TextureRegion bulletRegion;

    private EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;

        this.enemySmallRegion = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        this.enemyMiddleRegion = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        this.enemyLargeRegion = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generateEnemies(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            switch (Rnd.random.nextInt(ENEMY_LARGE + 1)) {
                case ENEMY_SMALL:
                    enemy.set(
                            enemySmallRegion,
                            enemySmallV,
                            bulletRegion,
                            ENEMY_SMALL_BULLET_HEIGHT,
                            ENEMY_SMALL_BULLET_VY,
                            ENEMY_SMALL_BULLET_DAMAGE,
                            ENEMY_SMALL_RELOAD_INTERVAL,
                            ENEMY_SMALL_HEIGHT,
                            ENEMY_SMALL_HP);
                    break;
                case ENEMY_MIDDLE:
                    enemy.set(
                            enemyMiddleRegion,
                            enemyMiddleV,
                            bulletRegion,
                            ENEMY_MIDDLE_BULLET_HEIGHT,
                            ENEMY_MIDDLE_BULLET_VY,
                            ENEMY_MIDDLE_BULLET_DAMAGE,
                            ENEMY_MIDDLE_RELOAD_INTERVAL,
                            ENEMY_MIDDLE_HEIGHT,
                            ENEMY_MIDDLE_HP);
                    break;
                case ENEMY_LARGE:
                    enemy.set(
                            enemyLargeRegion,
                            enemyLargeV,
                            bulletRegion,
                            ENEMY_LARGE_BULLET_HEIGHT,
                            ENEMY_LARGE_BULLET_VY,
                            ENEMY_LARGE_BULLET_DAMAGE,
                            ENEMY_LARGE_RELOAD_INTERVAL,
                            ENEMY_LARGE_HEIGHT,
                            ENEMY_LARGE_HP);
                    break;
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
