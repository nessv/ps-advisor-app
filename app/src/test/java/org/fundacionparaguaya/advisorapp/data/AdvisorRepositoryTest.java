package org.fundacionparaguaya.advisorapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.fundacionparaguaya.advisorapp.models.Family;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the advisor repository.
 */

@RunWith(MockitoJUnitRunner.class)
public class AdvisorRepositoryTest {
    @Mock
    FamilyDao familyDao;

    @InjectMocks
    AdvisorRepository repo;

    @Captor
    ArgumentCaptor<Family> familyCaptor;

    @Test
    public void ShouldBeAbleToCreateFamily() {
        Family family = new Family(1, "Smith");

        when(familyDao.updateFamily(family)).thenReturn(0);
        repo.saveFamily(family);

        verify(familyDao, atLeastOnce()).insertFamily(family);
    }

    @Test
    public void ShouldBeAbleToChangeFamily() {
        Family family = new Family(1, "Smith");

        when(familyDao.updateFamily(family)).thenReturn(1);
        repo.saveFamily(family);

        verify(familyDao, atLeastOnce()).updateFamily(family);
        verify(familyDao, never()).insertFamily(any(Family.class));
    }

    @Test
    public void ShouldBeAbleToDeleteFamily() {
        Family family = new Family(1, "Smith");

        repo.deleteFamily(family);

        verify(familyDao, atLeastOnce()).deleteFamily(familyCaptor.capture());
    }

    @Test
    public void ShouldBeAbleToGetFamily() {
        LiveData<Family> data = new MutableLiveData<>();
        when(familyDao.queryFamily(1)).thenReturn(data);

        assertEquals(data, repo.getFamily(1));

        verify(familyDao, atLeastOnce()).queryFamily(1);
    }

    @Test
    public void ShouldBeAbleToGetFamilies() {
        LiveData<List<Family>> data = new MutableLiveData<>();
        when(familyDao.queryFamilies()).thenReturn(data);

        assertEquals(data, repo.getFamilies());

        verify(familyDao, atLeastOnce()).queryFamilies();
    }

}
