package com.sat.tmf.movietkt.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.sat.tmf.movietkt.entities.Movie;
import com.sat.tmf.movietkt.entities.Screen;
import com.sat.tmf.movietkt.entities.Show;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ShowDao extends GenericDao<Show, Integer> {

    public ShowDao() {
        super(Show.class);
    }

    /**
     * Find all shows for a specific screen.
     */
    public List<Show> findByScreen(Screen screen) {
        Session session = getSession();
        Query<Show> query = session.createQuery(
                "from Show where screen.id = :sid order by showTime", Show.class);
        query.setParameter("sid", screen.getId());
        return query.list();
    }

    /**
     * Find upcoming shows for a movie.
     */
    public List<Show> findUpcomingShows(Movie movie) {
        Session session = getSession();
        Query<Show> query = session.createQuery(
                "from Show where movie.id = :mid and showTime >= :now order by showTime", Show.class);
        query.setParameter("mid", movie.getId());
        query.setParameter("now", LocalDateTime.now());
        return query.list();
    }

    /**
     * Delete all past shows.
     */
    public int deletePastShows() {
        Session session = getSession();
        return session.createQuery("delete from Show where showTime < :now")
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
    }
}

