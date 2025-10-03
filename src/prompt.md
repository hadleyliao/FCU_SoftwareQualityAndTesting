Log4j 2（功能較強大）
使用 Maven 配置 Log4j 2 主要分為兩個部分：添加 Maven 依賴（Dependencies） 和 撰寫 Log4j 2 配置文件。

第一步：配置 Maven 依賴 (pom.xml)
要使用 Log4j 2，您的 pom.xml 檔案中必須包含兩個核心組件：log4j-api（介面）和 log4j-core（實作）。

建議將 Log4j 的版本號定義在 <properties> 區塊，方便管理。

在您的 pom.xml 文件中，新增以下內容：

<properties>
    <log4j2.version>2.20.0</log4j2.version> 
</properties>

<dependencies>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>${log4j2.version}</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>${log4j2.version}</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j2-impl</artifactId>
        <version>${log4j2.version}</version>
    </dependency>
</dependencies>
小提示： 雖然你可以直接使用 Log4j 2 的 API，但業界標準更推薦使用 SLF4J (Simple Logging Facade for Java) 作為介面，然後讓 Log4j 2 作為底層實作。這樣未來如果要切換日誌框架（例如換成 Logback），程式碼不需要修改。

第二步：撰寫 Log4j 2 配置文件 (log4j2.xml)
Log4j 2 啟動時會自動在 Classpath 中尋找名為 log4j2.xml、log4j2.json、log4j2.yaml 或 log4j2.properties 的文件。

在 Maven 專案中，通常是將配置文件放在 src/main/resources 目錄下。

以下是一個簡單的 log4j2.xml 範例，它會將 INFO 級別及以上的日誌輸出到控制台 (Console) 和一個日誌文件 (File)：

<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <File name="FileAppender" fileName="logs/app.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    
    <Loggers>
        <Root level="info">
            <AppenderRef ref="ConsoleAppender"/>
            <AppenderRef ref="FileAppender"/>
        </Root>
        
        <Logger name="com.yourpackage.dao" level="debug" additivity="false">
             <AppenderRef ref="ConsoleAppender"/>
        </Logger>
    </Loggers>
</Configuration>
配置說明

<Configuration status="WARN">：設定 Log4j 內部日誌的級別。WARN 表示只輸出 Log4j 框架本身的警告及錯誤訊息。
<Appenders>：定義日誌輸出的目的地。
ConsoleAppender: 輸出到控制台 (SYSTEM_OUT)。
FileAppender: 輸出到指定路徑的檔案 (logs/app.log)。
PatternLayout: 定義日誌的格式。例如 %d 是日期，%-5level 是日誌級別，%msg%n 是日誌內容與換行。
<Loggers>：定義日誌記錄器。
<Root level="info">：這是預設的根紀錄器。將日誌級別設定為 info，表示 info、warn、error、fatal 級別的日誌都會被記錄。
<AppenderRef ref="...">：將 Appenders 綁定到 Logger 上。
第三步：在程式碼中使用 Log4j
在您的 Java 類別中，使用 Log4j 2 的 API 來實例化 Logger。

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyService {

    // 取得 Logger 實例
    private static final Logger logger = LogManager.getLogger(MyService.class);

    public void runLogic() {
        logger.trace("這是 Trace 等級的日誌"); // 級別太低，可能被忽略
        logger.debug("這是 Debug 等級的日誌"); // 級別太低，可能被忽略
        logger.info("應用程式啟動完成，執行中..."); // 會被 Root Logger 記錄 (level="info")
        
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 使用 error() 記錄例外，Log4j 會自動處理堆疊追蹤
            logger.error("發生數學錯誤", e); 
        }
    }
}
完成以上三個步驟後，執行您的 Maven 專案，Log4j 2 就會根據您的 log4j2.xml 配置文件開始記錄日誌了。

練習題:外送平台
模擬一個美食外送的伺服端功能，他會收到顧客訂單、餐廳收單、外送員接單的請求，過程中可能會產生很多例外，依據不同狀況寫到 log。

設計並拋出 Checked Exception (業務邏輯錯誤)。
處理並記錄 Unchecked Exception (程式碼邏輯或環境錯誤)。
正確使用 Log4j 2 的不同 日誌級別 (INFO, WARN, ERROR)。
透過 Enum 設計不同的訂單狀態，例如 PENDING 等狀態
設計 Order 的訂單類別
設計客製化 Exception
設計餐廳的接單功能 acceptOrder(), 可能拋出例外
...
最終，我們可以透過 log 發現後端在執行的過程中發生什麼問題。