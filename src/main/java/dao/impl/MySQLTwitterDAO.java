package dao.impl;

import dao.AbstractMySQLDAO;
import dao.TweetDAO;
import model.AppUser;
import model.Tweet;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class MySQLTwitterDAO extends AbstractMySQLDAO implements TweetDAO {

    @Override
    public void saveTweet(Tweet tweet) {
        hibernateUtil.save(tweet);
    }

    @Override
    public void deleteTweet(Long id) {
        hibernateUtil.delete(Tweet.class, id);
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        TypedQuery<Tweet> query = em.createQuery("from Tweet t where t.author.id=:user_id", Tweet.class);
        query.setParameter("user_id", user.getId());

        return query.getResultList();
    }

    @Override
    public Optional<Tweet> getTweet(Long id) {
        return Optional.ofNullable(em.find(Tweet.class, id));
    }
}
