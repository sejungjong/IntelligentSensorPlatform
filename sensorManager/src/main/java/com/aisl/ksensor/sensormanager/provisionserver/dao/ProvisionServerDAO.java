package com.aisl.ksensor.sensormanager.provisionserver.dao;

import com.aisl.ksensor.sensormanager.provisionserver.vo.ProvisionServerBaseVO;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionServerType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProvisionServerDAO {


    private final SqlSessionTemplate sqlSession;

    @Autowired
    public ProvisionServerDAO(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    public ProvisionServerBaseVO getProvisionServerVOByTypeLatest(ProvisionServerType type) {
        System.out.println("type is" + type.getCode());
        return sqlSession.selectOne("sensormanager.provisionserver.selectByTypeLatest", type.getCode());
//        return sqlSession.selectOne("sensormanager.provisionserver.selectByTypeLatest", "resourceManager");

    }

}

