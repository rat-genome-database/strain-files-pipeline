package edu.mcw.rgd.strainFiles;

import edu.mcw.rgd.process.Utils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;

public class Manager {

    public static void main(String[] args)throws Exception{

        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        new XmlBeanDefinitionReader(bf).loadBeanDefinitions(new FileSystemResource("properties/AppConfigure.xml"));

        try{
            StrainFile manager = (StrainFile) (bf.getBean("strainFile"));
            manager.run();
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
