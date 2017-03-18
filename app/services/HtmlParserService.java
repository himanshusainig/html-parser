package services;

import handlers.ParserHandler;
import play.Logger;
import play.libs.ws.*;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * Created by himanshu on 18/03/17.
 */
public class HtmlParserService implements ParserHandler {
    @Inject
    WSClient ws = play.api.Play.current().injector().instanceOf(WSClient.class);

    @Override
    public String parseString(String inputStrin, String url) {
        return null;
    }

    public List<Integer> getUrlStatus(List<String> urls){
        List<CompletableFuture<WSResponse>> futures = new ArrayList<>();
        List<Integer> statusList = new ArrayList<>();
        for(String urlString : urls) {
            futures.add(getStatus(urlString, statusList));
//            ws.url(urlString).get()
//                    .whenComplete(((wsResponse, throwable) -> {
//                        statusList.add(wsResponse.getStatus());
////                        System.out.println("**** "+ wsResponse.getStatus());
//                    }));
        }

        futures.forEach(completableFuture -> {
            try {
                completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                Logger.warn("{}", e.getCause());
            }
        });
        return statusList;
    }

    public CompletableFuture<WSResponse> getStatus(String urlString, List<Integer> statusList){
//    return ws.url(urlString).get().map(
//            new Function<WSResponse, Result>() {
//                public Result apply(WS.Response response) {
//                    return ok("Feed title:" + response.asJson().findPath("title"));
//                }
//            }
//    );

        return ws.url(urlString).get().whenComplete(((wsResponse, throwable) -> {
            statusList.add(wsResponse.getStatus());
        })).toCompletableFuture();
    }
}

