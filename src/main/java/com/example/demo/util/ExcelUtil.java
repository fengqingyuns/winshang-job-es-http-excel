package com.example.demo.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * Excel 工具类(POI)
 * 
 * @author litao
 * 
 * @param <T>
 *            需要存储的类 - 支持基本数据类型及String,Date,byte[](图片数据)
 * 
 */
public class ExcelUtil<T> {
    // 表格标题
    private String sheetTitle = "Sheet1";
    // 列标题及列宽
    private String[][] headers = {};
    //显示某些字段值
    private String[] fieldValue = {};
    // 数据集
    private Collection<T> datasets = null;
    // 日期输出格式
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    // 输出流
    private OutputStream out = null;
    // 图片行行高
    public static int PICLINEHEIGHT = 60;
    
    //private List<String[][]> headersList = null;
    
    //private List<String[]> fieldValuesList = null;
    
    //private List<Collection<T>> datasetsList = null;
    //通用
    private List<Map<String,List>> listMap = null;
    public ExcelUtil() {
        super();
    }
    public ExcelUtil(Collection<T> datasets, OutputStream out) {
        super();
        this.datasets = datasets;
        this.out = out;
    }
    public ExcelUtil(String sheetTitle, Collection<T> datasets, OutputStream out) {
        this(datasets, out);
        this.sheetTitle = sheetTitle;
    }
    public ExcelUtil(String[][] headers, Collection<T> datasets, OutputStream out) {
        this(datasets, out);
        this.headers = headers;
    }
    public ExcelUtil(String sheetTitle, String[][] headers, Collection<T> datasets, OutputStream out) {
        this(sheetTitle, datasets, out);
        this.headers = headers;
    }
    
    public ExcelUtil(String sheetTitle, String[][] headers,String[] fieldValues ,Collection<T> datasets, OutputStream out) {
        this(sheetTitle, datasets, out);
        this.headers = headers;
        this.fieldValue = fieldValues;
    }    
    //多重
    public ExcelUtil(String sheetTitle,List<Map<String,List>> listMap, OutputStream out) {
        this.sheetTitle = sheetTitle;
        this.listMap = listMap;
        this.out = out;
        
    }   
    public ExcelUtil(String sheetTitle, String[][] headers, Collection<T> datasets, String dateFormat, OutputStream out) {
        this(sheetTitle, headers, datasets, out);
        this.dateFormat = dateFormat;
    }
    public ExcelUtil(String sheetTitle, String[][] headers,String[] fieldValues, Collection<T> datasets, String dateFormat, OutputStream out) {
        this(sheetTitle, headers, datasets, out);
        this.dateFormat = dateFormat;
    }    
    
    public void ExportExcelMore() throws Exception {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetTitle);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 标题样式
        HSSFCellStyle titleStyles = workbook.createCellStyle();
        // 设置水平居中
        titleStyles.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       
        // 设置垂直居中
        titleStyles.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setColor(HSSFColor.BLACK.index);
        titleFont.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        titleStyles.setFont(titleFont);
        // 正文样式
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.cloneStyleFrom(titleStyles);
        // 正文字体
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontName("宋体");
        bodyFont.setColor(HSSFColor.BLACK.index);
        bodyFont.setFontHeightInPoints((short) 12);
        bodyStyle.setFont(bodyFont);
       
        HSSFRow row = null;
        List<String[][]> headersList = null;
        List<String[]> fieldValueList = null;
        List<Collection<T>> datasetsList = null;
        int temp = 0;
        int num = 0;
        int index = 0;
        for(Map<String,List> map:listMap){
        	 HSSFCellStyle titleStyle = workbook.createCellStyle();
        	 
        	 titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        	 titleStyle.setFont(titleFont);
        	String[][] headers = {};
            String[] fieldValue = {};
            Collection<T> datesets = new ArrayList<>();
        		List<String[][]> headersLists = map.get("headers");
        		if(headersLists != null){
        			headers = headersLists.get(0);
        		}	
        		List<String[]> valuesLists = map.get("fieldValues");
        		if(valuesLists != null){
        			fieldValue = valuesLists.get(0);
        		}
        		List<Collection<T>> dataLists = map.get("datasets");
        		if(dataLists != null){
        			datesets = dataLists.get(0);
        		}
        
                if (headers.length > 0) {
                    // 产生表格标题行
                    row = sheet.createRow(index++);
                    // 设置行高
                    row.setHeightInPoints(25f);
                    for (int i = 0; i < headers.length; i++) {
                    	//temp=0代表第一个headers,i=0代表headers的第一个元素
                    	if(temp == 0){
                    		if(i==0){
                       		 CellRangeAddress region=new CellRangeAddress(0, 0, 0, 1);
                                sheet.addMergedRegion(region);
                    		}
                    		if(i == 2){
                    			 CellRangeAddress region=new CellRangeAddress(0, 0, 3, 4);
                                 sheet.addMergedRegion(region);
                    		}
                    	}
                    	//temp就代表了headers
                    	if(temp == 0 || temp == 2 || temp == 5){
                    		  //设置水平居左
                            titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                    	}else{
                    		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    	}
                    	/**以下temp为3时即第4行**/
                        if(temp == 3){
                        	//第3行第1列和第二列合并
                        	if(i==0){
                        		 CellRangeAddress region=new CellRangeAddress(3, 3, 0, 1);
                                 sheet.addMergedRegion(region);
                        	}
                        	//第3行第2列和第4列合并
                        	if(i == 1){
                        		 CellRangeAddress region=new CellRangeAddress(3, 3, 2, 4);
                                 sheet.addMergedRegion(region);
                        	}
                        }
                        /**以下temp为3时即第4行**/
                        HSSFCell cell = row.createCell(i);
                        HSSFRichTextString text = new HSSFRichTextString(headers[i][0]);
                        
                        cell.setCellValue(text);
                        cell.setCellStyle(titleStyle);
                        
                       
                        // 设置列宽
                        sheet.setColumnWidth(i, Integer.parseInt(headers[i][1]) * 256);
                        num++;
                    }
                }
               
				// 遍历集合数据，产生数据行
                if(null !=datesets && datesets.size()>0){
                	 Iterator<T> it = datesets.iterator();
                     while (it.hasNext()) {
                         row = sheet.createRow(index);
                         // 设置行高
                         row.setHeightInPoints(25f);
                         T t = (T) it.next();
                         // 利用反射，得到属性值
                       //  Field[] fieldValue =  
                        // Field[] fields = t.getClass().getDeclaredFields();
                         
                         for (int i = 0; i < fieldValue.length; i++) {
                             HSSFCell cell = row.createCell(i);
                             cell.setCellStyle(bodyStyle);
                             /**以下temp为3时即第4行**/
                             if(temp == 3){
                             	if(i==0){
                             		 CellRangeAddress region=new CellRangeAddress(4, 4, 0, 1);
                                      sheet.addMergedRegion(region);
                             	}
                             	//第4行第2列和第4列合并
                             	if(i == 1){
                             		 CellRangeAddress region=new CellRangeAddress(4, 4, 2, 4);
                                      sheet.addMergedRegion(region);
                             	}
                             }
                           
                             /**以上temp为3时即第4行**/
                             
                              String fieldName = fieldValue[i];
                              String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  
                              Class tCls = t.getClass();
                              Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                              Object value = getMethod.invoke(t, new Object[] {});
                             // 判断值的类型后进行强制类型转换
                             String textValue = null;
                             if (value instanceof Date) {
                                 Date date = (Date) value;
                                 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                                 textValue = sdf.format(date);
                             } else {
                                 // 其它数据类型都当作字符串简单处理
                             	if(null != value){
                             		textValue = value.toString();
                             	}
                             }
                             // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                             if (textValue != null) {
                                 Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                                 Matcher matcher = p.matcher(textValue);
                                 if (matcher.matches()) {
                                     // 是数字当作double处理
                                     cell.setCellValue(Double.parseDouble(textValue));
                                 } else {
                                     cell.setCellValue(textValue);
                                 }
                             }
                         }
                         index++;
                     }
                }
               // Iterator<T> it = datasets.iterator();
                temp++;
        }
               
        workbook.write(out);
    }
    /**
     * 利用JAVA的反射机制，将集合中的数据输出到指定IO流中
     * 
     * 如有图片,需将图片字段（byte）的顺序与表格中的图片列顺序对应
     * 
     * @throws Exception
     *             异常
     */
    public void ExportExcel() throws Exception {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetTitle);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        // 设置水平居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setColor(HSSFColor.BLACK.index);
        titleFont.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        titleStyle.setFont(titleFont);
        // 正文样式
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.cloneStyleFrom(titleStyle);
        // 正文字体
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontName("宋体");
        bodyFont.setColor(HSSFColor.BLACK.index);
        bodyFont.setFontHeightInPoints((short) 12);
        bodyStyle.setFont(bodyFont);
        int index = 0;
        HSSFRow row = null;
        if (headers.length > 0) {
            // 产生表格标题行
            row = sheet.createRow(index++);
            // 设置行高
            row.setHeightInPoints(30f);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i][0]);
                cell.setCellValue(text);
                cell.setCellStyle(titleStyle);
                // 设置列宽
                sheet.setColumnWidth(i, Integer.parseInt(headers[i][1]) * 256);
            }
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = datasets.iterator();
        while (it.hasNext()) {
            row = sheet.createRow(index);
            // 设置行高
            row.setHeightInPoints(25f);
            T t = (T) it.next();
            // 利用反射，得到属性值
          //  Field[] fieldValue =  
           // Field[] fields = t.getClass().getDeclaredFields();
            
            for (int i = 0; i < fieldValue.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(bodyStyle);
                 String fieldName = fieldValue[i];
                 String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  
                 Class tCls = t.getClass();
                 Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                 Object value = getMethod.invoke(t, new Object[] {});
                // 判断值的类型后进行强制类型转换
                String textValue = null;
                if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                    textValue = sdf.format(date);
                } else {
                    // 其它数据类型都当作字符串简单处理
                	if(null != value){
                		textValue = value.toString();
                	}
                }
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if (textValue != null) {
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        cell.setCellValue(textValue);
                    }
                }
            }
            index++;
        }
        workbook.write(out);
    }
}