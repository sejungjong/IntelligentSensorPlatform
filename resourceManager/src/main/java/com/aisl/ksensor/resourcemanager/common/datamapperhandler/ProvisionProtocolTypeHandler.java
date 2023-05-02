package com.aisl.ksensor.resourcemanager.common.datamapperhandler;


import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ProvisionProtocol;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Mybatis enum type handler class (ProvisionProtocol)
 */
public class ProvisionProtocolTypeHandler extends EnumTypeHandler<ProvisionProtocol> {

    public ProvisionProtocolTypeHandler(Class<ProvisionProtocol> type) {
        super(type);
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, ProvisionProtocol parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, parameter.getCode());
        }
    }

    @Override
    public ProvisionProtocol getResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        try {
            return ProvisionProtocol.parseType(result);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public ProvisionProtocol getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        try {
            return ProvisionProtocol.parseType(result);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public ProvisionProtocol getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        try {
            return ProvisionProtocol.parseType(result);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

