package com.aisl.ksensor.sensormanager.provisionserver.vo;

import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionServerType;
import com.aisl.ksensor.sensormanager.provisionserver.dao.ProvisionServerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionServerSVC {

    @Autowired
    private ProvisionServerDAO provisionServerDAO;


    public ProvisionServerBaseVO getProvisionServerVOByTypeLatest(ProvisionServerType type) {
        return provisionServerDAO.getProvisionServerVOByTypeLatest(type);
    }

}
