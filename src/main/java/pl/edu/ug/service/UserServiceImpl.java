package pl.edu.ug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.ug.dao.RoleDao;
import pl.edu.ug.dao.UserDao;
import pl.edu.ug.model.Role;
import pl.edu.ug.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void save(User user) {
        if (findByUsername(user.getUsername()) == null) {
            if(user.getRoleId() == 1){
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                Set<Role> roles = new HashSet<>();
                roles.add(roleDao.getOne(1L));
                user.setRoles(roles);
            }
            if(user.getRoleId() == 3){
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                Set<Role> roles = new HashSet<>();
                roles.add(roleDao.getOne(3L));
                user.setRoles(roles);

            }
        }
        userDao.save(user);

    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userDao.getOne(id);
        if (user != null) {
            userDao.delete(id);
        }
    }

    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public User getOne(Long id) {
        return userDao.getOne(id);
    }
}
