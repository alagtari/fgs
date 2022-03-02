package com.example.filedemo.model;

import java.awt.Color;




import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
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
import  com.example.filedemo.service.FournisseurService;
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


@CrossOrigin("*")
public class PDFGenerator {




    private final static String logoPath="src\\main\\resources\\static\\logo\\logo.jpg";
    private  final ColisService colisService ;

    @Autowired
    public PDFGenerator (ColisService colisService) {
        this.colisService = colisService ;
    }





    private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public static ByteArrayInputStream colisPDFReport (List <Colis> coliss, String barCodeColisDirectoryPath ) throws MalformedURLException, IOException {
        Rectangle pageSize = new Rectangle(700, 1000) ;
        Document document = new Document(pageSize);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            for (Colis c3: coliss) {
                Font font5 = FontFactory.getFont(FontFactory.HELVETICA ,16, BaseColor.BLACK );
                Phrase bonLivraison = new Phrase ( "                                              Bon de livraison N° : "+c3.getBar_code() , font5);
                document .add(bonLivraison);
                Image image = Image.getInstance(barCodeColisDirectoryPath+"\\" + c3.toColisBarCode() + ".jpg");
                image.setBorder(1);
                Image logo = Image.getInstance(logoPath);
                logo.setBorder(1);
                logo.setAlignment(5);
                logo.setAbsolutePosition(-20,750);
                logo.scaleToFit(250,250);
                image.setAbsolutePosition( 400,850);
                image.scaleToFit(250,200);
                document.add(logo) ;
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));
                Paragraph societe = new Paragraph(c3.toColisBarCode() , font5);
                societe.setIndentationLeft(150f);    
                document.add(societe);
                image.setAlignment(50);
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));
                document.add(image);
                Phrase para111 = new Phrase (c3.toColisBarCode() , font5);
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
                document.add(new Phrase("\n"));

                PdfPTable expedtDest = new PdfPTable(2);
                expedtDest.setTotalWidth(600f);
                expedtDest.setLockedWidth(true);
                float[] columnsSize = new float[] {125f, 125f};
                expedtDest.setWidths(columnsSize);
                Stream.of("Coordonnés expéditeur" ,"Coordonnées destinataire").forEach(headerTitle -> {
                    PdfPCell header3 = new PdfPCell();
                    Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA,12,BaseColor.WHITE);
                    header3.setBackgroundColor(BaseColor.WHITE);
                    header3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header3.setBorderWidth(1);
                    header3.setBorderColor(new BaseColor(248, 155, 63));
                    header3.setPhrase(new Phrase(headerTitle, headFont4));
                    header3.setBackgroundColor(new BaseColor(248, 155, 63)) ;
                    expedtDest.addCell(header3);
                });
                PdfPCell celExp = new PdfPCell();
                StringBuilder expContent= new StringBuilder();
                expContent.append("Nom: "+ c3.getFournisseur().getNom_f()+"\n");
                expContent.append("Adresse: "+c3.getFournisseur().getPrenom_f()+"\n");
                expContent.append("Téléphone: "+c3.getFournisseur().getTel()+"\n");
                Phrase celExpContent = new Phrase(expContent.toString());
                celExp.addElement(celExpContent);
                celExp.setPaddingLeft(4);
                celExp.setHorizontalAlignment(Element.ALIGN_CENTER);
                celExp.setBorderWidth(1);
                celExp.setBorderColor(new BaseColor(248, 155, 63));

                PdfPCell celDes = new PdfPCell();
                StringBuilder desContent= new StringBuilder();
                desContent.append("Nom et Prénom  :   " + c3.getNom_c()+ " "  + c3.getPrenom_c()+"\n");
                desContent.append("Téléphone 1    :   " + c3.getTel_c_1()+"\n");
                desContent.append("Téléphone 2    :   " + c3.getTel_c_2()+"\n");
                desContent.append("Governorat      :   " + c3.getGouvernorat()+"\n");
                desContent.append("Adresse           :   " + c3.getDelegation()+" "+ c3.getAdresse()+"\n");
                desContent.append("\n");
                Phrase celDesContent = new Phrase(desContent.toString());
                celDes.addElement(celDesContent);
                celDes.setPaddingLeft(4);
                celDes.setBorderWidth(1);
                celDes.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celDes.setHorizontalAlignment(Element.ALIGN_CENTER);
                celDes.setBorderColor(new BaseColor(248, 155, 63));
                expedtDest.addCell(celExp);
                expedtDest.addCell(celDes);
                document.add(expedtDest);
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));


                // Add Text to PDF file ->// Add Text to PDF file ->
                Font font = FontFactory.getFont(FontFactory.HELVETICA ,15, BaseColor.WHITE ) ;
                Font font1 = FontFactory.getFont(FontFactory.HELVETICA ,11 , BaseColor.BLACK);

                Paragraph para1 = new Paragraph( "                                                 Coordonnées destinataire", font );

                PdfPCell cell = new PdfPCell(para1);
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);

                cell.setBorder( Rectangle.BOX) ;
                cell.setBorderColor( new BaseColor(248, 155, 63));
                cell.setBackgroundColor(new BaseColor(248, 155, 63));
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
                myCell.setBorderColor(new BaseColor(248, 155, 63));
                document.add(new Phrase("\n")) ;
                PdfPTable table111 = new PdfPTable(1);
                table111.addCell(myCell);






                Phrase para11 = new Phrase ("                      Nom et Prénom  :   " + c3.getNom_c()+ " "  + c3.getPrenom_c() , font1);
                Phrase para12 = new Phrase ("                      Téléphone 1       :    " + c3.getTel_c_1() , font1);
                Phrase para13 = new Phrase ("                      Téléphone 2       :    " + c3.getTel_c_2() , font1);
                Phrase para14 = new Phrase ("                      Adresse              :    " + c3.getGouvernorat()+" " +c3.getDelegation()+" "+ c3.getAdresse()+" " , font1);


                // document.add(new Phrase("\n"));
                document.add(para11);
                document.add(new Phrase("\n"));



                document.add(para12);
                document.add(new Phrase("\n"));


                document.add(para13);
                document.add(new Phrase("\n"));

                document.add(para14);



                Paragraph para2 = new Paragraph( "                                                           Transporteur  ", font);
                para2.setAlignment(Element.ALIGN_CENTER);



                PdfPCell cell5 = new PdfPCell(para2);
                cell5.setHorizontalAlignment (Element.ALIGN_LEFT);

                cell5.setBorder( Rectangle.BOX) ;
                cell5.setBorderColor( new BaseColor(248, 155, 63));
                cell5.setBackgroundColor(new BaseColor(248, 155, 63) ) ;
                cell5.setBorderWidth(10f);
                cell5.setColspan(50);
                cell5.setRowspan(50);
                PdfPTable table12 = new PdfPTable(1);
                table12.addCell(cell5);
                table12.setHorizontalAlignment (Element.ALIGN_LEFT);
                table12.setWidthPercentage(100f);


                document.add(table12) ;

                Font font11 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16 , BaseColor.BLACK);


               // Phrase para21 = new Phrase ("                                   Société De Livraison De Colis " , font11 ) ;



                Paragraph para = new Paragraph( "                                                 Coordonnées expediteur", font);
                para.setAlignment(Element.ALIGN_CENTER);





                PdfPCell cell55 = new PdfPCell(para);
                cell55.setHorizontalAlignment (Element.ALIGN_LEFT);

                cell55.setBorder( Rectangle.BOX) ;
                cell55.setBorderColor( new BaseColor(248, 155, 63));
                cell55.setBackgroundColor(new BaseColor(248, 155, 63) ) ;
                cell55.setBorderWidth(10f);
                cell55.setColspan(50);
                cell55.setRowspan(50);
                PdfPTable table122 = new PdfPTable(1);
                table122.addCell(cell55);
                table122.setHorizontalAlignment (Element.ALIGN_LEFT);
                table122.setWidthPercentage(100f);



                Phrase para22 = new Phrase ("                          RC              :      B0299972021" , font1);
                Phrase para23 = new Phrase ("                          MF              :      1713914 H/A/M/000 " , font1);
                Phrase para24 = new Phrase ("                          Adresse      :      kkkkkkk" , font1);



                //document.add(para21);
                document.add(new Phrase("\n"));

                document.add(new Phrase("\n"));

                document.add(para22);
                document.add(new Phrase("\n"));

                document.add(para23);
                document.add(new Phrase("\n"));

                document.add(para24);

                document.add(table122);



                PdfPTable table = new PdfPTable(13);
                table.setTotalWidth(650f);
                table.setLockedWidth(true);


                PdfPTable table6 = new PdfPTable(5);
                table6.setTotalWidth(600f);
                table6.setLockedWidth(true);


                Phrase para31 = new Phrase("                          Nom           :      "             + c3.getFournisseur().getNom_f()  , font1);
                Phrase para32= new Phrase("                          Adresse      :     "         +c3.getFournisseur().getPrenom_f() , font1);


                document.add(para31);
                document.add(new Phrase("\n"));

                document.add(para32);

                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));




                float[] columnWidths = new float[] {65f,22f, 30f, 30f, 30f,35f, 57f, 62f, 70f, 60f,44f,30f,32f};
                table.setWidths(columnWidths);


                Font font10 = FontFactory.getFont(FontFactory.HELVETICA ,11, BaseColor.BLACK);


                // Add PDF Table Header ->
                Stream.of("Date de création" ,"Nb","long","larg",  "Haut" , "Poids" ,"Mode de Paiement" , "Service" , "designation", "Remarque" ,  "Prix"   ,"TVA"  ,"Total")

                        .forEach(headerTitle -> {
                            PdfPCell header = new PdfPCell();
                            Font headFont = FontFactory.getFont(FontFactory.HELVETICA,11,BaseColor.WHITE);

                            header.setBackgroundColor(BaseColor.WHITE);
                            header.setHorizontalAlignment(Element.ALIGN_CENTER);
                            header.setBorderWidth(1);
                            header.setBorderColor(new BaseColor(248, 155, 63));
                            header.setPhrase(new Phrase(headerTitle, headFont));
                            header.setBackgroundColor(new BaseColor(248, 155, 63)) ;
                            table.addCell(header);

                        });

                PdfPCell dateCell = new PdfPCell(new Phrase(String.valueOf(c3.date_creation.getYear()) +"-" +String.valueOf(c3.date_creation.getMonthValue())+"-"+ String.valueOf(c3.date_creation.getDayOfMonth() ) , font10) );
                dateCell.setPaddingLeft(4);
                dateCell.setBorderWidth(1);

                dateCell.setVerticalAlignment(Element.ALIGN_CENTER);
                dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                dateCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(dateCell);


                PdfPCell NbCell = new PdfPCell(new Phrase(c3.getNb_p().toString() , font10));
                NbCell.setPaddingLeft(4);
                NbCell.setBorderWidth(1);

                NbCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                NbCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                NbCell.setBorderColor(new BaseColor(248, 155, 63));

                PdfPCell longCell = new PdfPCell(new Phrase(c3.getLongeur().toString() +  "                           "  , font10));
                longCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                longCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                longCell.setPaddingRight(4);
                longCell.setBorderWidth(1);
                longCell.setBorderColor(new BaseColor(248, 155, 63));

                NbCell.setBorderColor(new BaseColor(248, 155, 63));


                PdfPCell largeurCell = new PdfPCell(new Phrase(c3.getLargeur().toString() , font10));
                largeurCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                largeurCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                largeurCell.setPaddingRight(4);
                largeurCell.setBorderWidth(1);

                largeurCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(largeurCell);



                PdfPCell hautCell = new PdfPCell(new Phrase(c3.getHauteur().toString() , font10));
                hautCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hautCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hautCell.setPaddingRight(4);
                hautCell.setBorderWidth(1);

                hautCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(hautCell);

                PdfPCell poidsCell = new PdfPCell(new Phrase(c3.getPoids().toString() , font10));
                poidsCell.setPaddingLeft(4);
                poidsCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                poidsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                poidsCell.setBorderWidth(1);
                poidsCell.setBorderColor(new BaseColor(248, 155, 63));
                // table.addCell(poidsCell);




                PdfPCell modeCell = new PdfPCell(new Phrase(c3.getMode_paiement().toString() , font10));
                modeCell.setPaddingLeft(4);
                modeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                modeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                modeCell.setBorderWidth(1);
                modeCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(modeCell);



                PdfPCell serCell = new PdfPCell(new Phrase(c3.getService().toString() , font10) );
                serCell.setPaddingLeft(4);
                serCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                serCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                serCell.setBorderWidth(1);
                serCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(serCell);


                PdfPCell designCell = new PdfPCell(new Phrase(c3.getDesignation() , font10));
                designCell.setPaddingLeft(4);
                designCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                designCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                designCell.setBorderWidth(1);

                designCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(designCell);
//table6.addCell(designCell);

                PdfPCell rqCell = new PdfPCell(new Phrase(c3.getRemarque() , font10));
                rqCell.setPaddingLeft(4);
                rqCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rqCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                rqCell.setBorderWidth(1);
                rqCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(rqCell);


                PdfPCell desCell = new PdfPCell(new Phrase(c3.getRemarque()));
                desCell.setPaddingLeft(4);
                desCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                desCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                desCell.setBorderWidth(1);
                desCell.setBorderColor(new BaseColor(248, 155, 63));
//table.addCell(rqCell);


                double prix = c3.getCod()*0.93 ;

                PdfPCell codCell = new PdfPCell(new Phrase (String.valueOf(prix) , font10 ));
                codCell.setPaddingLeft(4);
                codCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                codCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                codCell.setBorderWidth(1);
                codCell.setBorderColor(new BaseColor(248, 155, 63));
//table.addCell(codCell);
//table6.addCell(codCell);

                double prix2 = c3.getCod()*0.81 ;

                PdfPCell cod2Cell = new PdfPCell(new Phrase (String.valueOf(prix2) , font10));
                cod2Cell.setPaddingLeft(4);
                cod2Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cod2Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cod2Cell.setBorderWidth(1);
                cod2Cell.setBorderColor(new BaseColor(248, 155, 63));
//table.addCell(cod2Cell);
//table6.addCell(codCell);



                PdfPCell tvaCell = new PdfPCell(new Phrase("7%" , font10) );
                tvaCell.setPaddingLeft(4);
                tvaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tvaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tvaCell.setBorderWidth(1);
                tvaCell.setBorderColor(new BaseColor(248, 155, 63));
                //table.addCell(tvaCell);

                PdfPCell tva2Cell = new PdfPCell(new Phrase("19%"));
                tva2Cell.setPaddingLeft(4);
                tva2Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tva2Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tva2Cell.setBorderWidth(1);
                tva2Cell.setBorderColor(new BaseColor(248, 155, 63));
//table.addCell(tvaCell);


                PdfPCell Nb2Cell = new PdfPCell(new Phrase(c3.getNb_p().toString() , font10));
                Nb2Cell.setPaddingLeft(4);
                Nb2Cell.setBorderWidth(1);

                Nb2Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                Nb2Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                Nb2Cell.setBorderColor(new BaseColor(248, 155, 63));
//table6.addCell(Nb2Cell);



                PdfPCell totCell = new PdfPCell(new Phrase (String.valueOf(c3.getCod()) , font10));
                totCell.setPaddingLeft(4);
                totCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                totCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                totCell.setBorderWidth(1);
                totCell.setBorderColor(new BaseColor(248, 155, 63));
//table6.addCell(totCell);

                PdfPCell tot2Cell = new PdfPCell(new Phrase (String.valueOf(c3.getCod()) , font10));
                tot2Cell.setPaddingLeft(4);
                tot2Cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tot2Cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tot2Cell.setBorderWidth(1);
                tot2Cell.setBorderColor(new BaseColor(248, 155, 63));
//table6.addCell(totCell);

                table.addCell(dateCell);
                table.addCell(NbCell);
                table.addCell(longCell);
                table.addCell(largeurCell);
                table.addCell(hautCell);
                table.addCell(poidsCell);
                table.addCell(modeCell);
                table.addCell(serCell);
                table.addCell(designCell);
                table.addCell(rqCell);
                table.addCell(codCell);
                table.addCell(tvaCell);
                table.addCell(totCell);




                Paragraph para50 = new Paragraph( " Cachet Obligatoire : " , font1);

                PdfPCell cell50 = new PdfPCell(para50);
                cell50.setHorizontalAlignment (Element.ALIGN_CENTER);

                cell50.setBorderColor(BaseColor.WHITE);

                PdfPTable table50 = new PdfPTable(1);
                table50.addCell(cell50);
                table50.setHorizontalAlignment (Element.ALIGN_CENTER);
                table50.setWidthPercentage(50f);

                document.add(table);
                document.add(new Phrase("\n"));



                document.add(table50);


                // document.add(table);
                document.newPage();

                Font font3 = FontFactory.getFont(FontFactory.HELVETICA,10, BaseColor.BLACK);

                Font font2 = FontFactory.getFont(FontFactory.HELVETICA,18,  new BaseColor(248, 155, 63));

                Paragraph para4 = new Paragraph( "                                                         Facture  ", font);
                // para1.setHorizontalAlignment (Element.ALIGN_LEFT);

                para.setAlignment(Element.ALIGN_CENTER);





                PdfPCell cell555 = new PdfPCell(para4);
                cell555.setHorizontalAlignment (Element.ALIGN_LEFT);

                cell555.setBorder( Rectangle.BOX) ;
                cell555.setBorderColor( new BaseColor(248, 155, 63));
                cell555.setBackgroundColor(new BaseColor(248, 155, 63));
                cell555.setBorderWidth(10f);
                cell555.setColspan(50);
                cell555.setRowspan(50);
                PdfPTable table1222 = new PdfPTable(1);
                table1222.addCell(cell555);
                table1222.setHorizontalAlignment (Element.ALIGN_LEFT);
                table1222.setWidthPercentage(100f);

                document.add(table1222) ;
                document.add(new Phrase("\n"));
                document.add(new Phrase("\n"));


                //Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,12,  new BaseColor(249, 161, 29));
                //1.121.111

                Phrase para41 = new Phrase ("                                Client            :   ", font1 ) ;
                Phrase para42 = new Phrase ( c3.getNom_c()+" "+c3.getPrenom_c() , font1) ;

                Phrase para43 = new Phrase ("                                Adresse        :   ", font1) ;
                Phrase para44 = new Phrase ( c3.getGouvernorat()+" " +c3.getDelegation()+" " + c3.getDelegation()+ " " + c3.getAdresse()+ " " , font1) ;
                document.add(para41) ;
                document.add(para42) ;
                document.add(new Phrase("\n"));

                document.add(para43) ;

                document.add(para44) ;

                Phrase para45 = new Phrase ("                                téléphone 1  :   ", font1 ) ;
                Phrase para46 = new Phrase ( (String.valueOf(c3.getTel_c_1()) ), font1) ;


                Phrase para500 = new Phrase("                                téléphone 2  :   ", font1 ) ;
                Phrase para51 = new Phrase ( (String.valueOf(c3.getTel_c_2()) ), font1) ;



                Phrase para49 = new Phrase ("                                Date             :   ", font1 ) ;
                Phrase para491 = new Phrase ( (                               String.valueOf( c3.date_creation.getYear()) +"-" + String.valueOf(c3.date_creation.getMonthValue()) + "-"+ String.valueOf(c3.date_creation.getDayOfMonth()))  , font1) ;
                document.add(new Phrase("\n"));

                document.add(para45) ;
                document.add(para46) ;
                document.add(new Phrase("\n"));
                document.add(para500) ;
                document.add(para51) ;
                document.add(new Phrase("\n"));
                document.add(para49) ;
                document.add(para491) ;
                document.add(new Phrase("\n"));

                document.add(new Phrase("\n"));


                float[] columnWidths2 = new float[] {90f,40f, 40f, 40f, 40f};
                table6.setWidths(columnWidths2);



                // Add PDF Table Header ->
                Stream.of("Désignation" ,"Quantité" , "Prix Unitaire Ht" , "TVA", "Prix Totale").forEach(headerTitle -> {
                    PdfPCell header3 = new PdfPCell();
                    Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA,12,BaseColor.WHITE);

                    header3.setBackgroundColor(BaseColor.WHITE);
                    header3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header3.setBorderWidth(1);
                    header3.setBorderColor(new BaseColor(248, 155, 63));
                    header3.setPhrase(new Phrase(headerTitle, headFont4));
                    header3.setBackgroundColor(new BaseColor(248, 155, 63)) ;
                    table6.addCell(header3);

                });



                table6.addCell(desCell);
                table6.addCell(Nb2Cell);
                table6.addCell(cod2Cell);
                table6.addCell(tva2Cell);
                table6.addCell(tot2Cell);

                Font font4 = FontFactory.getFont(FontFactory.HELVETICA,18,  new BaseColor(248, 155, 63));
                Font font6 = FontFactory.getFont(FontFactory.HELVETICA,14,  BaseColor.BLACK);


                document.add(table6);
                Phrase para77 = new Phrase ( "Total : " +String.valueOf(c3.getCod())  , font6) ;

//Phrase para78 = new Phrase ( String.valueOf(prix2) , font4) ;

//document.add(para77) ;
//document.add(para78 );


/**Paragraph para = new Paragraph( " Cordonnées expediteur", font);
 para.setAlignment(Element.ALIGN_CENTER);
 */

//document.add(new Phrase("\n"));

                PdfPCell cell7 = new PdfPCell(para77);
                cell7.setBorder( Rectangle.BOX) ;
                cell7.setHorizontalAlignment(120) ;
                cell7.setBorderColor( new BaseColor(255,255,255));
                cell7.setBorderWidth(1f);

                PdfPTable table8 = new PdfPTable(1);
                table8.addCell(cell7);
                table8.setHorizontalAlignment (Element.ALIGN_RIGHT);
                table8.setWidthPercentage(20f);
                document.add(new Phrase("\n"));

                document.add(table8);
                document.add(new Phrase("\n"));
//document.add(new Phrase("\n"));
//document.add(new Phrase("\n"));
                document.add(table50);
                document.newPage();

///////////////////////












            }


            document.close(); }

        catch(DocumentException e) {
            logger.error(e.toString());

        }
        return new ByteArrayInputStream(out.toByteArray());
    }}


