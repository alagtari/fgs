
package com.example.filedemo.model;
import java.awt.Color;





import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.filedemo.service.ColisService;
import com.example.filedemo.repository.ColisRepository;
import com.example.filedemo.controller.ColisController;
import  com.example.filedemo.model.Colis;
import  com.example.filedemo.model.Fournisseur;
import com.example.filedemo.service.FournisseurService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.* ;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import  com.example.filedemo.model.Runsheet;
import com.example.filedemo.service.RunsheetService;
import com.itextpdf.text.pdf.PdfWriter;


@CrossOrigin("*")
public class PDFGenerator3 {


    private static Logger logger = LoggerFactory.getLogger(PDFGenerator3.class);

    public static ByteArrayInputStream runsheetPDFReport(Runsheet r) throws MalformedURLException, IOException {
        Rectangle pageSize = new Rectangle(700, 1000) ;
        Document document = new Document(pageSize);
        ByteArrayOutputStream out = new ByteArrayOutputStream();





        try {

            PdfWriter.getInstance(document, out);
            document.open();

            Image logo = Image.getInstance("C:\\Users\\bureau\\Desktop\\delisas-backend\\src\\main\\java\\logo\\Delisas.png");
            logo.setBorder(1);
            logo.setAlignment(5);
            logo.setAbsolutePosition(-30,800);
            logo.scaleToFit(250,250);

            Image image = Image.getInstance("C:\\Users\\bureau\\Desktop\\delisas-backend\\src\\main\\java\\imageBarCodeRunsheet\\"+ r.createRunsheetBarCode() +".jpg");
            image.setBorder(1);
            image.setAbsolutePosition( 450,900);
            image.scaleToFit(250,200);
            image.setAlignment(50);
            document.add(image);
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));


            Font font5 = FontFactory.getFont(FontFactory.HELVETICA ,20, BaseColor.BLACK );

            Phrase para111 = new Phrase (   r.createRunsheetBarCode() , font5);


            PdfPCell cell9 = new PdfPCell(para111);
            cell9.setHorizontalAlignment (Element.ALIGN_CENTER);

            cell9.setBorder( Rectangle.BOX) ;
            cell9.setBorderColor( new BaseColor(253, 254, 254));
            cell9.setBorderWidth(1f);


            PdfPTable table9 = new PdfPTable(1);
            table9.addCell(cell9);
            table9.setHorizontalAlignment (Element.ALIGN_RIGHT);

            table9.setWidthPercentage(50f);
            document.add(table9);





            document.add(logo);
            document.add(new Phrase("\n"));




            // Add Text to PDF file ->// Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.HELVETICA,15, BaseColor.WHITE ) ;
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA ,15 , BaseColor.BLACK);

            Paragraph para1 = new Paragraph( "                                                 Coordonnées Livreur", font );

            PdfPCell cell = new PdfPCell(para1);
            cell.setHorizontalAlignment (Element.ALIGN_LEFT);

            cell.setBorder( Rectangle.BOX) ;
            cell.setBorderColor( new BaseColor(53, 122, 183));
            cell.setBackgroundColor(new BaseColor(53, 122, 183) ) ;
            cell.setBorderWidth(10f);
            cell.setColspan(50);
            cell.setRowspan(50);
            PdfPTable table1 = new PdfPTable(1);
            table1.addCell(cell);
            table1.setHorizontalAlignment (Element.ALIGN_LEFT);
            table1.setWidthPercentage(100f);




            document.add(table1);
            PdfPCell myCell = new PdfPCell(new Paragraph(" ") );
            myCell.setBorder(Rectangle.BOTTOM);
            myCell.setBorderColor(new BaseColor(53, 122, 183));
            document.add(new Phrase("\n")) ;
            PdfPTable table111 = new PdfPTable(1);
            table111.addCell(myCell);


            Phrase para11 = new Phrase ("                          Nom et Prénom    :    " +  r.getLivreur().getNom() + " "+ r.getLivreur().getPrenom() , font1);


            document.add(para11);




            PdfPTable table = new PdfPTable(9);
            table.setTotalWidth(650f);
            table.setLockedWidth(true);



            float[] columnWidths = new float[] {90f,70f, 70f, 80f, 70f,95f, 40f, 80f, 60f};
            table.setWidths(columnWidths);


            Font font10 = FontFactory.getFont(FontFactory.HELVETICA ,12, BaseColor.BLACK);


            // Add PDF Table Header ->
            Stream.of("Code à barres " ,"Expéditeur","Téléphone Expéditeur" ,"Client",  " Téléphone Client" , "Destination" ,"COD" , "Remarque" , "Etat")

                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA,12,BaseColor.WHITE);

                        header.setBackgroundColor(BaseColor.WHITE);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(1);
                        header.setBorderColor(new BaseColor(53, 122, 183));
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        header.setBackgroundColor(new BaseColor(53, 122, 183)) ;
                        table.addCell(header);

                    });


            List<Colis> c = r.getColis();
            for(Colis c4 : c)
            {


                Image image2 = Image.getInstance("C:\\Users\\bureau\\Desktop\\delisas-backend\\src\\main\\java\\imageBarCodeColis\\" + c4.getBar_code() + ".jpg");
                image2.setBorder(1);


                Font font11 = FontFactory.getFont(FontFactory.HELVETICA ,12, BaseColor.BLACK);


                PdfPCell RefCell = new PdfPCell(new Phrase(  c4.bar_code  , font11) );
                RefCell.addElement(image2);
                RefCell.addElement(new Phrase("   "+c4.bar_code , font11)) ;

                RefCell.setPaddingLeft(4);
                RefCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                RefCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                RefCell.setBorderWidth(1);
                RefCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(RefCell);

                PdfPCell  expCell = new PdfPCell(new Phrase(c4.getFournisseur().getNom_f() +" "+ c4.getFournisseur().getPrenom_f() , font10));
                expCell.setPaddingLeft(4);
                expCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                expCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                expCell.setBorderWidth(1);
                expCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(expCell);


                PdfPCell  telexpCell = new PdfPCell(new Phrase(String.valueOf(c4.getFournisseur().getTel()) , font10));
                telexpCell.setPaddingLeft(4);
                telexpCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                telexpCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                telexpCell.setBorderWidth(1);
                telexpCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(telexpCell);


                PdfPCell clCell = new PdfPCell(new Phrase(c4.getNom_c()+ " " +  c4.getPrenom_c() , font10));
                clCell.setPaddingLeft(4);
                clCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                clCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                clCell.setBorderWidth(1);
                clCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(clCell);


                PdfPCell telCell = new PdfPCell(new Phrase(String.valueOf(c4.getTel_c_1()) , font10));
                telCell.setPaddingLeft(4);
                telCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                telCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                telCell.setBorderWidth(1);
                telCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(telCell);


                PdfPCell adCell = new PdfPCell(new Phrase(c4.getGouvernorat() + " " + c4.getDelegation() +" "+ c4.getAdresse() +" " , font10));
                adCell.setPaddingLeft(4);
                adCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                adCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                adCell.setBorderWidth(1);
                adCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(adCell);



                PdfPCell coCell = new PdfPCell(new Phrase(String.valueOf(c4.getCod()) , font10));
                coCell.setPaddingLeft(4);
                coCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                coCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                coCell.setBorderWidth(1);
                coCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(coCell);



                PdfPCell reCell = new PdfPCell(new Phrase(c4.getRemarque() , font10));
                reCell.setPaddingLeft(4);
                reCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                reCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                reCell.setBorderWidth(1);
                reCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(reCell);



                PdfPCell etatCell = new PdfPCell(new Phrase(c4.getEtat().toString() , font10));
                etatCell.setPaddingLeft(4);
                etatCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                etatCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                etatCell.setBorderWidth(1);
                etatCell.setBorderColor(new BaseColor(53, 122, 183));
                table.addCell(etatCell);


            }
            document.add(table);



            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));





            Font font2 = FontFactory.getFont(FontFactory.HELVETICA,15,  BaseColor.DARK_GRAY);

            LocalDateTime dateCreation1 = LocalDateTime.now();
            Paragraph para33 = new Paragraph(       "      Date                                        Signature Coursier                                 Cachet Socièté"  , font2  );
            Paragraph para44 = new Paragraph(                        String.valueOf( dateCreation1.getYear()) +"-" +String.valueOf(dateCreation1.getMonthValue()) + "-"+ String.valueOf(dateCreation1.getDayOfMonth()) + "                                         ..................                                            .................."  , font2);

            document.add(new Phrase("\n"));


            document.add(para33) ;
            document.add(new Phrase("\n"));

            document.add(para44) ;




            document.close(); }

        catch(DocumentException e) {
            logger.error(e.toString());

        }
        return new ByteArrayInputStream(out.toByteArray());
    }}

    
    

