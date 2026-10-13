package Clases;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GenerarPDF {

    public void generarPDFAsientosPorUsuario(DefaultTableModel modelo, String rutaArchivo) {
        Document document = new Document();
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            document.add(new Paragraph("Asientos del Usuario"));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(modelo.getColumnCount());
            
            // Añadir los encabezados de la tabla
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Paragraph(modelo.getColumnName(i)));
                table.addCell(cell);
            }
            
            // Añadir las filas de datos
            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    table.addCell(modelo.getValueAt(i, j).toString());
                }
            }
            
            document.add(table);
            document.close();
            
            JOptionPane.showMessageDialog(null, "PDF generado correctamente en " + rutaArchivo);
            
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
        }
    }
}
