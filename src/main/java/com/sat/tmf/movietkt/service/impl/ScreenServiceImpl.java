package com.sat.tmf.movietkt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sat.tmf.movietkt.dao.ScreenDao;
import com.sat.tmf.movietkt.entities.Screen;
import com.sat.tmf.movietkt.entities.Theater;
import com.sat.tmf.movietkt.service.ScreenService;

import java.util.List;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenDao screenDao;

    @Override
    public Screen createScreen(Screen screen) {
        screenDao.save(screen);
        return screen;
    }

    @Override
    public Screen updateScreen(Screen screen) {
        screenDao.saveOrUpdate(screen);
        return screen;
    }

    @Override
    public Screen findById(Integer id) {
        return screenDao.findById(id);
    }

    @Override
    public List<Screen> findAll() {
        return screenDao.findAll();
    }

    @Override
    public List<Screen> findByTheater(Theater theater) {
        return screenDao.findByTheater(theater);
    }

    @Override
    public void deleteScreen(Integer id) {
        Screen s = screenDao.findById(id);
        if (s != null) {
            screenDao.delete(s);
        }
    }
}

