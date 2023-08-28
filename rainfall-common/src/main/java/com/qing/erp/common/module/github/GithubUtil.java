package com.qing.erp.common.module.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qing.erp.common.module.github.pojo.GitHubRepo;
import lombok.SneakyThrows;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;


public class GithubUtil {

    @SneakyThrows
    public void test(String[] args) {
        String repoUrl = "https://github.com/qs2042/chat_room";

        String proxyHost = "localhost";
        int proxyPort = 10808;

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        Document doc = Jsoup.connect(repoUrl)
//                .proxy(proxy)
//                .timeout(1000 * 3)
                .get();

        String repoName = doc.select("h1 strong a").text();
        String repoDescription = doc.select("div[data-testid=\"repository-description\"]").text();
        String language = doc.select("span[itemprop=\"programmingLanguage\"]").text();
        int starsCount = Integer.parseInt(doc.select("a[href$=\"/stargazers\"] span").text().replaceAll(",", ""));
        int forksCount = Integer.parseInt(doc.select("a[href$=\"/network/members\"] span").text().replaceAll(",", ""));
        int watchersCount = Integer.parseInt(doc.select("a[href$=\"/watchers\"] span").text().replaceAll(",", ""));

        System.out.println("Repository name: " + repoName);
        System.out.println("Repository description: " + repoDescription);
        System.out.println("Language: " + language);
        System.out.println("Stars count: " + starsCount);
        System.out.println("Forks count: " + forksCount);
        System.out.println("Watchers count: " + watchersCount);
    }

    @SneakyThrows
    public static GitHubRepo getInfo(String repoUrl) {

        URL url = new URL(repoUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(conn.getInputStream(), GitHubRepo.class);
    }

    public static void main(String[] args) {
        val r = getInfo("https://api.github.com/repos/qs2042/RainFall");
        System.out.println(r);
    }
}
