package controllers;

import com.sun.net.httpserver.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import play.libs.F;
import java.util.concurrent.CompletionStage;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import play.mvc.*;
import play.libs.ws.*;
import services.HtmlParserService;

import java.util.concurrent.CompletionStage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


/**
 * Created by himanshu on 18/03/17.
 */
public class ParserController extends Controller{
    @Inject WSClient ws;

    public Result parseUrl() {
        try {
            Document dom = Jsoup.connect("http://en.wikipedia.org").get();
            Elements ele = dom.select("#mp-itn b a");
            System.out.println(ele.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> urlList = new ArrayList<>();
        urlList.add("http://en.wikipedia.org");
        urlList.add("http://www.google.com");
        List<Integer> urlStatus = new HtmlParserService().getUrlStatus(urlList);
        return ok(urlStatus.toString());
//        System.out.println(urlStatus);

//        try {
//            URL obj = new URL("http://google.com");
//            URLConnection conn = obj.openConnection();
//            Map<String, List<String>> map = conn.getHeaderFields();
//            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//                System.out.println("Key : " + entry.getKey() +
//                        " ,Value : " + entry.getValue());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ok("done");
    }
}
