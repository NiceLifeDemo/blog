

package com.rczx.blog.util.restfulbody.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.zm.system.utils.util.MapBuilder;

public class ProjectGen {
    private static Map<String, Object> configMap = (new MapBuilder()).add("group-id", "cn.com.zmkj.abc").add("artifact-id", "abc-core").add("boot-version", "1.5.6.RELEASE").add("pavilion-version", "1.0.0").add("name", "abc-wan-source").add("app-class-name", "Abc").add("out-dir", "D:/").build();
    private static String mainJava = "";

    public ProjectGen() {
    }

 /*   public static void main(String[] args) throws IOException {
        String name = (String)configMap.get("name");
        String groupId = (String)configMap.get("group-id");
        String outDir = (String)configMap.get("out-dir");
        File outDirFile = new File(outDir);
        if(!outDirFile.exists()) {
            throw new RuntimeException("out-dir不存在");
        } else {
            mkDir(outDir + name);
            mkDir(outDir + name + "/src");
            mkDir(outDir + name + "/src/main");
            mkDir(outDir + name + "/src/main/java");
            String[] pkgs = groupId.split("\\.");
            String dirs = "";
            String[] resource = pkgs;
            int mavenPomStr = pkgs.length;

            for(int file = 0; file < mavenPomStr; ++file) {
                String mainClassRes = resource[file];
                dirs = dirs + mainClassRes + "/";
                mkDir(outDir + name + "/src/main/java/" + dirs);
            }

            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/web");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/service");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/domain");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/common");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/common/util");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/common/constant");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/common/constant/table");
            mkDir(outDir + name + "/src/main/java/" + groupId.replaceAll("\\.", "/") + "/config");
            mkDir(outDir + name + "/src/main/resources");
            mkDir(outDir + name + "/src/test");
            mkDir(outDir + name + "/src/test/java");
            Resource var21 = (new DefaultResourceLoader()).getResource("classpath:./demo/maven-pom.xml");
            String var22 = FileCopyUtils.copyToString(new InputStreamReader(var21.getInputStream()));
            var22 = replaceWithConfig(var22);
            File var23 = new File(outDir + name + "/pom.xml");
            var23.createNewFile();
            FileCopyUtils.copy(var22, new OutputStreamWriter(new FileOutputStream(var23)));
            Resource var24 = (new DefaultResourceLoader()).getResource("classpath:./demo/main-class.txt");
            String mainClassStr = FileCopyUtils.copyToString(new InputStreamReader(var24.getInputStream()));
            mainClassStr = replaceWithConfig(mainClassStr);
            File mainClassFile = new File(outDir + name + "/src/main/java/" + groupId.replace(".", "/") + "/" + configMap.get("app-class-name").toString() + "Application.java");
            System.out.println(outDir + name + "/src/main/java/" + groupId.replace(".", "/") + "/" + configMap.get("app-class-name").toString() + "Application.java");
            mkFile(mainClassFile);
            FileCopyUtils.copy(mainClassStr, new OutputStreamWriter(new FileOutputStream(mainClassFile)));
            Resource initRes = (new DefaultResourceLoader()).getResource("classpath:./demo/app-init.txt");
            String iniStr = FileCopyUtils.copyToString(new InputStreamReader(initRes.getInputStream()));
            iniStr = replaceWithConfig(iniStr);
            File initFile = new File(outDir + name + "/src/main/java/" + groupId.replace(".", "/") + "/config/ApplicationInit.java");
            mkFile(initFile);
            FileCopyUtils.copy(iniStr, new OutputStreamWriter(new FileOutputStream(initFile)));
            Resource configRes = (new DefaultResourceLoader()).getResource("classpath:./demo/config.txt");
            String configStr = FileCopyUtils.copyToString(new InputStreamReader(configRes.getInputStream()));
            configStr = replaceWithConfig(configStr);
            File configJava = new File(outDir + name + "/src/main/java/" + groupId.replace(".", "/") + "/config/MyConfig.java");
            mkFile(configJava);
            FileCopyUtils.copy(configStr, new OutputStreamWriter(new FileOutputStream(configJava)));
            File proper = new File(outDir + name + "/src/main/resources/application.properties");
            mkFile(proper);
            String properStr = "server.port=80\nserver.servlet-path=/***some-name/\nspring.http.encoding.charset=utf-8\nspring.http.encoding.enabled=true\nspring.http.encoding.force=true\n\nspring.datasource.driverClassName=org.postgresql.Driver\nspring.datasource.url=jdbc:postgresql://***ip:5432/***dbname?stringtype=unspecified\nspring.datasource.username=developer\nspring.datasource.password=password\n\nspring.datasource.tomcat.initial-size =2\nspring.datasource.tomcat.max-idle=2\nspring.datasource.tomcat.max-wait=20000\nspring.datasource.tomcat.min-idle=2\nspring.datasource.tomcat.validation-query=SELECT 1\nspring.datasource.tomcat.test-on-borrow=false\nspring.datasource.tomcat.test-while-idle=true";
            FileCopyUtils.copy(properStr, new OutputStreamWriter(new FileOutputStream(proper)));
        }
    }*/

    private static String replaceWithConfig(String mainClass) {
        Entry entry;
        for(Iterator var1 = configMap.entrySet().iterator(); var1.hasNext(); mainClass = mainClass.replaceAll("\\{" + (String)entry.getKey() + "}", entry.getValue().toString())) {
            entry = (Entry)var1.next();
        }

        return mainClass;
    }

    private static void mkDir(String dirName) {
        boolean mkdir = (new File(dirName)).mkdir();
        if(mkdir) {
            System.out.println(dirName + "\t 目录创建成功");
        } else {
            System.out.println(dirName + "\t ***********目录创建失败");
        }

    }

    private static void mkFile(File fileName) {
        boolean mkdir = false;

        try {
            mkdir = fileName.createNewFile();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        if(mkdir) {
            System.out.println(fileName.getAbsolutePath() + "\t 文件创建成功");
        } else {
            System.out.println(fileName.getAbsolutePath() + "\t ***********文件创建失败");
        }

    }
}
