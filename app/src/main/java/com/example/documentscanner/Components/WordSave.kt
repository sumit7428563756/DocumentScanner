import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import android.os.Environment


fun createDocxFromText(context: Context, text: String): Uri? {
  try {
    val fileName = "RecognizedText_${System.currentTimeMillis()}.docx"
    val contentValues = ContentValues().apply {
      put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
      put(MediaStore.Files.FileColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
      }
    }

    val uri = context.contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
    uri?.let {
      context.contentResolver.openOutputStream(it)?.use { outputStream ->
        createSimpleDocx(text, outputStream)
      }
    }

    return uri
  } catch (e: Exception) {
    e.printStackTrace()
    return null
  }
}

fun createSimpleDocx(text: String, outputStream: OutputStream) {
  val escapedText = text.replace("&", "&amp;")

    .replace("<", "&lt;")
    .replace(">", "&gt;")
    .split("\n")
    .joinToString("\n") {
      """
            <w:p>
                <w:pPr>
                    <w:jc w:val="center"/>
                </w:pPr>
                <w:r>
                    <w:t xml:space="preserve">$it</w:t>
                </w:r>
            </w:p>
            """.trimIndent()
    }

  val documentXml = """
  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
  <w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:body>
  $escapedText
  </w:body>
  </w:document>
  """.trimIndent()

  val files = mapOf(
    "[Content_Types].xml" to """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
                <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
                <Default Extension="xml" ContentType="application/xml"/>
                <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
            </Types>
        """.trimIndent(),

    "_rels/.rels" to """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
            </Relationships>
        """.trimIndent(),

    "word/_rels/document.xml.rels" to """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
            </Relationships>
        """.trimIndent(),

    "word/document.xml" to documentXml
  )

  val zip = ZipOutputStream(outputStream)
  for ((path, content) in files) {
    zip.putNextEntry(ZipEntry(path))
    zip.write(content.toByteArray(Charsets.UTF_8))
    zip.closeEntry()
  }
  zip.close()
}

