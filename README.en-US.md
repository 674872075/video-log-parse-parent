# Video Viewership Analysis Based on Hadoop

This project is divided into four modules: the crawler module, the offline data analysis module, the common foundation module, and the web display module. Firstly, video data from Bilibili is crawled using WebMagic as the data source. A data analysis architecture is constructed based on offline analysis, incorporating the primary offline technologies of big data. To address the processing speed of the system and the requirements of actual production environments, research was conducted on distributed architecture. Upon establishing the necessary technical framework, a distributed project deployment method was adopted to ensure fault tolerance and scalability in a production environment, thereby improving the system's applicability and efficiency. Ultimately, the design and implementation of video viewership analysis based on big data technology were completed. Specific details include:

(1) A distributed environment was set up using three virtual machines to simulate a production environment.

(2) WebMagic crawler technology was used to crawl Bilibili video information. The collected data was imported into Kafka; the Flume framework was then used to collect data from Kafka to HDFS and import it into Hive. Subsequently, Hue was used to operate Hive for offline data analysis, and finally, Sqoop was used to import the results into MySQL. The entire process utilized Azkaban for scheduled script orchestration to achieve the required video viewership analysis functionality.

(3) The big data analysis display system used the currently popular enterprise SpringBoot + Mybatis framework to build the backend service layer. The system includes basic functions such as user login and registration. The frontend utilizes Bootstrap + Echarts technology to implement the data display module.

## Crawler Module

video-log-spider: Performs data crawling to collect Bilibili video information.

## Offline Data Analysis Module

video-log-analysis: Performs data preprocessing and offline analysis using Hive.

## Common Foundation Module

video-log-common: Encapsulates global exception handling and common utilities.

## Web Display Module

video-log-web: Used for the web display of offline analysis results. Completed functions include: daily collection overview, total collection overview, playback rankings by category, overall playback rankings, active user analysis, total favorites rankings, and total bullet-chat (danmaku) rankings.
