package edu.mcw.rgd.strainFiles;

import edu.mcw.rgd.dao.AbstractDAO;
import edu.mcw.rgd.dao.impl.StrainDAO;
import edu.mcw.rgd.datamodel.StrainFiles;

import java.util.List;

public class DAO {
    StrainDAO sdao =new StrainDAO();

    public DAO() {}

    public String getConnection(){  return sdao.getConnectionInfo(); }

    public List<StrainFiles> getStrainFiles() throws Exception{
        return sdao.getStrainFiles();
    }

}
