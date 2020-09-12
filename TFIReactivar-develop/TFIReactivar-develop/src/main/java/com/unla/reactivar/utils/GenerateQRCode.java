package com.unla.reactivar.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQRCode {

	public static void generateQRCode(String text, int width, int height, String filePath)
			throws WriterException, IOException {

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(matrix, "JPG", path);
	}


}