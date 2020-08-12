package com.pluto.spider.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 获取PhantomJsDriver类工厂
 * phantomjs 与 chrome的区别，phantomjs在后台运行无界面，省内存，
 * chrome有页面，用于调试比较直观
 */
@Component
public class WebDriverFactory {
    private Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    @Value("${phantomjs.driver.url}")
    private String PHANTOMJS_DRIVER_URL; //phantomjs浏览器驱动程序安装路径

    @Value("${chrome.application.url}")
    private String CHROME_APPLICATION_URL; //chrome浏览器安装路径

    @Value("${chrome.driver.url}")
    private String CHROME_DRIVER_URL; //chrome浏览器驱动程序安装路径

    private static ChromeDriverService service;

    public WebDriver getWebDriver() {
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", false);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_DRIVER_URL);

        WebDriver driver = new PhantomJSDriver(dcaps);
        return  driver;
    }

    public WebDriver getChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver",CHROME_APPLICATION_URL);
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，
        // 只要在newFile（）的时候写入你放的路径即可）
        try {
            service = new ChromeDriverService.Builder().usingDriverExecutable(new File(CHROME_DRIVER_URL))
                    .usingAnyFreePort().build();
            service.start();
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }
}
