package com.ainetwork.service.impl;

import com.ainetwork.entity.OID;
import com.ainetwork.mapper.OIDMapper;
import com.ainetwork.service.OIDService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OIDServiceImpl extends  ServiceImpl<OIDMapper, OID> implements OIDService{
}
