package edu.mcw.rgd.strainFiles;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import edu.mcw.rgd.datamodel.StrainFiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StrainFile {
    String version;
    String strainPageProd;
    String strainPageDev;
    DAO dao = new DAO();

    protected Logger logger = LogManager.getLogger("status");

    public void run() throws Exception{
        String strainPage = getStrainPageProd();

        logger.info(getVersion());
        logger.info("   "+dao.getConnection());
        logger.info("   -- Strain File Pipeline Start --  \n");
        LocalDate lastWeek = LocalDate.now().minusWeeks(1);
        List<StrainFiles> curFiles = dao.getStrainFiles();
        List<StrainFiles> newFiles = newStrainsAdded(curFiles,lastWeek);

        if(newFiles.size()==0)
            logger.info("   No new files added within the last week.");
        else {
            for (StrainFiles newFile : newFiles) {
                if(newFile.getModifiedBy() != null) {
                    logger.info(newFile.getStrainId() + ", Content Type: " + newFile.getContentType() + ", File Name: " + newFile.getFileName()
                            + ", File Type: " + newFile.getFileType() + ", Modified By: " + newFile.getModifiedBy());
                }
                else{ // modifiedBy is null
                    logger.info(newFile.getStrainId() + ", Content Type: " + newFile.getContentType() + ", File Name: " + newFile.getFileName()
                            + ", File Type: " + newFile.getFileType() + ", was modified/made before column was added. " );
                }
                String webLink = strainPage + newFile.getStrainId();
                logger.info(webLink);
            }
            logger.info("   Total new files added: " + newFiles.size());
        }
        logger.info("\n   -- Strain File Pipeline End --  ");
    }

    public List<StrainFiles> newStrainsAdded(List<StrainFiles> files, LocalDate lastWeek){
        List<StrainFiles> weeklyStrains = new ArrayList<>();

        for(StrainFiles file : files){

            if(file.getLastModifiedDate()==null)
                continue;

            LocalDate fileDate = file.getLastModifiedDate().toLocalDate();

            if(!fileDate.isBefore(lastWeek))
                weeklyStrains.add(file);
        }

        return weeklyStrains;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setStrainPageProd(String strainPageProd) {
        this.strainPageProd = strainPageProd;
    }

    public String getStrainPageProd() {
        return strainPageProd;
    }

    public void setStrainPageDev(String strainPageDev) {
        this.strainPageDev = strainPageDev;
    }

    public String getStrainPageDev() {
        return strainPageDev;
    }
}
