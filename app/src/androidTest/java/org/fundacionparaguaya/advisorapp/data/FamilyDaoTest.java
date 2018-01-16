package org.fundacionparaguaya.advisorapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.runner.AndroidJUnit4;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.local.LocalDatabase;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.FamilyMember;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static org.fundacionparaguaya.advisorapp.data.LiveDataTestUtil.waitForValue;

/**
 * The tests for the family data access object.
 */
@RunWith(AndroidJUnit4.class)
public class FamilyDaoTest {
    private LocalDatabase db;
    private FamilyDao familyDao;


    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(getTargetContext(), LocalDatabase.class).build();
        familyDao = db.familyDao();
    }

    @After
    public void close() {
        db.close();
    }

    @Test
    public void ShouldBeAbleToInsertAFamily() {
        FamilyMember member = new FamilyMember("Joe", "Smith", "");
        Family family = new Family(1, "Smith", member);

        familyDao.insertFamily(family);

        LiveData<List<Family>> result = familyDao.queryFamilies();
        List<Family> value = waitForValue(result);
        assertEquals(1, value.size());
        assertEquals(family, value.get(0));
    }

    @Test
    public void ShouldBeAbleToInsertFamilies() {
        FamilyMember member1 = new FamilyMember("Joe", "Smith", "");
        Family family1 = new Family(1, "Smith", member1);
        FamilyMember member2 = new FamilyMember("Bob", "Gogan", "");
        Family family2 = new Family(2, "Gogan", member2);

        familyDao.insertFamily(family1);
        familyDao.insertFamily(family2);

        LiveData<List<Family>> result = familyDao.queryFamilies();
        List<Family> value = waitForValue(result);
        assertEquals(2, value.size());
    }

    @Test
    public void ShouldBeAbleToQueryFamilyById() {
        FamilyMember member1 = new FamilyMember("Joe", "Smith", "");
        Family family1 = new Family(1, "Smith", member1);
        FamilyMember member2 = new FamilyMember("Bob", "Gogan", "");
        Family family2 = new Family(2, "Gogan", member2);
        familyDao.insertFamily(family1);
        familyDao.insertFamily(family2);

        LiveData<Family> result = familyDao.queryFamily(2);
        Family value = waitForValue(result);
        assertEquals(value, family2);
    }

    @Test
    public void ShouldBeAbleToUpdateAFamily() {
        FamilyMember member = new FamilyMember("Joe", "Smith", "");
        Family family = new Family(1, "Smith", member);
        familyDao.insertFamily(family);

        family.setName("Smithy");
        familyDao.updateFamily(family);

        LiveData<List<Family>> result = familyDao.queryFamilies();
        List<Family> value = waitForValue(result);
        assertEquals(1, value.size());
        assertEquals(family, value.get(0));
    }


    @Test
    public void ShouldBeAbleToDeleteAFamily() {
        FamilyMember member = new FamilyMember("Joe", "Smith", "");
        Family family = new Family(1, "Smith", member);
        familyDao.insertFamily(family);

        familyDao.deleteFamily(family);

        LiveData<List<Family>> result = familyDao.queryFamilies();
        List<Family> value = waitForValue(result);
        assertEquals(0, value.size());
    }
}
