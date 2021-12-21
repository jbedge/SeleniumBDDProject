package com.utilities;

import com.opencsv.CSVReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVReaderClass {

    By username = By.xpath("//input[@name='username']");
    By password = By.xpath("//input[@name='password']");
    By loginIn = By.xpath("//input[@name='Login']");
    By inpSearch = By.xpath("//*[@id=\"subheader_search\"]/input[@class='headersearchbox search__input']");
    By iconSearch = By.xpath("//*[@id=\"subheader_search\"]/button");
    By action = By.xpath("//a[@title='Properties']");
    By hdr = By.xpath("//h2[text()='View Properties']");
    By bcm = By.xpath("//a[normalize-space()='Peterson, M.A.']//following-sibling::a[normalize-space()='120989']//following-sibling::a[normalize-space()='No Value Specified - 120989 - Peterson, M.A.']");

    @Step("Login into DocuShare")

    public void verifyFields(int iteration) throws Exception {

        com.opencsv.CSVReader reader = new com.opencsv.CSVReader(new FileReader("D:\\DocuShare\\HCCanada\\TestData\\000001_010000 US Formated_Contracts.csv"));
        String[] nextLine;
        String[] header = reader.readNext();
        List<String> stringList = new ArrayList<String>(Arrays.asList(header));
        stringList.set(stringList.indexOf("Title (DocuShare Record Name)"), "Title");
        stringList.set(stringList.indexOf("Author/Agent Name2"), "Author/Agent Name 2");
        stringList.set(stringList.indexOf("Author/Agent Code2"), "Author/Agent Code 2");
        stringList.set(stringList.indexOf("Xpert Created by"), "Xpert Created By");
        int cnt = 0;
        while ((nextLine = reader.readNext()) != null) {
            System.out.println("Row number " + cnt++);
            cnt++;

            String[] arr = {"Title", "Licensing Type", "Author/Agent Name", "Author/Agent Code", "Pseudonym", "Contract Number", "Document Type", "Document Level", "Source Code", "Author/Agent Name 2", "Author/Agent Code 2", "Bar Code Number", "Xpert Created By", "Other Author/Agent Numbers"};


            for (String hdr : arr) {
                int dataIndex = stringList.indexOf(hdr);
//                System.out.println("hdr"+hdr);
//                System.out.print(hdr+":"+nextLine[dataIndex]+",");
                verifyText(hdr, nextLine[dataIndex]);
            }
            String[] arr2 = {"Agreement Date", "Document Date"};
            for (String hdr : arr2) {
                int dataIndex = stringList.indexOf(hdr);
//                System.out.print(hdr+":"+main(nextLine[dataIndex])+",");
                verifyDate(hdr, main(nextLine[dataIndex]));
            }
            String[] breadcumb = {"Author/Agent Name", "Contract Number", "Title"};
            for (String hdr : breadcumb) {
                int name = stringList.indexOf("Author/Agent Name");
                int number = stringList.indexOf("Contract Number");
                int title = stringList.indexOf("Title");
//                verifyDate(hdr,main(nextLine[dataIndex]));
            }
            if (iteration <= cnt) {
                break;
            }
            System.out.println();
        }

    }

    public static String main(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        DateFormat originalFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = originalFormat.format(date1);
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String dayWeekText = new SimpleDateFormat("EEEE").format(date1);
        String modifedDate = dayWeekText + ", " + formattedDate;
        return modifedDate;

    }


    public void verifyText(String header, String data) {
        By text = By.xpath("//td[normalize-space()='" + header + ":']//following-sibling::td[normalize-space()='" + data + "']");
        try {
            waitForVisibility(text);
        } catch (Exception e) {
            By textActual = By.xpath("//td[normalize-space()='" + header + ":']//following-sibling::td");
            System.out.println(header + ":: " + data + "=>" + readText(textActual));
        }
    }

    public void verifyDate(String header, String data) {
        By text = By.xpath("//td[normalize-space()='" + header + ":']//following-sibling::td/font[contains(text(),'" + data + "')]");
        try {
            waitForVisibility(text);
        } catch (Exception e) {
            By textActual = By.xpath("//td[normalize-space()='" + header + ":']//following-sibling::td/font");
            System.out.println(header + ":: " + data + "=>" + readText(textActual));
        }
    }

    private String readText(By textActual) {
        return "";
    }

    public void verifyBreadCumb(String name, String number, String title) {
        By bcm = By.xpath("//a[normalize-space()='" + name + "']//following-sibling::a[normalize-space()='" + number + "']//following-sibling::a[normalize-space()='" + title + "']");
        try {
            waitForVisibility(bcm);
        } catch (Exception e) {
            System.out.println("Failed to verify Breadcumb");
        }
    }

    private void waitForVisibility(By bcm) {
    }

}

