package com.example.oscarruiz.misseries.interfaces;

import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.Season;

import java.util.ArrayList;

/**
 * Created by Oscar Ruiz on 30/06/2017.
 */

public class AppInterfaces {

    /**
     * Interface to delete serie
     */
    public interface IPressDeleteSerie {
        public abstract void isDeleting(int position);
    }

    /**
     * Interface to search serie
     */
    public interface ISearchSerie {
        public abstract void getSerie(ResponseSerie responseSerie);
    }

    /**
     * Interface to search detail serie
     */
    public interface ISearchDetailSerie {
        public abstract void getDetailSerie(ResponseSerie responseSerie);
    }

    /**
     * Interface to search external ids
     */
    public interface ISearchExternalId {
        public abstract void getExternalId(ResponseSerie responseSerie);
    }

    /**
     * Interface to search series matches
     */
    public interface ISearchMatches {
        public abstract void getMatches(ArrayList<ResponseSerie> matchesList);
    }

    /**
     * Interface to search serie season
     */
    public interface ISearchSeason {
        public abstract void getSeason(Season season);
    }

}
