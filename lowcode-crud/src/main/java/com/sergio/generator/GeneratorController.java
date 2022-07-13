package com.sergio.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "crud生成器")
public class GeneratorController {

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/generate-code")
    public String generator(@Valid @ModelAttribute FormEntity formEntity) throws IOException, TemplateException {

        Iterator<EntityField> iterator = formEntity.getEntityFields().iterator();
        while (iterator.hasNext()) {//iterator.hasNext(),用于检查序列中是否还有元素
            EntityField entityField = iterator.next();//获得序列中的下一个元素
            if (entityField.getFieldName() == null) {
                iterator.remove();//循环遍历，直至为null时，将迭代器新返回的元素移除
            }
        }

        String JsonStr=objectMapper.writeValueAsString(formEntity);//将对象转换为json字符串
        Map<String, Object> map = objectMapper.readValue(JsonStr, Map.class);//将字符串转换为对象

        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));

        String resultBaseDir ="C:/out/" ;
        String templateBaseDir = "lowcode-crud/src/main/resources/templates/generator/";

        // entity
        String entityResultPath = resultBaseDir + "mybatis/entity/" + (String) formEntity.getEntityName() + ".java";
        String entityTemplatePath = templateBaseDir + "Entity.ftl";
        generatorCode(entityResultPath, entityTemplatePath, config, map);

        // mapper
        String mapperResultPath = resultBaseDir + "mybatis/mapper/" + (String) formEntity.getEntityName() + "Mapper.java";
        String mapperTemplatePath = templateBaseDir + "Mapper.ftl";
        generatorCode(mapperResultPath, mapperTemplatePath, config, map);

        // createInDTO
        String createInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "CreateInDTO.java";
        String createInDTOTemplatePath = templateBaseDir + "InDTO_Create.ftl";
        generatorCode(createInDTOResultPath, createInDTOTemplatePath, config, map);

        // updateInDTO
        String updateInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "UpdateInDTO.java";
        String updateInDTOTemplatePath = templateBaseDir + "InDTO_Update.ftl";
        generatorCode(updateInDTOResultPath, updateInDTOTemplatePath, config, map);

        // queryInDTO
        String queryInDTOResultPath = resultBaseDir + "controller/dto/" + (String) formEntity.getEntityName() + "QueryInDTO.java";
        String queryInDTOTemplatePath = templateBaseDir + "InDTO_Query.ftl";
        generatorCode(queryInDTOResultPath, queryInDTOTemplatePath, config, map);

        // controller
        String controllerResultPath = resultBaseDir + "controller/" + (String) formEntity.getEntityName() + "Controller.java";
        String controllerTemplatePath = templateBaseDir + "Controller.ftl";
        generatorCode(controllerResultPath, controllerTemplatePath, config, map);

        // createInBO
        String createInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "CreateInBO.java";
        String createInBOTemplatePath = templateBaseDir + "InBO_Create.ftl";
        generatorCode(createInBOResultPath, createInBOTemplatePath, config, map);

        // updateInBO
        String updateInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "UpdateInBO.java";
        String updateInBOTemplatePath = templateBaseDir + "InBO_Update.ftl";
        generatorCode(updateInBOResultPath, updateInBOTemplatePath, config, map);

        // queryInBO
        String queryInBOResultPath = resultBaseDir + "service/bo/" + (String) formEntity.getEntityName() + "QueryInBO.java";
        String queryInBOTemplatePath = templateBaseDir + "InBO_Query.ftl";
        generatorCode(queryInBOResultPath, queryInBOTemplatePath, config, map);

        // service
        String serviceResultPath = resultBaseDir + "service/" + (String) formEntity.getEntityName() + "Service.java";
        String serviceTemplatePath = templateBaseDir + "Service.ftl";
        generatorCode(serviceResultPath, serviceTemplatePath, config, map);

        // serviceImpl
        String serviceImplResultPath = resultBaseDir + "service/impl/" + (String) formEntity.getEntityName() + "ServiceImpl.java";
        String serviceImplTemplatePath = templateBaseDir + "ServiceImpl.ftl";
        generatorCode(serviceImplResultPath, serviceImplTemplatePath, config, map);

        // sql
        String sqlResultPath = resultBaseDir + "sql/sql.sql";
        String sqlTemplatePath = templateBaseDir + "Sql.ftl";
        generatorCode(sqlResultPath, sqlTemplatePath, config, map);

        formEntity.setInitFlag(0);
        return "generator";
    }

    // ----------------------------------------------

    @GetMapping("/generator")
    public String test(ModelMap map) {
        FormEntity formEntity = new FormEntity();
        formEntity.setTableName("demo");
        formEntity.setEntityName("Demo");

        List<EntityField> entityFields = new ArrayList<>();

        EntityField entityField = new EntityField();
        entityField.setFieldName("id");
        entityField.setFieldType("String");
        entityField.setDbColumn("id");
        entityField.setDbType("varchar(36)");
        entityField.setNotNull("on");
        entityField.setComment("id");
        entityFields.add(entityField);

        EntityField entityField1 = new EntityField();
        entityField1.setFieldName("accountId");
        entityField1.setFieldType("String");
        entityField1.setDbColumn("account_id");
        entityField1.setDbType("varchar(36)");
        entityField1.setNotNull("on");
        entityField1.setComment("account id");
        entityFields.add(entityField1);

        formEntity.setEntityFields(entityFields);
        formEntity.setInitFlag(1);

        map.put("formEntity", formEntity);

        return "generator";
    }

    @GetMapping(value = "/generate-code")
    public String generator() {
        return "redirect:generator";
    }

    private void generatorCode(String resultPath, String templatePath, Configuration config, Map<String, Object> map) {
        try {
            prepareFile(resultPath);
            Template template = config.getTemplate(templatePath, "UTF-8");
            //将resultPath中的数据写入file
            FileOutputStream fileOutputStream=new FileOutputStream(resultPath);
            //将字符流转为字节流，并定义编码
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");
            //将字符输出
            Writer out = new BufferedWriter(outputStreamWriter);
            template.process(map, out);
            out.flush();//刷新缓冲流，并写入文件
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();

            File file = new File(resultPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private String prepareFile(String resultPath) throws IOException {

        // if file exists, delete it
        File file = new File(resultPath);
        if (file.exists()) {
            file.delete();
        }

        if (!file.getParentFile().exists()) {
            //避免因为文件夹不存在而报出的异常(如果文件夹不存在的话，就创建该文件夹)
            boolean mkdir = file.getParentFile().mkdirs();
            if (!mkdir) {
                throw new RuntimeException("mkdir parent directory failed");
            }
        }
        // 最后创建文件，这样更为保险
        file.createNewFile();

        return resultPath;
    }

}

