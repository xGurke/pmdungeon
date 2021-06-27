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
    @DisplayName("initialised List empty")
    @Test
    void testInitialListEmpty() {
        assertTrue(entityController.getList().isEmpty(), "initialised list must be empty");
    }

    //-----------update--------------
    @DisplayName("update single entity")
    @Test
    void testUpdateSingleEntity(){
        TestEntity entity = new TestEntity();
        entityController.addEntity(entity);

        assertTrue(entityController.getList().contains(entity));
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertFalse(listEntity.updated);

        entityController.update();

        assertTrue(entityController.getList().contains(entity), "entity must be in list");
        listEntity = (TestEntity) entityController.getList().get(0);
        assertTrue(listEntity.updated, "entity must be updated");
    }

    @DisplayName("update multiple entity")
    @Test
    void testUpdateMultipleEntity(){
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

        assertEquals(entityController.getList().size(), 3, "list must have same size as before update()");
        for (IEntity obj: entityController.getList()){
            assertTrue(((TestEntity) obj).updated, "entity must be updated");
        }
    }

    @DisplayName("update single deletable entity")
    @Test
    void testUpdateSingleDeletableEntity(){
        TestEntity entity = new TestEntity(true);
        entityController.addEntity(entity);

        assertEquals(entityController.getList().size(), 1);
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertFalse(listEntity.updated);

        entityController.update();

        assertFalse(entityController.getList().contains(entity), "entity should not be in list");
        assertEquals(entity.timesUpdated, 0, "entity should not be updated");
    }

    @DisplayName("update multiple unique deletable entity")
    @Test
    void testUpdateMultipleDeletableEntity(){
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

        assertEquals(entityController.getList().size(), 0, "list must be empty");
        for (IEntity obj: entityController.getList()){
            assertFalse(((TestEntity) obj).updated, "entity should not be updated");
        }
    }

    @DisplayName("update entity and make deletable")
    @Test
    void testUpdateEntityMakeDeletable(){
        TestEntity entity = new TestEntity(false);
        entityController.addEntity(entity);

        assertFalse(((TestEntity) entityController.getList().get(0)).updated);
        entityController.update();
        assertTrue(((TestEntity) entityController.getList().get(0)).updated, "entity must be updated");

        // "Reset" updated for checking, make deletable
        ((TestEntity) entityController.getList().get(0)).updated = false;
        ((TestEntity) entityController.getList().get(0)).makeDeletable();

        assertFalse(((TestEntity) entityController.getList().get(0)).updated, "entity must not be updated");
        entityController.update();
        assertTrue(entityController.getList().isEmpty(),"entity should not be in list");
    }

    @DisplayName("update mixes entities")
    @Test
    void testUpdateMixedEntities(){
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

        assertEquals(entityController.getList().size(), 1, "only non deletable entities must be in list, which is 1");
        assertTrue(((TestEntity) entityController.getList().get(0)).updated, "non deletable entity must be updated");
        assertFalse(entity1.updated, "deletable entity should not be updated");
        assertFalse(entity2.updated, "deletable entity should not be updated");
    }

    @DisplayName("update single entity multiple times")
    @Test
    void testUpdateSingleEntityRepeated(){
        TestEntity entity = new TestEntity();
        entityController.addEntity(entity);

        assertTrue(entityController.getList().contains(entity));
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertEquals(listEntity.timesUpdated, 0);

        entityController.update();
        entityController.update();
        entityController.update();

        assertTrue(entityController.getList().contains(entity), "entity must be in list");
        listEntity = (TestEntity) entityController.getList().get(0);
        assertEquals(listEntity.timesUpdated, 3, "entity must be have called update 3 times");
    }

    @DisplayName("update single deletable entity multiple times")
    @Test
    void testUpdateSingleDeletableEntityRepeated(){
        TestEntity entity = new TestEntity(true);
        entityController.addEntity(entity);

        assertTrue(entityController.getList().contains(entity));
        TestEntity listEntity = (TestEntity) entityController.getList().get(0);
        assertEquals(listEntity.timesUpdated, 0);

        entityController.update();
        entityController.update();
        entityController.update();

        assertTrue(entityController.getList().isEmpty(), "entity should not be in list");
        assertEquals(entity.timesUpdated, 0, "entity should not be updated");
    }

    //-----------addEntity-----------
    @DisplayName("add single entity")
    @Test
    void testAddSingleEntity() {
        TestEntity entity = new TestEntity();

        assertTrue(entityController.getList().isEmpty());

        entityController.addEntity(entity);

        assertEquals(entityController.getList().size(), 1, "list size should increase by 1: 0->1");
        assertTrue(entityController.getList().contains(entity), "entity must be in list");
    }

    @DisplayName("add multiple entities")
    @Test
    void testAddMultipleUniqueEntities() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        TestEntity entity3 = new TestEntity();

        assertTrue(entityController.getList().isEmpty());

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(entity3);

        assertEquals(entityController.getList().size(), 3, "list size should increase by 3: 0->3");
        assertTrue(entityController.getList().contains(entity1), "entity must be in list");
        assertTrue(entityController.getList().contains(entity2), "entity must be in list");
        assertTrue(entityController.getList().contains(entity3), "entity must be in list");
    }

    @DisplayName("add multiple same entities")
    @Test
    void testAddMultipleSameEntities() {
        TestEntity singleEntity = new TestEntity();
        TestEntity multipleEntity = new TestEntity();

        assertTrue(entityController.getList().isEmpty());

        entityController.addEntity(singleEntity);
        entityController.addEntity(multipleEntity);
        entityController.addEntity(multipleEntity);

        // doppelte Entities sollen nur einmal eingefügt werden: daher 2 nicht 3
        assertEquals(entityController.getList().size(), 2, "list size must increase by amount of unique entities added: 0->2 ");
        assertTrue(entityController.getList().contains(singleEntity), "unique entity must be in list");
        assertTrue(entityController.getList().contains(multipleEntity), "unique entity must be in list");
    }

    //-----------removeEntity--------------
    @DisplayName("remove single entity")
    @Test
    void testRemoveSingleEntity() {
        TestEntity entity = new TestEntity();
        entityController.addEntity(entity);

        assertEquals(entityController.getList().size(), 1);

        entityController.removeEntity(entity);

        assertEquals(entityController.getList().size(), 0, "entity should not be in list");
    }

    @DisplayName("remove multiple unique entities")
    @Test
    void testRemoveMultipleUniqueEntity() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);

        assertEquals(entityController.getList().size(), 2);

        entityController.removeEntity(entity1);

        assertFalse(entityController.getList().contains(entity1), "list should not include deleted entity");
        assertTrue(entityController.getList().contains(entity2), "entity must be in list");
    }

    @DisplayName("remove not in list entity")
    @Test
    void testRemoveNotInListEntity() {
        TestEntity entity1 = new TestEntity();
        TestEntity removeEntity = new TestEntity();
        entityController.addEntity(entity1);

        assertTrue(entityController.getList().contains(entity1));

        entityController.removeEntity(removeEntity);

        // stays the same
        assertTrue(entityController.getList().contains(entity1), "entity must be in list");
    }

    //-----------removeAll-----------------
    @DisplayName("remove all entities")
    @Test
    void testRemoveAllEntities() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);

        assertEquals(entityController.getList().size(), 2);

        entityController.removeAll();

        assertTrue(entityController.getList().isEmpty(), "list must be empty");
    }

    @DisplayName("remove all from empty list")
    @Test
    void testRemoveAllEmptyList() {
        assertTrue(entityController.getList().isEmpty());

        entityController.removeAll();

        assertTrue(entityController.getList().isEmpty(), "list must be empty");
    }

    //-----------removeAllFrom-------------
    @DisplayName("remove all from empty list")
    @Test
    void testRemoveAllFromEmptyList() {
        assertTrue(entityController.getList().isEmpty());

        entityController.removeAllFrom(TestEntity.class);

        assertTrue(entityController.getList().isEmpty(), "list must be empty");
    }

    @DisplayName("remove all from class")
    @Test
    void testRemoveAllFromDeleteOne() {
        TestEntity entity1 = new TestEntity();
        IEntity entity2 = mock(IEntity.class);
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);

        assertEquals(entityController.getList().size(), 2);

        entityController.removeAllFrom(entity1.getClass());

        assertEquals(entityController.getList().size(), 1, "list size mus decrease by the amount of objects of the deleted class: 2->1");
        assertFalse(entityController.getList().contains(entity1), "TestEntity classes should not be in list");
        assertTrue(entityController.getList().contains(entity2), "non TestEntity classes should be in list");
    }

    @DisplayName("remove all from class with multiple same entities")
    @Test
    void testRemoveAllFromMultipleSame() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        IEntity other = mock(IEntity.class);

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        entityController.addEntity(other);
        assertEquals(entityController.getList().size(), 3);

        entityController.removeAllFrom(entity1.getClass());

        assertEquals(entityController.getList().size(), 1, "list size mus decrease by the amount of objects of the deleted class: 2->1");
        assertFalse(entityController.getList().contains(entity1), "TestEntity classes should not be in list");
        assertTrue(entityController.getList().contains(entity2), "TestEntity classes should not be in list");
        assertTrue(entityController.getList().contains(other), "non TestEntity classes should be in list");
    }

    @DisplayName("remove all from another class from ")
    @Test
    void testRemoveAllFromAnotherClass() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        IEntity other = mock(IEntity.class);

        entityController.addEntity(entity1);
        entityController.addEntity(entity2);
        assertEquals(entityController.getList().size(), 2);

        entityController.removeAllFrom(other.getClass());

        assertEquals(entityController.getList().size(), 2, "list size must stay the same");
        assertTrue(entityController.getList().contains(entity1), "non Mock classes should be in list");
        assertTrue(entityController.getList().contains(entity2), "non Mock classes should be in list");
    }

    //-----------getList------------------
    @DisplayName("get empty list")
    @Test
    void testGetEmptyList() {
        assertTrue(entityController.getList().isEmpty(), "an empty list must be returned");
    }

    @DisplayName("get list")
    @Test
    void testGetList() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();
        entityController.addEntity(entity1);
        entityController.addEntity(entity2);

        assertEquals(entityController.getList().size(), 2, "list size should be 2");
        assertTrue(entityController.getList().contains(entity1), "list must contain entity");
        assertTrue(entityController.getList().contains(entity2), "list must contain entity");
    }

    /**
     * An Implementation of IEntity for testing purposes
     */
    static class TestEntity implements IEntity {

        private boolean deletable;
        public boolean updated;
        public int timesUpdated;

        public TestEntity(boolean deletable) {
            this.deletable=deletable;
            this.updated = false;
            this.timesUpdated = 0;
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
            this.timesUpdated += 1;
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
