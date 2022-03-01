package com.example.filedemo.model;

import java.io.ByteArrayInputStream;
import com.example.filedemo.service.ColisService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@CrossOrigin("*")
public class PDFGenerator2 {
	/**	private List<Colis> c4;

	 public PDFGenerator2(List<Colis> c4) {
	 this.c4 = c4;
	 }
	 */
    private final static String logoPath="src\\main\\resources\\static\\logo\\logo.jpg";

	@Autowired
	private  final ColisService colisService ;

	@Autowired
	public PDFGenerator2(ColisService colisService) {
		this.colisService = colisService ;
	}


	private static Logger logger1 = LoggerFactory.getLogger(PDFGenerator2.class);

	public static ByteArrayInputStream colisDechargeReport ( List <Colis> coliss, String barCodeColisDirectoryPath) throws MalformedURLException, IOException {

		Rectangle pageSize = new Rectangle(700, 1000) ;
		Document document1 = new Document(pageSize);
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();


		try {

			PdfWriter.getInstance(document1, out1);
			document1.open();


			Image logo = Image.getInstance(logoPath);
			logo.setBorder(1);
			logo.setAlignment(5);
			logo.setAbsolutePosition(1,875);
			logo.scaleToFit(150,150);
			//image.setAbsolutePosition( 400,900);
			//image.scaleToFit(300,300);
            document1.add(logo);
			document1.add(new Phrase("\n"));
			document1.add(new Phrase("\n"));
			document1.add(new Phrase("\n"));
			document1.add(new Phrase("\n"));
			document1.add(new Phrase("\n"));




			PdfPTable table9 = new PdfPTable(8);
			table9.setTotalWidth(650f);
			table9.setLockedWidth(true);

			float[] columnWidths = new float[] {57f,55f, 55f, 55f, 100f,35f, 50f , 42f};
			table9.setWidths(columnWidths);



			// Add PDF Table Header ->
			Stream.of("Code à barres" ,"Expéditeur","Client","Téléphone",  "Adresse" , "Cod" ,"Remarque" , "Pointage" )

					.forEach(headerTitle -> {
						PdfPCell header3 = new PdfPCell();
						Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA,12,BaseColor.WHITE);

						header3.setBackgroundColor(BaseColor.WHITE);
						header3.setHorizontalAlignment(Element.ALIGN_CENTER);
						header3.setBorderWidth(1);
						header3.setBorderColor(new BaseColor(248, 155, 63));
						header3.setPhrase(new Phrase(headerTitle, headFont4));
						header3.setBackgroundColor(new BaseColor(248, 155, 63)) ;
						table9.addCell(header3);

					});

			for (Colis c4 : coliss) {
				Font font10 = FontFactory.getFont(FontFactory.HELVETICA ,11, BaseColor.BLACK);
				Image image2 = Image.getInstance(barCodeColisDirectoryPath+"\\" + c4.getBar_code() + ".jpg");
				image2.setBorder(1);

				PdfPCell RefCell = new PdfPCell(new Phrase(c4.bar_code , font10) );
				RefCell.setPaddingLeft(4);
				RefCell.addElement(new Phrase("\n"));

				RefCell.addElement(image2);
				RefCell.addElement(new Phrase("  "+c4.bar_code , font10)) ;

				RefCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				RefCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				RefCell.setBorderWidth(1);
				RefCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(RefCell);


				PdfPCell  expCell = new PdfPCell(new Phrase(c4.getFournisseur().getNom_f() +" "+ c4.getFournisseur().getPrenom_f() , font10));
				expCell.setPaddingLeft(4);
				expCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				expCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				expCell.setBorderWidth(1);
				expCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(expCell);


				PdfPCell clCell = new PdfPCell(new Phrase(c4.getNom_c()+ " " +  c4.getPrenom_c() , font10));
				clCell.setPaddingLeft(4);
				clCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				clCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				clCell.setBorderWidth(1);
				clCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(clCell);


				PdfPCell telCell = new PdfPCell(new Phrase(String.valueOf(c4.getTel_c_1()) , font10));
				telCell.setPaddingLeft(4);
				telCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				telCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				telCell.setBorderWidth(1);
				telCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(telCell);

				PdfPCell adCell = new PdfPCell(new Phrase(c4.getGouvernorat() + " " + c4.getDelegation() + " " + c4.getDelegation() +" "+ c4.getAdresse() +" " , font10));
				adCell.setPaddingLeft(4);
				adCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				adCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				adCell.setBorderWidth(1);
				adCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(adCell);



				PdfPCell coCell = new PdfPCell(new Phrase(String.valueOf(c4.getCod()) , font10));
				coCell.setPaddingLeft(4);
				coCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				coCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				coCell.setBorderWidth(1);
				coCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(coCell);


				PdfPCell reCell = new PdfPCell(new Phrase(c4.getRemarque() , font10));
				reCell.setPaddingLeft(4);
				reCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				reCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				reCell.setBorderWidth(1);
				reCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(reCell);


				PdfPCell poCell = new PdfPCell(new Phrase( "    ", font10));
				poCell.setPaddingLeft(4);
				poCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				poCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				poCell.setBorderWidth(1);
				poCell.setBorderColor(new BaseColor(248, 155, 63));
				table9.addCell(poCell);
			}

			document1.add(table9);
			Font font = FontFactory.getFont(FontFactory.HELVETICA,15, BaseColor.WHITE ) ;

			Font font22 = FontFactory.getFont(FontFactory.HELVETICA,15,  new BaseColor(121, 28, 248));
			Font font2 = FontFactory.getFont(FontFactory.HELVETICA,15,  BaseColor.DARK_GRAY);

			//LocalDateTime dateCreation1 = LocalDateTime.now();
			float som = 0 ;

			for (Colis c4 : coliss) {

				som = som + c4.getCod() ;
			}


			Paragraph para11 = new Paragraph(           " Somme   :   "  + String.valueOf(som) + "  TND  "  , font  );
			para11.setAlignment(Element.ALIGN_CENTER);





			PdfPCell cell55 = new PdfPCell(para11);
			cell55.setHorizontalAlignment (Element.ALIGN_CENTER);

			cell55.setBorder( Rectangle.BOX) ;
			cell55.setBorderColor( new BaseColor(248, 155, 63));
			cell55.setBackgroundColor(new BaseColor(248, 155, 63) ) ;
			cell55.setBorderWidth(10f);
			cell55.setColspan(50);
			cell55.setRowspan(50);
			PdfPTable table122 = new PdfPTable(1);
			table122.addCell(cell55);
			table122.setHorizontalAlignment (Element.ALIGN_CENTER);
			table122.setWidthPercentage(50f);
			document1.add(new Phrase("\n"));

			document1.add(table122) ;

			// Phrase para22 = new Phrase ("            " + c4.getCod(), font2);


			LocalDateTime dateCreation1 = LocalDateTime.now();
			Paragraph para1 = new Paragraph( " Date :  " +  String.valueOf( dateCreation1.getYear()) +"-" +String.valueOf(dateCreation1.getMonthValue()) + "-"+ String.valueOf(dateCreation1.getDayOfMonth())   , font  );
			para11.setAlignment(Element.ALIGN_CENTER);

			PdfPCell cell555 = new PdfPCell(para1);
			cell555.setHorizontalAlignment (Element.ALIGN_CENTER);

			cell555.setBorder( Rectangle.BOX) ;
			cell555.setBorderColor( new BaseColor(248, 155, 63));
			cell555.setBackgroundColor(new BaseColor(248, 155, 63) ) ;
			cell555.setBorderWidth(10f);
			cell555.setColspan(50);
			cell555.setRowspan(50);
			PdfPTable table1222 = new PdfPTable(1);
			table1222.addCell(cell555);
			table1222.setHorizontalAlignment (Element.ALIGN_CENTER);
			table1222.setWidthPercentage(50f);
			document1.add(new Phrase("\n"));

			document1.add(table1222) ;

//document1.add(new Phrase("\n"));

//document1.add(new Phrase("\n"));

			document1.add(para1) ;
//document1.add(new Phrase("\n"));

			Paragraph para33 = new Paragraph( "            Cachet Livreur                                                                 Signature Fournisseur "  , font2  );
			Paragraph para44 = new Paragraph( "               ..............                                                                                   .............."  , font2);

			document1.add(new Phrase("\n"));



			document1.add(para33) ;
			document1.add(new Phrase("\n"));

			document1.add(para44) ;


			document1.close();

		}
		catch(DocumentException e) {
			logger1.error(e.toString());

		}
		return new ByteArrayInputStream(out1.toByteArray());
	}}

