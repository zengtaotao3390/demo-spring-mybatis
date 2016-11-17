package com.lzrd.service;

import com.lzrd.dao.BaseDao;
import com.lzrd.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengtao on 2016/3/24.
 */
@Service
public class TestService {

    @Autowired
    private BaseDao baseDao;

    public List<Map<String, Object>> findAll() {
         return baseDao.queryForList(Constant.TEST_MAPPER_TEST, new HashMap<String, Object>());
    }
}
