package com.aisl.ksensor.resourcemanager.sensorsetup.dao;

import com.aisl.ksensor.resourcemanager.sensorsetup.vo.SensorSetupBaseVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorSetupDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List<SensorSetupBaseVO> getDataModelBaseVOList() {
        return sqlSession.selectList("resourcemanager.sensorsetup.selectAll");
    }

    public SensorSetupBaseVO getDataModelBaseVOById(SensorSetupBaseVO dataModelBaseVO) {
        return sqlSession.selectOne("resourcemanager.sensorsetup.selectById", dataModelBaseVO);
    }

    public int createDataModelBaseVO(SensorSetupBaseVO dataModelBaseVO) {
        return sqlSession.update("resourcemanager.sensorsetup.createDataModelBase", dataModelBaseVO);
    }

    public int updateDataModelBase(SensorSetupBaseVO dataModelBaseVO) {
        return sqlSession.update("resourcemanager.sensorsetup.updateDataModelBase", dataModelBaseVO);
    }

    public int deleteDataModelBaseVO(SensorSetupBaseVO dataModelBaseVO) {
        return sqlSession.update("resourcemanager.sensorsetup.deleteDataModelBase", dataModelBaseVO);
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
}

