//package com.sergio.generator;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import freemarker.template.Configuration;
//import freemarker.template.DefaultObjectWrapper;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//@Controller
//@Api(tags = "crud生成器")
//public class GeneratorController {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostMapping(value = "/generate-code")
//    public Object generator(@Valid @ModelAttribute FormEntity formEntity) throws IOException, TemplateException {
//
//        Iterator<EntityField> iterator = formEntity.getEntityFields().iterator();
//        while (iterator.hasNext()) {//iterator.hasNext(),用于检查序列中是否还有元素
//            EntityField entityField = iterator.next();//获得序列中的下一个元素
//            if (entityField.getFieldName() == null) {
//                iterator.remove();//循环遍历，直至为null时，将迭代器新返回的元素移除
//            }
//        }
//
//        String JsonStr=objectMapper.writeValueAsString(formEntity);//将对象转换为json字符串
//        Map<String, Object> map = objectMapper.readValue(JsonStr, Map.class);//将字符串转换为对象
//
//        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
//        config.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
//
//        String resultBaseDir ="C:/out/" ;
//        String templateBaseDir = "lowcode-crud/src/main/resources/templates/generator/";
//
//        // entity
//        String entityResultPath = resultBaseDir + "mybatis/entity/" + (String) formEntity.getEntityName() + ".java";
//        String entityTemplatePath = templateBaseDir + "Entity.ftl";
//        generatorCode(entityResultPath, entityTemplatePath, config, map);
////        toDownload(response,entityTemplatePath,entityResultPath);
//
//        // mapper
//        String mapperResultPath = resultBaseDir + "mybatis/mapper/" + (String) formEntity.getEntityName() + "Mapper.java";
//        String mapperTemplatePath = templateBaseDir + "Mapper.ftl";
////        toDownload(response,mapperTemplatePath,mapperResultPath);
//        generatorCode(mapperResultPath, mapperTemplatePath, config, map);
//
//        // createInDTO
//        String createInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "CreateInDTO.java";
//        String createInDTOTemplatePath = templateBaseDir + "InDTO_Create.ftl";
////        toDownload(response,createInDTOTemplatePath,createInDTOResultPath);
//        generatorCode(createInDTOResultPath, createInDTOTemplatePath, config, map);
//
//        // updateInDTO
//        String updateInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "UpdateInDTO.java";
//        String updateInDTOTemplatePath = templateBaseDir + "InDTO_Update.ftl";
////        toDownload(response,updateInDTOTemplatePath,updateInDTOResultPath);
//        generatorCode(updateInDTOResultPath, updateInDTOTemplatePath, config, map);
//
//        // queryInDTO
//        String queryInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "QueryInDTO.java";
//        String queryInDTOTemplatePath = templateBaseDir + "InDTO_Query.ftl";
////        toDownload(response,queryInDTOTemplatePath,queryInDTOResultPath);
//        generatorCode(queryInDTOResultPath, queryInDTOTemplatePath, config, map);
//
//        // controller
//        String controllerResultPath = resultBaseDir + "controller/" + (String) formEntity.getEntityName() + "Controller.java";
//        String controllerTemplatePath = templateBaseDir + "Controller.ftl";
// //       toDownload(response,controllerTemplatePath,controllerResultPath);
//        generatorCode(controllerResultPath, controllerTemplatePath, config, map);
//
//        // createInBO
//        String createInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "CreateInBO.java";
//        String createInBOTemplatePath = templateBaseDir + "InBO_Create.ftl";
////        toDownload(response,createInBOTemplatePath,createInBOResultPath);
//        generatorCode(createInBOResultPath, createInBOTemplatePath, config, map);
//
//        // updateInBO
//        String updateInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "UpdateInBO.java";
//        String updateInBOTemplatePath = templateBaseDir + "InBO_Update.ftl";
////        toDownload(response,updateInBOTemplatePath,updateInBOResultPath);
//        generatorCode(updateInBOResultPath, updateInBOTemplatePath, config, map);
//
//        // queryInBO
//        String queryInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "QueryInBO.java";
//        String queryInBOTemplatePath = templateBaseDir + "InBO_Query.ftl";
////        toDownload(response,queryInBOTemplatePath,queryInBOResultPath);
//        generatorCode(queryInBOResultPath, queryInBOTemplatePath, config, map);
//
//        // service
//        String serviceResultPath = resultBaseDir + "service/" + (String) formEntity.getEntityName() + "Service.java";
//        String serviceTemplatePath = templateBaseDir + "Service.ftl";
////        toDownload(response,serviceTemplatePath,serviceResultPath);
//        generatorCode(serviceResultPath, serviceTemplatePath, config, map);
//
//        // serviceImpl
//        String serviceImplResultPath = resultBaseDir + "service/impl/" + (String) formEntity.getEntityName() + "ServiceImpl.java";
//        String serviceImplTemplatePath = templateBaseDir + "ServiceImpl.ftl";
////        toDownload(response,serviceImplTemplatePath,serviceImplResultPath);
//        generatorCode(serviceImplResultPath, serviceImplTemplatePath, config, map);
//
//        // sql
//        String sqlResultPath = resultBaseDir + "sql/sql.sql";
//        String sqlTemplatePath = templateBaseDir + "Sql.ftl";
////        toDownload(response,sqlTemplatePath,sqlResultPath);
//        generatorCode(sqlResultPath, sqlTemplatePath, config, map);
//
//        formEntity.setInitFlag(0);
//        return "generator";
//    }
//
//    // ----------------------------------------------
//
//    @GetMapping("/generator")
//    public String test(ModelMap map) {
//        FormEntity formEntity = new FormEntity();
//        formEntity.setTableName("demo");
//        formEntity.setEntityName("Demo");
//
//        List<EntityField> entityFields = new ArrayList<>();
//
//        EntityField entityField = new EntityField();
//        entityField.setFieldName("id");
//        entityField.setFieldType("String");
//        entityField.setDbColumn("id");
//        entityField.setDbType("varchar(36)");
//        entityField.setNotNull("on");
//        entityField.setComment("id");
//        entityFields.add(entityField);
//
//        EntityField entityField1 = new EntityField();
//        entityField1.setFieldName("accountId");
//        entityField1.setFieldType("String");
//        entityField1.setDbColumn("account_id");
//        entityField1.setDbType("varchar(36)");
//        entityField1.setNotNull("on");
//        entityField1.setComment("account id");
//        entityFields.add(entityField1);
//
//        formEntity.setEntityFields(entityFields);
//        formEntity.setInitFlag(1);
//
//        map.put("formEntity", formEntity);
//
//        return "generator";
//    }
//
////    @GetMapping("/download")
////    @ApiOperation(value = "文件下载", notes = "参数:无")
////    public void findOperatorCharBar2(HttpServletResponse response, String path, String docName) throws IOException {
////        toDownload(response, path, docName);
////
////    }
//
//    @GetMapping(value = "/generate-code")
//    public String generator() {
//        return "redirect:generator";
//    }
//
////    private void generatorCode(String resultPath, String templatePath, Configuration config, Map<String, Object> map) {
//////        ServletOutputStream out = null;
//////        InputStream inputStream = null;
//////        HttpServletResponse response=null;
////        try {
////            prepareFile(resultPath);
////            Template template = config.getTemplate(templatePath, "UTF-8");
////            //将resultPath中的数据写入file
////            FileOutputStream fileOutputStream=new FileOutputStream(resultPath);
////            //将字符流转为字节流，并定义编码
////            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");
////            //将字符输出
////            Writer out = new BufferedWriter(outputStreamWriter);
////            template.process(map, out);
////            out.flush();//刷新缓冲流，并写入文件
////            out.close();
////        } catch (IOException | TemplateException e) {
////            e.printStackTrace();
////
////            File file = new File(resultPath);
////            if (file.exists()) {
////                file.delete();
////            }
////        }
////    }
//
//    private void generatorCode(HttpServletResponse response,String resultPath, String templatePath, Configuration config, Map<String, Object> map,String docName) {
//        ServletOutputStream out1 = null;
//        InputStream inputStream = null;
//        try {
//            //中文转义,浏览器中文需要转义,否则报400参数错误
//            int lastIndexOf = resultPath.lastIndexOf('/');
//            String fileName = resultPath.substring(lastIndexOf + 1);
//            // 转义关键代码
//            String newFileName = URLEncoder.encode(fileName, "utf-8");
//            String subUrl = resultPath.substring(0, lastIndexOf + 1);
//            String newUrl = subUrl + newFileName;
//
//            URL url = new URL(newUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//
//            conn.setConnectTimeout(3 * 1000);
//            //防止屏蔽程序抓取而返回403错误
//            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            inputStream = conn.getInputStream();
////            prepareFile(inputStream);
//            Template template = config.getTemplate(templatePath, "UTF-8");
//            //将resultPath中的数据写入file
////            FileOutputStream fileOutputStream=new FileOutputStream(resultPath);
//////            将字符流转为字节流，并定义编码
////            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");
//////            将字符输出
////            Writer out = new BufferedWriter(outputStreamWriter);
////            template.process(map, out);
//            //刷新缓冲流，并写入文件
//
//            /**
//             * 输出文件到浏览器
//             */
//            int len = 0;
//            // 输出 下载的响应头，如果下载的文件是中文名，文件名需要经过url编码
//            response.setContentType("application/x-download");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docName, "UTF-8"));
//            response.setHeader("Cache-Control", "no-cache");
//            out1 = response.getOutputStream();
//            byte[] buffer = new byte[1024];
//            while ((len = inputStream.read(buffer)) > 0) {
//                out1.write(buffer, 0, len);
//            }
//            out1.flush();
//            out1.close();
////            out1.flush();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (out1!= null) {
//                try {
//                    out1.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    private String prepareFile(String resultPath) throws IOException {
//
//        // if file exists, delete it
//        File file = new File(resultPath);
//        if (file.exists()) {
//            file.delete();
//        }
//
//        if (!file.getParentFile().exists()) {
//            //避免因为文件夹不存在而报出的异常(如果文件夹不存在的话，就创建该文件夹)
//            boolean mkdir = file.getParentFile().mkdirs();
//            if (!mkdir) {
//                throw new RuntimeException("mkdir parent directory failed");
//            }
//        }
//        // 最后创建文件，这样更为保险
////        String path="http://ceshi";
//        file.createNewFile();
////        String file2=String.valueOf(file);
////        toDownload(response,resultPath,file2);
//
//        return resultPath;
//    }
////    private String prepareFile(String resultPath) throws IOException {
////
////        // if file exists, delete it
////        File file = new File(resultPath);
////        if (file.exists()) {
////            file.delete();
////        }
////
////        if (!file.getParentFile().exists()) {
////            //避免因为文件夹不存在而报出的异常(如果文件夹不存在的话，就创建该文件夹)
////            boolean mkdir = file.getParentFile().mkdirs();
////            if (!mkdir) {
////                throw new RuntimeException("mkdir parent directory failed");
////            }
////        }
////        // 最后创建文件，这样更为保险
//////        String path="http://ceshi";
////        file.createNewFile();
//////        String file2=String.valueOf(file);
//////        toDownload(response,resultPath,file2);
////
////        return resultPath;
////    }
//
//    public static void toDownload(HttpServletResponse response, String path, String docName) {
//        ServletOutputStream out = null;
//        InputStream inputStream = null;
//
//        try {
//            //中文转义,浏览器中文需要转义,否则报400参数错误
//            int lastIndexOf = path.lastIndexOf('/');
//            String fileName = path.substring(lastIndexOf + 1);
//            // 转义关键代码
//            String newFileName = URLEncoder.encode(fileName, "utf-8");
//            String subUrl = path.substring(0, lastIndexOf + 1);
//            String newUrl = subUrl + newFileName;
//
//            URL url = new URL(newUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//
//            conn.setConnectTimeout(3 * 1000);
//            //防止屏蔽程序抓取而返回403错误
//            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            inputStream = conn.getInputStream();
//            /**
//             * 输出文件到浏览器
//             */
//            int len = 0;
//            // 输出 下载的响应头，如果下载的文件是中文名，文件名需要经过url编码
//            response.setContentType("application/x-download");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docName, "UTF-8"));
//            response.setHeader("Cache-Control", "no-cache");
//            out = response.getOutputStream();
//            byte[] buffer = new byte[1024];
//            while ((len = inputStream.read(buffer)) > 0) {
//                out.write(buffer, 0, len);
//            }
//            out.flush();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void zipFiles(List<File> srcfile, String zipFileName, HttpServletResponse response) throws IOException {
//        byte[] buf = new byte[1024];
//        // 获取输出流
//        BufferedOutputStream bos = null;
//        try {
//            bos = new BufferedOutputStream(response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileInputStream in = null;
//        ZipOutputStream out = null;
//        try {
//            response.reset(); // 重点突出
//            // 不同类型的文件对应不同的MIME类型
//            response.setContentType("application/x-msdownload");
//            response.setCharacterEncoding("utf-8");
//            response.setHeader("Content-disposition", "attachment;filename=" + zipFileName + ".zip");
//
//            // ZipOutputStream类：完成文件或文件夹的压缩
//            out = new ZipOutputStream(bos);
//            for (int i = 0; i < srcfile.size(); i++) {
//                in = new FileInputStream(srcfile.get(i));
//                // 给列表中的文件单独命名
//                out.putNextEntry(new ZipEntry(srcfile.get(i).getName()));
//                int len = -1;
//                while ((len = in.read(buf)) != -1) {
//                    out.write(buf, 0, len);
//                }
//            }
//            out.close();
//            bos.close();
////            log.info("压缩完成.");
//            System.out.println("压缩完成");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null)
//            {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//}
//
