package com.company.demo.service.impl;

import com.company.demo.entity.Detail;
import com.company.demo.service.IDetailService;
import go.openus.rapidframework.service.impl.JdbcRapidServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl extends JdbcRapidServiceImpl<Detail> implements IDetailService{

}
