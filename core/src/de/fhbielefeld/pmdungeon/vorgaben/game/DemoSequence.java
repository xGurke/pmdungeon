package de.fhbielefeld.pmdungeon.vorgaben.game;

import de.fhbielefeld.pmdungeon.vorgaben.dungeon.dungeonconverter.DungeonConverter;

/**
 * Demo sequence to offer multiple levels. Used instead of a procedural level generator.
 */
public class DemoSequence {

    private final GameController gameController;
    private final DungeonConverter dungeonConverter = new DungeonConverter();
    private Stage stage = Stage.A;

    public DemoSequence(GameController gameController) {
        this.gameController = gameController;
        nextStage();
    }

    /**
     * Polling if next stage got triggered.
     */
    public void update() {
        if (gameController.isNextLevelTriggered()) nextStage();
    }

    /**
     * If next stage is triggered, change the dungeon.
     */
    private void nextStage() {
        switch (stage) {
            case A:
                gameController.setupDungeon(dungeonConverter.dungeonFromJson("small_dungeon.json"));
                stage = Stage.B;
                break;
            case B:
                gameController.setupDungeon(dungeonConverter.dungeonFromJson("simple_dungeon.json"));
                stage = Stage.A;
                break;
        }

    }

    enum Stage {
        A,
        B,
        C,
        D,
    }
}
