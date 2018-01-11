package org.fundacionparaguaya.advisorapp.models;

import android.arch.lifecycle.MutableLiveData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A family is the entity that is being helped by the advisor. The family has snapshots of their situation
 * added when they take a survey. In the future, they will also have notes.
 */

public class Family
{
    public static int MAX_PRIORITIES = 5;

    private String mName;
    private String mUid;
    private URL mImageUrl;
    private MutableLiveData<Snapshot> mLatestSnapshot; //should be observable with live data?

    private String mPhoneNumber;

    private List<Indicator> mPriorities;
    private List<Snapshot> mSnapshots;

    public Family()
    {
        mPriorities = new ArrayList<Indicator>();
    }

    /**
     *
     * @param i Priority to add (Indicator)
     * @return  True if successfully added, False if family already has MAX_PRIORITIES
     */
    public Boolean addPriority(Indicator i)
    {
        if(getPrioritiesCount()<5)
        {
            mPriorities.add(i);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Removes an indicator from the family's priorities
     *
     * @return true if priority was successfully removed, false otherwise
     */
    public Boolean removePriority(Indicator i)
    {
        return mPriorities.remove(i);
    }

    public int getPrioritiesCount()
    {
        return mPriorities.size();
    }
}
