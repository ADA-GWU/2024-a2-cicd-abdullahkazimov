package ada.edu.demo.webtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebInterfaceTests {

	@Autowired
	private WebDriver webDriver;

	@LocalServerPort
	private int port;

	@Test
	@Order(1)
	@DisplayName("Create a user")
	public void CreateUser() {
		webDriver.get("http://localhost:"+port+"/student/new");

		WebElement studentIdInput = webDriver.findElement(By.id("studentId"));
		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
		WebElement emailInput = webDriver.findElement(By.id("email"));

		// Check if such a field exists
		assertNotNull(firstNameInput);

		try {
			studentIdInput.sendKeys("1");
			Thread.sleep(2000);
			firstNameInput.sendKeys("Jamal");
			Thread.sleep(2000);
			lastNameInput.sendKeys("Hasanov");
			Thread.sleep(2000);
			emailInput.sendKeys("jhasanov@ada.edu.az");
			Thread.sleep(2000);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}

		// Find and submit the form (assuming there's a submit button with a specific attribute)
		WebElement submitButton = webDriver.findElement(By.id("submit"));

		// Check if submit button exists
		assertNotNull(submitButton, "Submit button is not found.");

		submitButton.click();
	}

	@Test
	@Order(2)
	@DisplayName("Check the created user")
	public void CheckUser() {
		// Check if the student is added
		webDriver.get("http://localhost:"+port+"/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Jamal')]"));
		List<WebElement> bodyElementLName = webDriver.findElements(By.xpath("//*[contains(text(), 'Jamal')]"));
		System.out.println("Element result"+bodyElementLName);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// Check if the text "Jamal" is present in the page content [DONE]
		assert(bodyElementFName.size() == 1);
		assert(bodyElementLName.size() == 1);
	}

	@Test
	@Order(3)
	@DisplayName("Update the user information")
	public void UpdateUser() {
		// Check if user is updated
		webDriver.get("http://localhost:" + port + "/student/update?id=1");

		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		assertNotNull(firstNameInput, "First name input field is not found.");

		try {
			firstNameInput.clear();
			firstNameInput.sendKeys("Kamal");
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			fail("Test interrupted during the update process", ex);
		}

		WebElement submitButton = webDriver.findElement(By.id("submit"));
		assertNotNull(submitButton, "Submit button is not found.");
		submitButton.click();

		webDriver.get("http://localhost:"+port+"/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Kamal')]"));
		System.out.println("Element result"+bodyElementFName);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// Check if the text "Kamal" is present in the page content [DONE]
		assert(bodyElementFName.size() == 1);
	}


}
