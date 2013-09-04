package com.efsf.sf.util.bik;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.exceptions.CryptographyException;


public class PDFWithText extends PDFTextStripper
{

static StringWriter sw = new StringWriter();   

private static ArrayList<Table> tables= new ArrayList<Table>();
static double[] tableMin={0,   51, 102, 210, 262, 335, 380, 423, 486};
static double[] tableMax={51, 102, 210, 262, 335, 380, 423, 486, 9999};
        
static String last="";
static boolean isTable;

    /**
     * @return the tables
     */
    public static ArrayList<Table> getTables() {
        return tables;
    }
float actual_y, actual_x;    
    
    public PDFWithText() throws IOException
    {
        super.setSortByPosition( true );
    }
    
    public void reset(){
        sw=new StringWriter();
        tables=new ArrayList<Table>();
        last="";
        isTable=false;
    }

    public String getTextFromPDF(String file, String pass) throws Exception
    {
            PDDocument document = null;
            try
            {
                document = PDDocument.load(file);
                
                if( document.isEncrypted() )
                {
                   document.decrypt( pass );
                }
                
                PDFWithText printer = new PDFWithText();
                List allPages = document.getDocumentCatalog().getAllPages();
                for( int i=0; i<allPages.size(); i++ )
                {
                    PDPage page = (PDPage)allPages.get( i );
                    PDStream contents = page.getContents();
                    if( contents != null )
                    {
                        printer.processStream( page, page.findResources(), page.getContents().getStream() );
                    }
                }
            } catch(IOException | CryptographyException e) {
                
                return "";
                
            } finally {
                if( document != null )
                {
                    document.close();
                }
            }
            
            postProcessTables();
            return sw.toString();
        
    }


    @Override
    protected void processTextPosition( TextPosition text )
    {
        char a=text.getCharacter().charAt(0);
       
        
        if(last.endsWith("HISTORIA RACHUNKU")){
            isTable=true;
            getTables().add(new Table());
        }
        if(last.endsWith("INFORMACJE")) isTable=false;        
        
        //NORMAL MODE
        if(text.getYDirAdj()!=actual_y)
        {
            sw.write('\n');
            actual_y=text.getYDirAdj();
            
            if(isTable) getTables().get(getTables().size()-1).data.add(new String[9]);
        }
        
        if(text.getXDirAdj()-actual_x>10)
        {
            sw.write(';');
        }
        
        actual_x=text.getXDirAdj();

       
        if(isTable){
            float x=text.getXDirAdj();
            for(int i=0; i<tableMin.length; i++){
                if(x>tableMin[i] && x<tableMax[i])
                    if(getTables().get(getTables().size()-1).getLastRow()[i]==null){
                       tables.get(tables.size()-1).getLastRow()[i]=String.valueOf(a); 
                    } else {
                       tables.get(tables.size()-1).getLastRow()[i]+=String.valueOf(a);
                    }        
            }
        }
        
        last+=String.valueOf(a);
        sw.write(a);
    }
    
    public void postProcessTables(){
        for(Table t: getTables())
            t.postProcess();
    }
    
}