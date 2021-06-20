package de.fhbielefeld.pmdungeon.vorgabe.game.Controller;

import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TestCase: EntityController")
public class EntityControllerTest {

    private EntityController entityController;

    @BeforeEach
    void setUp() {
        entityController = new EntityController();
    }

    //-----------Constructor--------------
    @DisplayName("list initialised")
    @Test
    void initialise() {
        assertNotNull(entityController);
    }

    @DisplayName("initialised List empty")
    @Test
    void initialListEmpty() {
        assertTrue(entityController.getList().isEmpty());
    }

    //TODO:-----------update--------------
    /*
      delEntity: removed, noCall
      MultDelEntity: removed, not called
      Entity: exist, called
      MultEntity: exist, called
      MultEntity, MultDelEntity: entity exist/called; del removed, not called
      Entity: exist, called --> makeDel: removed, not called
     */

    //-----------addEntity-----------
    @DisplayName("add single entity")
    @Test
    void addSingleEntity() {
        TestEntity entity = new TestEntity();

        assertTrue(entityController.getList().isEmpty());
        entityController.addEntity(entity);
        assertEquals(entityController.getList().size(), 1);
        assertTrue(entityController.getList().contains(entity));
    }

    @DisplayName("add multiple entities")
    @Test
    void addMultipleEntities() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        TestEntity entity3 = new TestEntity();

        assertTrue(entityController.getList().isEmpty());
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(entity3);
        assertEquals(entityController.getList().size(), 3);
        assertTrue(entityController.getList().contains(entity1));
        assertTrue(entityController.getList().contains(entity2));
        assertTrue(entityController.getList().contains(entity3));
    }

    @DisplayName("add multiple same entities")
    @Test
    void addMultipleSameEntities() {
        TestEntity singleEntity = new TestEntity();
        TestEntity multipleEntity = new TestEntity();

        assertTrue(entityController.getList().isEmpty());
        entityController.addEntity(singleEntity);
        entityController.addEntity(multipleEntity);
        entityController.addEntity(multipleEntity);
        // doppelte Entities sollen nur einmal eingefügt werden: daher 2 nicht 3
        assertEquals(entityController.getList().size(), 2);
        assertTrue(entityController.getList().contains(singleEntity));
        assertTrue(entityController.getList().contains(multipleEntity));

    }

    //-----------removeEntity--------------
    @DisplayName("remove single entity")
    @Test
    void removeSingleEntity() {
        TestEntity entity = new TestEntity();
        entityController.addEntity(entity);
        assertEquals(entityController.getList().size(), 1);

        entityController.removeEntity(entity);
        assertEquals(entityController.getList().size(), 0);
    }

    @DisplayName("remove multiple unique entities")
    @Test
    void removeMultipleUniqueEntity() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        entityController.removeEntity(entity1);
        assertFalse(entityController.getList().contains(entity1));
        assertTrue(entityController.getList().contains(entity2));
        entityController.removeEntity(entity2);
        assertFalse(entityController.getList().contains(entity2));
        assertTrue(entityController.getList().isEmpty());
    }

    @DisplayName("remove not in list entity")
    @Test
    void removeNotInListEntity() {
        TestEntity entity1 = new TestEntity();
        TestEntity removeEntity = new TestEntity();
        entityController.addEntity(entity1);
        assertTrue(entityController.getList().contains(entity1));

        // stays the same
        entityController.removeEntity(removeEntity);
        assertTrue(entityController.getList().contains(entity1));
    }

    //-----------removeAll-----------------
    @DisplayName("remove all entities")
    @Test
    void removeAllEntities() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        entityController.removeAll();
        assertTrue(entityController.getList().isEmpty());
    }

    @DisplayName("remove all from empty list")
    @Test
    void removeAllFromEmptyList() {
        assertTrue(entityController.getList().isEmpty());

        entityController.removeAll();
        assertTrue(entityController.getList().isEmpty());
    }

    //TODO:-----------removeAllFrom-------------
    /*
     *  remove new IEntity from emptyList
     *  remove all entites of class(there are other entities)
     *  remove all entities of class (there are multiple)
     *  remove all entites of class, there is not one in the list
     *
     */

    //-----------getList------------------
    @DisplayName("get empty list")
    @Test
    void getEmptyList() {
        assertTrue(entityController.getList().isEmpty());
    }

    @DisplayName("get list")
    @Test
    void getList() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        assertTrue(entityController.getList().contains(entity1));
        assertTrue(entityController.getList().contains(entity2));
    }

    /**
     * An Entity for testing
     */
    static class TestEntity implements IEntity {

        private boolean deletable;
        public boolean updated;

        public TestEntity(boolean deletable) {
            this.deletable=deletable;
            this.updated = false;
        }

        public TestEntity() {
            new TestEntity(false);
        }

        public void makeDeletable() {
            this.deletable = true;
        }

        /**
         * Will be executed every frame. Remember to draw/animate your drawable objects.
         */
        @Override
        public void update() {
            this.updated = true;
        }

        /**
         * @return if this instance can be deleted (than will be removed from DungeonEntityController list);
         */
        @Override
        public boolean deleteable() {
            return this.deletable;
        }
    }

}
