package apache_05_10;
//Kreirati TablePage za potrebe ovog testa, koji pribavlja sve elemente sa stranice.
//Napisati test koji vrsi dodavanje redova u tabelu citaju iz Zaposleni sheeta xlsx fajla. (SKINITE FAJL KAO XLSX i ubacite u sklopu vaseg projekta)
//
//Ucitati stranicu https://www.tutorialrepublic.com/snippets/bootstrap/table-with-add-and-delete-row-feature.php
//Dodati red podataka - jedan red u jednoj iteraciji 
//Kliknite na dugme Add New
//Unesite name,departmant i phone i kliknite na zeleno Add dugme (ovo bi bilo najbolje da bude jedna metoda u page-u npr neka se zove insertEmployee koja prima sve potrebne parametre)
//Nakon svakog dodavanja reda proverite da li je u zadnjem redu u tabeli korisnik sa unetim imenom
//Provere preko SOFT ASSERTA
//Na kraju programa ugasite pretrazivac.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PageTest {
	private WebDriver driver;
	private TablePage tablePage;

	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver", "driver-lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.tutorialrepublic.com/snippets/bootstrap/table-with-add-and-delete-row-feature.php" );
	}

	@Test
	public void tableTest () throws Exception {
		SoftAssert sa = new SoftAssert();
		File file = new File ("data/Podaci.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		XSSFSheet sheetKorisnici = wb.getSheet("Zaposleni");
		System.out.println(sheetKorisnici.getLastRowNum());
		for (int i = 1; i <= sheetKorisnici.getLastRowNum(); i++) {
			String name= 
					sheetKorisnici.getRow(i)
					.getCell(0).getStringCellValue();
			
			String department = 
					sheetKorisnici.getRow(i)
					.getCell(1).getStringCellValue();
			String phone = 
					sheetKorisnici.getRow(i)
					.getCell(2).getStringCellValue();
			
			tablePage.insertEmployee(name, department, phone);
			
			String lastEmployee = tablePage.getLastRowDataNameColumn().getText();
			sa.assertEquals(lastEmployee, name);
		}
		sa.assertAll();
	}
}
