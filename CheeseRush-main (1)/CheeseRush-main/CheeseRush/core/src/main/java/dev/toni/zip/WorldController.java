package dev.toni.zip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

public class WorldController {
    public Player player;
    public Cat cat;
    public Texture bgDay, bgAfternoon, bgNight;
    public Texture currentBg;
    public Music theme;
    public boolean gameOver = false;
    public float score = 0f;
    public int difficulty = 0;
    private float catStartDelay = 0f;
    public Array<Obstacle> obstacles = new Array<>();
    private float spawnTimer = 0f;
    private Random rng = new Random();

    public WorldController(AssetManager assets) {
        player = new Player();
        cat = new Cat();
        player.bounds.setSize(1.1f, 1.1f);
        cat.bounds.setSize(1.2f, 1.2f);
        bgDay = new Texture("Cenario_Dia.jpg");
        bgAfternoon = new Texture("Cenario_Tarde.jpg");
        bgNight = new Texture("Cenario_Noite.jpg");
        currentBg = bgDay;
        theme = Gdx.audio.newMusic(Gdx.files.internal("theme.mp3"));
        theme.setLooping(true);
        theme.play();
        Array<TextureRegion> ratFrames = new Array<>();
        for (int i = 1; i <= 4; i++) ratFrames.add(new TextureRegion(new Texture("Rato" + i + ".png")));
        player.anim = new Animation<>(0.1f, ratFrames, Animation.PlayMode.LOOP);
        Array<TextureRegion> catFrames = new Array<>();
        for (int i = 1; i <= 6; i++) catFrames.add(new TextureRegion(new Texture("Gato" + i + ".png")));
        cat.anim = new Animation<>(0.1f, catFrames, Animation.PlayMode.LOOP);
        player.pos.set(8f, Constants.GROUND_Y);
        cat.pos.set(1f, Constants.GROUND_Y);
        player.bounds.setPosition(player.pos.x, player.pos.y);
        cat.bounds.setPosition(cat.pos.x, cat.pos.y);
        catStartDelay = 3f;
        spawnTimer = 1.5f;
    }

    public void update(float dt) {
        if (!gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) player.accelerate();
            player.update(dt);
            if (catStartDelay > 0f) catStartDelay -= dt; else cat.update(dt);
            updateObstacles(dt);
            player.bounds.setPosition(player.pos.x, Constants.GROUND_Y);
            cat.bounds.setPosition(cat.pos.x, Constants.GROUND_Y);
            score += Math.max(player.vel.x, 0) * dt * 10f;
            if (score < 200f) {
                difficulty = 0;
                currentBg = bgDay;
            } else if (score < 600f) {
                difficulty = 1;
                currentBg = bgAfternoon;
            } else {
                difficulty = 2;
                currentBg = bgNight;
            }
            if (checkCollisions()) gameOver = true;
        } else {
            if (Gdx.input.justTouched()) reset();
        }
    }

    private void updateObstacles(float dt) {
        float speedBase = 4f + difficulty * 1.2f;
        spawnTimer -= dt;
        float spawnInterval = Math.max(1.6f - difficulty * 0.4f, 0.7f);
        if (spawnTimer <= 0f) {
            spawnTimer = spawnInterval;
            int pick = 0;
            if (difficulty == 0) {
                pick = 0;
            } else if (difficulty == 1) {
                pick = rng.nextInt(2);
            } else {
                pick = rng.nextInt(3);
            }
            switch (pick) {
                case 0:
                    obstacles.add(new Obstacle(player.pos.x + 18f, Constants.GROUND_Y, 1f, 1f, -speedBase, "obst_lata.png"));
                    break;
                case 1:
                    obstacles.add(new Obstacle(player.pos.x + 18f, Constants.GROUND_Y, 1.2f, 1.2f, - (speedBase + 0.6f), "obst_extra1.png"));
                    break;
                case 2:
                    obstacles.add(new Obstacle(player.pos.x + 18f, Constants.GROUND_Y, 1.4f, 1.4f, - (speedBase + 1.0f), "obst_extra2.png"));
                    break;
            }
        }
        for (int i = obstacles.size - 1; i >= 0; i--) {
            Obstacle o = obstacles.get(i);
            o.update(dt);
            if (o.pos.x + o.width < player.pos.x - 20f) obstacles.removeIndex(i);
        }
    }

    private boolean checkCollisions() {
        for (Obstacle o : obstacles) {
            if (o.bounds.overlaps(player.bounds)) return true;
        }
        if (player.bounds.overlaps(cat.bounds)) return true;
        return false;
    }

    public void reset() {
        player.reset();
        cat.reset();
        obstacles.clear();
        player.pos.set(8f, Constants.GROUND_Y);
        cat.pos.set(1f, Constants.GROUND_Y);
        player.bounds.setPosition(player.pos.x, player.pos.y);
        cat.bounds.setPosition(cat.pos.x, cat.pos.y);
        catStartDelay = 3f;
        score = 0f;
        difficulty = 0;
        currentBg = bgDay;
        spawnTimer = 1.5f;
        gameOver = false;
        if (!theme.isPlaying()) theme.play();
    }

    public void dispose() {
        bgDay.dispose();
        bgAfternoon.dispose();
        bgNight.dispose();
        theme.stop();
        theme.dispose();
        for (Obstacle o : obstacles) o.dispose();
    }
}
