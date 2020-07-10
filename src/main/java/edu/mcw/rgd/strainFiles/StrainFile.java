package edu.mcw.rgd.strainFiles;


import org.apache.log4j.Logger;
import edu.mcw.rgd.datamodel.StrainFiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StrainFile {
    String version;
    String strainPageProd;
    String strainPageDev;
    DAO dao = new DAO();

    protected Logger logger =Logger.getLogger("status");

    public void run() throws Exception{
        String strainPage = getStrainPageDev();

        logger.info(getVersion());
        logger.info("   "+dao.getConnection());
        logger.info("   -- Strain File Pipeline Start --  \n");
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Date lastWeek = subtractWeek(date);
        List<StrainFiles> files = new ArrayList<>();
        files = dao.getStrainFiles();
        List<StrainFiles> newFiles = newStrainsAdded(files,lastWeek);

        if(newFiles.size()==0)
            logger.info("   No new files added within the last week.");
        else {
            for (StrainFiles newFile : newFiles) {
                logger.info(newFile.getStrainId() + ", Content Type: " + newFile.getContentType() + ", File Name: " + newFile.getFileName()
                        + ", File Type: " + newFile.getFileType() + ", Modified By: " + newFile.getModifiedBy());
                String webLink = strainPage + newFile.getStrainId();
                logger.info(webLink);
            }
            logger.info("   Total new files added: " + newFiles.size());
        }
        logger.info("\n   -- Strain File Pipeline End --  ");
    }

    public List<StrainFiles> newStrainsAdded(List<StrainFiles> files, Date lastWeek)throws Exception{
        List<StrainFiles> weeklyStrains = new ArrayList<>();

        for(StrainFiles file : files){

            if(file.getLastModifiedDate()==null)
                continue;
            Date fileDate = file.getLastModifiedDate();

            LocalDate ldFileDate = fileDate.toLocalDate();
            LocalDate ldLastWeek = lastWeek.toLocalDate();

            if(ldFileDate.equals(ldLastWeek) || ldFileDate.isAfter(ldLastWeek))
                weeklyStrains.add(file);
        }

        return weeklyStrains;
    }

    public static Date subtractWeek(Date date) {
        int days = 7;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
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
