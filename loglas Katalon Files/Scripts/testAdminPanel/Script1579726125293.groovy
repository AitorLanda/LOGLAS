import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://loglas.duckdns.org:8181/POPBL5/')

WebUI.click(findTestObject('Page_LOGLAS/span_Click to login'))

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Login_userName'), 'admin')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login/input_Login_password'), 'RAIVpflpDOg=')

WebUI.click(findTestObject('Page_Login/span_LOGIN'))

WebUI.click(findTestObject('Object Repository/Page_Games Dashboard/a_admin'))

WebUI.verifyTextPresent('Questions', true)

WebUI.verifyTextPresent('Grafana', true)

