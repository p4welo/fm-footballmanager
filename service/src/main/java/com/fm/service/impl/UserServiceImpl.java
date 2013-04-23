package com.fm.service.impl;

import com.fm.dao.IAbstractDao;
import com.fm.dao.IUserDao;
import com.fm.domain.User;
import com.fm.service.IUserService;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: pawel
 * Date: 17.12.12
 * Time: 19:30
 */
@Service(UserServiceImpl.BEAN_NAME)
public class UserServiceImpl extends AbstractServiceImpl<User> implements IUserService
{
   public static final String BEAN_NAME = "userService";

   @Resource
   private PasswordEncoder passwordEncoder;

   @Resource
   private IUserDao userDao;

   @Override
   protected IAbstractDao<User> getDao()
   {
      return (IAbstractDao<User>) userDao;
   }

   @Override
   @Transactional
   public User save(User user)
   {
      user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getLogin()));
      return super.save(user);
   }

   @Override
   @Transactional
   public User changePassword(User user, String password)
   {
      user.setPassword(passwordEncoder.encodePassword(password, user.getLogin()));
      return update(user);
   }

   @Override
   @Transactional
   public User getByLogin(String login)
   {
      return userDao.findByLogin(login);
   }
}
