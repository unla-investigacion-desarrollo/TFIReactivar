package com.unla.reactivar.utils;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Base64Util;

import com.google.zxing.WriterException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;

public class QREmprendimientoPDFExporter {

	private Emprendimiento emprendimiento;
	private String serverHost;

	public QREmprendimientoPDFExporter(Emprendimiento emp, String serverHost) {
		this.emprendimiento = emp;
		this.serverHost = serverHost;
	}

	private void writeTableData(PdfPTable table) {

		for (ConfiguracionLocal horario : emprendimiento.getConfiguracionLocales()) {
			PdfPCell cell = new PdfPCell();
			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(16);
			cell.setBorderColor(Color.WHITE);
			cell.setPadding(5);
			cell.setPhrase(new Phrase("", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(horario.getDiaSemana().toUpperCase(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			String horarioDia = (horario.getTurno1Desde() != null
					? horario.getTurno1Desde() + " - " + horario.getTurno1Hasta()
					: "CERRADO")
					+ (horario.getTurno2Desde() != null
							? " | " + horario.getTurno2Desde() + " - " + horario.getTurno2Hasta()
							: "	");
			cell.setPhrase(new Phrase(horarioDia, font));

			table.addCell(cell);

		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException, WriterException {
		long idEmprendimiento = emprendimiento.getIdEmprendimiento();
		String rutaImagenQR = "src/main/resources/image/QR_Emp_" + idEmprendimiento + ".jpg";

		String qrMessage = new StringBuilder(serverHost).append("/api/ocupacionLocal/").append(Base64Util.encode(String.valueOf(idEmprendimiento))).toString();

		GenerateQRCode.generateQRCode(qrMessage, 350, 350, rutaImagenQR);

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(36);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph(emprendimiento.getNombre().toUpperCase(), font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		Image qrImage = Image.getInstance(rutaImagenQR);

		qrImage.setAlignment(qrImage.ALIGN_CENTER);

		document.add(qrImage);

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.2f, 2f, 4f });

		writeTableData(table);

		document.add(table);

		p = new Paragraph("MAXIMO " + emprendimiento.getCapacidad() + " PERSONAS DENTRO DEL LOCAL", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		p.setSpacingBefore(20);
		font.setSize(24);
		font.setColor(Color.RED);
		document.add(p);

		Image qrImage2 = Image.getInstance("src/main/resources/image/StoreApp.jpg");
		qrImage2.setAbsolutePosition(33, 0);
		document.add(qrImage2);

		Image qrImage3 = Image.getInstance("src/main/resources/image/Reactivar.png");
		qrImage3.setAbsolutePosition(400, 5);
		qrImage3.scaleToFit(200, 200);
		document.add(qrImage3);

		document.close();

		File archivo1 = new File(rutaImagenQR);
		archivo1.delete();

	}

}
