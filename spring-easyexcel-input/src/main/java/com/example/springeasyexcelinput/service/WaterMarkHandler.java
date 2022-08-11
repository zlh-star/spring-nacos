package com.example.springeasyexcelinput.service;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RequiredArgsConstructor
public class WaterMarkHandler implements SheetWriteHandler {

    private final String WATER_MARK;
    private final String TEST;

    public static ByteArrayOutputStream createWaterMark(String content,String test) throws IOException {
        int width = 200;
        int height = 150;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "微软雅黑";
        int fontStyle = Font.BOLD;
        int fontSize = 20;

        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 20)); //设置字体颜色和透明度，最后一个参数为透明度
        g2d.setStroke(new BasicStroke(1)); // 设置字体
        g2d.setFont(font); // 设置字体类型  加粗 大小
        g2d.rotate(-0.5, (double) image.getWidth() / 2, (double) image.getHeight() / 2);//设置倾斜度
        FontRenderContext context = g2d.getFontRenderContext();
        float fontHeigth1 = font.getSize2D();
        float fontHeigth2 = (int)font.getSize2D();
        int tempHeight = (int) (fontHeigth1*2+10);
        Rectangle2D bounds = font.getStringBounds(content, context);
        Rectangle2D bound = font.getStringBounds(test, context);
        double x1 = (width - bound.getWidth()) / 2;
//        double y1 = (height - bound.getHeight()) / 2;
//        double ascent1 = -bound.getY();
//        double baseY1 = y1 + ascent1;

        double x = (width - bounds.getWidth()) / 2;
//        double y = (height - bounds.getHeight()) / 2;
//        double ascent = -bounds.getY();
//        double baseY = y + ascent;
        //将不同水印换行输出
        g2d.drawString(content, (int) x, fontHeigth2+10 +tempHeight);
        g2d.drawString(test,(int) x1,fontHeigth2*2+10 +tempHeight);
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os;
    }


    /**
     * 为Excel打上水印工具函数
     *
     * @param sheet excel sheet
     * @param bytes 水印图片字节数组
     */
    public static void putWaterRemarkToExcel(XSSFSheet sheet, byte[] bytes) {
        //add relation from sheet to the picture data
        XSSFWorkbook workbook = sheet.getWorkbook();
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        String rID = sheet.addRelation(null, XSSFRelation.IMAGES, workbook.getAllPictures().get(pictureIdx))
                .getRelationship().getId();
        //set background picture to sheet
        sheet.getCTWorksheet().addNewPicture().setId(rID);
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @SneakyThrows
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        try (ByteArrayOutputStream waterMark = createWaterMark(WATER_MARK,TEST)){
            XSSFSheet sheet = (XSSFSheet) writeSheetHolder.getSheet();
            putWaterRemarkToExcel(sheet, waterMark.toByteArray());
        }
    }
}
