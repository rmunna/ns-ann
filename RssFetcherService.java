package com.example.nsann.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssFetcherService {
    private final String RSS_FEED_URL = "https://nsearchives.nseindia.com/content/RSS/Financial_Results.xml";

    public List<RssItem> fetchRssFeed() {
        List<RssItem> rssItems = new ArrayList<>();
        try {
            URL url = new URL(RSS_FEED_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String link = item.getElementsByTagName("link").item(0).getTextContent();
                String description = item.getElementsByTagName("description").item(0).getTextContent();

                if (title.contains("Financial Result")) {
                    rssItems.add(new RssItem(title, link, pubDate, description));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rssItems;
    }
}
