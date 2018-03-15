package au.com.UITesting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class SharedDriver extends EventFiringWebDriver {

    private static WebDriver REAL_DRIVER;
    private static String URL;
    public static String BROWSER;
    private static BrowserMobProxyServer server;
    //private static BrowserMobServer browserMobServer;
    private static DesiredCapabilities capabilities;
    private static FirefoxProfile profile;
    private static String parentWindowHandle;
    private static Proxy proxy;
    private static String REMOTE_URL;

    private static final Thread CLOSE_THREAD = new Thread() {
        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            REAL_DRIVER.close();
            REAL_DRIVER.quit();
            if (server != null) {
                server.stop();
            }

            if (Thread.currentThread() != null) {
                Thread.currentThread().stop();
            }
        }
    };

    /*
     * static initializer
     */
    static {

        try {
//            BROWSER = System.getProperty("browser");
            BROWSER = "chrome";
            if (BROWSER.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(Arrays.asList("allow-running-insecure-content", "ignore-certificate-errors", "start-maximized"));
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                REAL_DRIVER = new ChromeDriver(capabilities);

            } else if (BROWSER.equalsIgnoreCase("iexplorer")) {
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/driver/IEDriverServer.exe");
                DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
                // ieCapabilities['acceptSslCerts'] = True
                ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                // ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
                // true);
                // ieCapabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS,
                // true);

                REAL_DRIVER = new InternetExplorerDriver(ieCapabilities);
                // REAL_DRIVER.navigate().to("javascript:document.getElementById('overridelink').click()");

            } else if (System.getProperty("browser").equalsIgnoreCase("phantom")) {
                DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
                org.openqa.selenium.Proxy p = new org.openqa.selenium.Proxy();
                p.setProxyAutoconfigUrl("proxy goes here");
                capabilities.setCapability(CapabilityType.PROXY, p);
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, System.getProperty("user.dir") + "/driver/phantomjs.exe");

                capabilities.setJavascriptEnabled(true);
                REAL_DRIVER = new PhantomJSDriver(capabilities);

            } else if (BROWSER.equalsIgnoreCase("firefox")) {

                profile = new FirefoxProfile();
                profile.setPreference("network.proxy.type", ProxyType.AUTODETECT.ordinal());
                profile.setPreference("network.proxy.http", "localhost");
                profile.setPreference("network.proxy.http_port", 3128);
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(false);
                profile.setPreference("security.mixed_content.block_active_content", false);

                // ==============DERSIRED CAPABILITIES=============
                capabilities = new DesiredCapabilities();
                capabilities.setCapability(CapabilityType.PROXY, proxy);
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

                profile = new FirefoxProfile();
                profile.setPreference("network.proxy.type", ProxyType.AUTODETECT.ordinal());
                profile.setPreference("network.proxy.http", "localhost");
                profile.setPreference("network.proxy.http_port", 3128);
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(false);
                profile.setPreference("security.mixed_content.block_active_content", false);

                // =================FIREFOX DRIVER INITIATING==================
                REAL_DRIVER = new FirefoxDriver(capabilities);
                // REMOTE_URL=System.getProperty("selenium.hub.url");
                // REAL_DRIVER = new RemoteWebDriver(new
                // URL(REMOTE_URL),capabilities);
                REAL_DRIVER.manage().window().maximize();

            } else if (System.getProperty("browser").equalsIgnoreCase("remote")) {
                REMOTE_URL = System.getProperty("selenium.hub.url");
                if (System.getProperty("run").equalsIgnoreCase("chrome")) {
                    // System.setProperty("webdriver.chrome.driver",
                    // "./driver/chromedriver.exe");
                    capabilities = DesiredCapabilities.chrome();
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(Arrays.asList("--allow-running-insecure-content", "ignore-certificate-errors", "--start-maximized", "--disable-web-security", "--test-type"));
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);

                } else if (System.getProperty("run").equalsIgnoreCase("phantom")) {
                    capabilities = DesiredCapabilities.phantomjs();
                    capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
                    org.openqa.selenium.Proxy p = new org.openqa.selenium.Proxy();
                    p.setProxyAutoconfigUrl("proxy goes here"); //
                    capabilities.setCapability(CapabilityType.PROXY, p);
                    System.getProperty("phantomjs.binary");
                    capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./driver/phantomjs.exe");
                    capabilities.setJavascriptEnabled(true);
                } else if (System.getProperty("run").equalsIgnoreCase("firefox")) {
                    profile = new FirefoxProfile();
                    profile.setPreference("network.proxy.type", ProxyType.AUTODETECT.ordinal());
                    profile.setPreference("network.proxy.http", "localhost");
                    profile.setPreference("network.proxy.http_port", 3128);
                    profile.setAcceptUntrustedCertificates(true);
                    profile.setAssumeUntrustedCertificateIssuer(false);
                    profile.setPreference("security.mixed_content.block_active_content", false);

                    capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName("firefox");
                    capabilities.setCapability(CapabilityType.PROXY, proxy);
                    capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                } else { // For PLR - initiates browsermobproxy server - browser value passed should be empty
                    if (!(server != null)) {
                        server = new BrowserMobProxyServer();
                    }

                    server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
                    server.setConnectTimeout(120, TimeUnit.SECONDS);
                    server.setTrustAllServers(true);
                    server.start();
                    /*
                     * List<String> collection = new ArrayList<String>(); //
					 * Collection<String> collection = null; //To capture
					 * analytics collection.add(".*infos.*");
					 * server.whitelistRequests(collection,200);
					 */
                    // browserMobServer = new BrowserMobServer(server);
                    // browserMobServer.setServer(server);

                    // ==============SELENIUM PROXY=============
                    proxy = ClientUtil.createSeleniumProxy(server);
                    // ==============Firefox Profile=============
                    profile = new FirefoxProfile();
                    profile.setAcceptUntrustedCertificates(true);
                    profile.setAssumeUntrustedCertificateIssuer(true);
                    // add Javascript error capture extension
                    //  JavaScriptError.addExtension(profile);

                    // ==============DERSIRED CAPABILITIES=============
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(CapabilityType.PROXY, proxy);
                    capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

                }

                REAL_DRIVER = new RemoteWebDriver(new URL(REMOTE_URL), capabilities);
                REAL_DRIVER.manage().window().maximize();

            } else {

                // ==============PROXY SERVER=============

                if (!(server != null)) {
                    server = new BrowserMobProxyServer();
                }

                server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
                server.setConnectTimeout(120, TimeUnit.SECONDS);
                server.setTrustAllServers(true);
                server.start();
                /*
				 * List<String> collection = new ArrayList<String>(); //
				 * Collection<String> collection = null; //To capture analytics
				 * collection.add(".*infos.*");
				 * server.whitelistRequests(collection,200);
				 */
                //  browserMobServer = new BrowserMobServer(server);
                //  browserMobServer.setServer(server);

                // ==============SELENIUM PROXY=============
                proxy = ClientUtil.createSeleniumProxy(server);
                // ==============Firefox Profile=============
                profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(true);

                // ==============DERSIRED CAPABILITIES=============
                capabilities = new DesiredCapabilities();
                capabilities.setCapability(CapabilityType.PROXY, proxy);
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

                // =================FIREFOX DRIVER INITIATING==================
                REAL_DRIVER = new FirefoxDriver(capabilities);
                REAL_DRIVER.manage().window().maximize();
            }

            REAL_DRIVER.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            //REAL_DRIVER.manage().deleteAllCookies();

            if (System.getProperty("url") != null) {
                URL = System.getProperty("url");
            } else {
                URL = "http://secure.smartbearsoftware.com/samples/TestComplete11/WebOrders/";
            }

            REAL_DRIVER.get(URL);
            parentWindowHandle = REAL_DRIVER.getWindowHandle(); // Store the
            // parent window
            // handle

            Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public SharedDriver() {
        super(REAL_DRIVER);
    }

    @Before
    public void beforeScenario() {
        REAL_DRIVER.navigate().refresh();
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");

        }
        super.close();
    }

    @After
    public void runAfterEachScenario(Scenario scenario) throws IOException {
        this.embedScreenshot(scenario);
        if (scenario.getSourceTagNames().toString().contains("captureJSErrorsON")) {
            //this.logJSErrors(scenario);
        }
        this.closeAllTabs();
        server = null;
    }

    private void embedScreenshot(Scenario scenario) throws IOException {
        String path;
        if (scenario.isFailed()) {
            if (scenario.isFailed()) {
                try {
                    File sourceFile = ((TakesScreenshot) REAL_DRIVER).getScreenshotAs(OutputType.FILE);
                    byte[] source = ((TakesScreenshot) REAL_DRIVER).getScreenshotAs(OutputType.BYTES);
                    path = System.getProperty("user.dir") + "/target/screenshots/";
                    String fileName = scenario.getName() + "_" + new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date()) + ".png";
                    fileName.substring(0, 40); // chop off characters > 40 to
                    // save
                    // file size
                    File destination = new File(path + fileName);
                    // FileUtils.copyFile(sourceFile, destination); -- Not
                    // required.
                    scenario.embed(source, "image/png");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    private void closeAllTabs() {

        for (String handle : REAL_DRIVER.getWindowHandles()) {
            if (!handle.equals(parentWindowHandle)) {
                REAL_DRIVER.switchTo().window(handle);
                REAL_DRIVER.close();
            }
        }
        REAL_DRIVER.switchTo().window(parentWindowHandle);
    }

}
