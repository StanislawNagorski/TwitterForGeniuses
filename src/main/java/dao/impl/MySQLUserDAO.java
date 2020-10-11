package dao.impl;

import dao.AbstractMySQLDAO;
import dao.AppUserDAO;
import hibernate.HibernateUtil;
import model.AppUser;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class MySQLUserDAO extends AbstractMySQLDAO implements AppUserDAO {


    @Override
    public HashSet<AppUser> getAll() {
        TypedQuery<AppUser> getAll = em.createQuery("from AppUser u where u.isActive=true ", AppUser.class);
        List<AppUser> resultList = getAll.getResultList();

        return new HashSet<>(resultList);
    }

    @Override
    public Optional<AppUser> getUserById(Long id) {
        TypedQuery<AppUser> query = em.createQuery("from AppUser u where u.id=:id", AppUser.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AppUser> getUserByEmail(String email) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.email=:email and u.isActive = true ", AppUser.class);
        query.setParameter("email", email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AppUser> getUserByLogin(String login) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.login=:login", AppUser.class);
        query.setParameter("login", login);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser) {
        return new HashSet<>(loggedUser.getFollowing());
    }

    @Override
    public HashSet<AppUser> getNotFollowed(AppUser loggedUser) {
        Query query = em.createQuery("from AppUser u where u.login != :login");
        query.setParameter("login", loggedUser.getLogin());
        HashSet<AppUser> appUsers = new HashSet<AppUser>(query.getResultList());
        appUsers.remove(loggedUser.getFollowing());
        return appUsers;

        //TODO do przepsania w hibarnate tak aby nie pobierać całej listy
    }

    @Override
    public HashSet<AppUser> getFollowers(AppUser loggedUser) {
        Query query = em.createQuery("select followers from AppUser u where u.id = :userID");
        query.setParameter("userID", loggedUser.getId());
        ArrayList<AppUser> resultList = new ArrayList<>(query.getResultList());

        return resultList.stream().filter(AppUser::isActive).collect(Collectors.toCollection(HashSet::new));
    }


    @Override
    public void follow(AppUser loggedUser, AppUser userToFollow) {
        loggedUser.getFollowing().add(userToFollow);
        saveUser(loggedUser);
    }

    @Override
    public void unfollow(AppUser loggedUser, AppUser userToStopFollowing) {
        loggedUser.getFollowing().remove(userToStopFollowing);
        saveUser(loggedUser);
    }

    @Override
    public void saveUser(AppUser user) {
        hibernateUtil.save(user);
    }

    @Override
    public void deleteUser(AppUser user) {
        user.setActive(false);
    }

    private void unfollowBeforeDelete(AppUser user) {
        getFollowers(user).forEach(following -> unfollow(following, user));
    }

}
