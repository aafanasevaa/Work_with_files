import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Tests extends TestData {

    @Test
    @DisplayName("Скачивание PDF файла и проверка наличия в нем данных")
    public void downloadPdfFile() throws IOException {
        open(pdfUrl);
        File pdf = $(".document-preview__link").download();
        PDF myPdf = new PDF(pdf);
        Assertions.assertEquals(myPdf.numberOfPages, 4);
    }

    @Test
    @DisplayName("Скачивание xls файла и проверка его содержимого")
    public void downloadXlsFile() throws FileNotFoundException {
        open(xlsUrl);
        File xls = $(byText("Download sample xls file")).download();
        XLS myXls = new XLS(xls);
        boolean checkPassed = myXls.excel
                .getSheetAt(0)
                .getRow(4)
                .getCell(4)
                .getStringCellValue().
                contains("United States");
    }

    @Test
    @DisplayName("Загрузка изображения на сайте")
    public void uploadPicture() {
        open(picUrl);
        $(".standard_upload").uploadFromClasspath(namePicture);
        $(".upload-item").shouldHave(text(namePicture));
    }
}
