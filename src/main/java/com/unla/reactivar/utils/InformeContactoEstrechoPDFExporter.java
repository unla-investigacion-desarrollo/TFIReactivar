package com.unla.reactivar.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

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
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.models.PersonaFisica;

@Component
public class InformeContactoEstrechoPDFExporter {

	private List<OcupacionLocal> ocupacionesLocal;

	public InformeContactoEstrechoPDFExporter(List<OcupacionLocal> ocupacionesLocal) {
		this.ocupacionesLocal = ocupacionesLocal;
	}

	private void writeTableData(PdfPTable table) {

		for (OcupacionLocal ocupacion : ocupacionesLocal) {
			Emprendimiento emprendimiento = ocupacion.getEmprendimiento();
			PersonaFisica persona = ocupacion.getPersona();
			PdfPCell cell = new PdfPCell();
			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setSize(8);
			cell.setBorderColor(Color.BLACK);
			cell.setPadding(2);
			cell.setPhrase(new Phrase(persona.getNombre(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);
			table.addCell(cell);
			cell.setPhrase(new Phrase(persona.getApellido(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(persona.getCelular(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(persona.getLogin().getEmail(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(ocupacion.getFechaHoraEntrada().toString().substring(0, 16), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(ocupacion.getFechaHoraSalida().toString().substring(0, 16), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(emprendimiento.getNombre(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(emprendimiento.getUbicacion().getCalle(), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);
			cell.setPhrase(new Phrase(String.valueOf(emprendimiento.getUbicacion().getNumero()), font));
			cell.setHorizontalAlignment(cell.ALIGN_LEFT);

			table.addCell(cell);

		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException, WriterException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(36);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph("Informe posible contacto estrecho", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		p.setSpacingAfter(10f);
		document.add(p);

		PdfPTable table = new PdfPTable(9);

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 7f, 7f, 7f, 19f, 16f, 16f, 7f, 7f, 7f });

		writeTableData(table);

		document.add(table);

		Image qrImage2 = Image.getInstance("src/main/resources/image/StoreApp.jpg");
		qrImage2.setAbsolutePosition(33, 0);
		document.add(qrImage2);

		Image qrImage3 = Image.getInstance("src/main/resources/image/Reactivar.jpg");
		qrImage3.setAbsolutePosition(438, 22);
		document.add(qrImage3);

		document.close();

	}

}
