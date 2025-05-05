package com.example.documentscanner.Components

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

fun saveTextAsPdf(context: Context, text: String) {

    val fileName = "RecognizedText_${System.currentTimeMillis()}.pdf"
    val pdfDocument = PdfDocument()
    val pageWidth = 595
    val pageHeight = 842
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint().apply {
        color = Color.BLACK
        textSize = 12f
        textAlign = Paint.Align.LEFT
    }

    val lines = text.split("\n")
    var y = 40f

    for (line in lines) {
        val textWidth = paint.measureText(line)
        val x = (pageWidth - textWidth) / 2  // Center horizontally
        canvas.drawText(line, x, y, paint)
        y += paint.textSize + 12
    }

    pdfDocument.finishPage(page)

//    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
//    pdfDocument.writeTo(FileOutputStream(file))
//    pdfDocument.close()


//    Toast.makeText(context, "PDF saved to: ${file.absolutePath}", Toast.LENGTH_LONG).show()

    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
        put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }
    }

    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

    if (uri != null) {
        resolver.openOutputStream(uri).use { outputStream ->
            pdfDocument.writeTo(outputStream)
        }
        Toast.makeText(context, "PDF saved as $fileName.pdf", Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "Failed to create file", Toast.LENGTH_SHORT).show()
    }

    pdfDocument.close()
}
