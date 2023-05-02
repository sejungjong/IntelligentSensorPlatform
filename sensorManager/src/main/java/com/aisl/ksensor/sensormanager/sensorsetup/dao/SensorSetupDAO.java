package com.aisl.ksensor.sensormanager.sensorsetup.dao;

import com.aisl.ksensor.sensormanager.sensorsetup.vo.SensorSetupBaseVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SensorSetupDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public int createSensorSetupVO(SensorSetupBaseVO sensorSetupBaseVO) {
        return sqlSession.update("sensormanager.sensorsetup.createSensorSetupBase", sensorSetupBaseVO);
    }


    public SensorSetupBaseVO getSensorSetupBaseVO(SensorSetupBaseVO sensorSetupBaseVO) {
        return sqlSession.selectOne("sensormanager.sensorsetup.selectById", sensorSetupBaseVO);
    }

}
