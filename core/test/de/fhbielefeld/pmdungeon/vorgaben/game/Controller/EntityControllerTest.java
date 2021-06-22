package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

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

    //-----------update--------------
    @DisplayName("update single entity")
    @Test
    void updateSingleEntity(){
        TestEntity entity = new TestEntity();
        entityController.addEntity(entity);

        assertEquals(entityController.getList().size(), 1);
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertFalse(listEntity.updated);

        entityController.update();

        assertEquals(entityController.getList().size(), 1);
        listEntity = (TestEntity) entityController.getList().get(0);
        assertTrue(listEntity.updated);
    }

    @DisplayName("update multiple entity")
    @Test
    void updateMultipleEntity(){
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        TestEntity entity3 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(entity3);

        assertEquals(entityController.getList().size(), 3);
        for (IEntity obj: entityController.getList()){
            assertFalse(((TestEntity) obj).updated);
        }

        entityController.update();

        assertEquals(entityController.getList().size(), 3);
        for (IEntity obj: entityController.getList()){
            assertTrue(((TestEntity) obj).updated);
        }
    }

    @DisplayName("update single deletable entity")
    @Test
    void updateSingleDeletableEntity(){
        TestEntity entity = new TestEntity(true);
        entityController.addEntity(entity);

        assertEquals(entityController.getList().size(), 1);
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertFalse(listEntity.updated);

        entityController.update();

        assertFalse(entityController.getList().contains(entity));
    }

    @DisplayName("update multiple unique deletable entity")
    @Test
    void updateMultipleUniqueDeletableEntity(){
        TestEntity entity1 = new TestEntity(true);
        TestEntity entity2 = new TestEntity(true);
        TestEntity entity3 = new TestEntity(true);
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(entity3);

        assertEquals(entityController.getList().size(), 3);
        for (IEntity obj: entityController.getList()){
            assertFalse(((TestEntity) obj).updated);
        }

        entityController.update();

        assertEquals(entityController.getList().size(), 0);
        for (IEntity obj: entityController.getList()){
            assertFalse(((TestEntity) obj).updated);
        }
    }

    @DisplayName("update entity and make deletable")
    @Test
    void updateEntityMakeDeletable(){
        TestEntity entity = new TestEntity(false);
        entityController.addEntity(entity);

        assertFalse(((TestEntity) entityController.getList().get(0)).updated);
        entityController.update();
        assertTrue(((TestEntity) entityController.getList().get(0)).updated);

        // "Reset" updated, make deletable
        ((TestEntity) entityController.getList().get(0)).updated = false;
        ((TestEntity) entityController.getList().get(0)).makeDeletable();

        assertFalse(((TestEntity) entityController.getList().get(0)).updated);
        entityController.update();
        assertTrue(entityController.getList().isEmpty());
    }

    @DisplayName("update mixes entities")
    @Test
    void updateMixedEntities(){
        TestEntity entity1 = new TestEntity(true);
        TestEntity entity2 = new TestEntity(true);
        TestEntity entity3 = new TestEntity(false);
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(entity3);

        assertEquals(entityController.getList().size(), 3);
        for (IEntity obj: entityController.getList()){
            assertFalse(((TestEntity) obj).updated);
        }

        entityController.update();

        assertEquals(entityController.getList().size(), 1);
        assertTrue(((TestEntity) entityController.getList().get(0)).updated);
        assertFalse(entity1.updated);
        assertFalse(entity2.updated);
    }

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
    void removeAllEmptyList() {
        assertTrue(entityController.getList().isEmpty());

        entityController.removeAll();
        assertTrue(entityController.getList().isEmpty());
    }

    //-----------removeAllFrom-------------
    @DisplayName("remove all from empty list")
    @Test
    void removeAllFromEmptyList() {
        assertTrue(entityController.getList().isEmpty());

        entityController.removeAllFrom(TestEntity.class);
        assertTrue(entityController.getList().isEmpty());
    }

    @DisplayName("remove all from class")
    @Test
    void removeAllFromDeleteOne() {
        TestEntity entity1 = new TestEntity();
        IEntity entity2 = mock(IEntity.class);

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        entityController.removeAllFrom(TestEntity.class);
        assertEquals(entityController.getList().size(), 1);
        assertTrue(entityController.getList().contains(entity2));
    }

    @DisplayName("remove all from class with multiple same entities")
    @Test
    void removeAllFromMultipleSame() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        IEntity other = mock(IEntity.class);

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(other);
        assertEquals(entityController.getList().size(), 3);

        entityController.removeAllFrom(TestEntity.class);
        assertEquals(entityController.getList().size(), 1);
        assertTrue(entityController.getList().contains(other));
    }

    @DisplayName("remove all from another class from ")
    @Test
    void removeAllFromAnotherClass() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        IEntity other = mock(IEntity.class);

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        entityController.removeAllFrom(other.getClass());
        assertEquals(entityController.getList().size(), 2);
        assertTrue(entityController.getList().contains(entity1));
        assertTrue(entityController.getList().contains(entity2));
    }

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

        public void makeConsistent() {
            this.deletable = false;
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
