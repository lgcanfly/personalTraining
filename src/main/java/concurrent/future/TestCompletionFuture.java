package concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/25 Created
 */
public class TestCompletionFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Stage 1.");
        CompletableFuture<Void> voidFuture1 = CompletableFuture.runAsync(() -> {System.out.println("I'm void future 1.(" +
                Thread.currentThread().getName() + ")");});
        CompletableFuture<Void> voidFuture2 = CompletableFuture.runAsync(() -> {System.out.println("I'm void future 2.(" +
                Thread.currentThread().getName() + ")");});

        System.out.println("Stage 1.");

        // thenApply
        // need value from completable future and return the processed result
        CompletableFuture<String> supplierCompletableFuture = CompletableFuture.supplyAsync(()-> "Hello, ");
        CompletableFuture<String> supplierFuture = supplierCompletableFuture.thenApply(s -> s + "world.");
        System.out.println(supplierFuture.get());

        // thenAccept
        // need value from completable future and process, don't the result
        CompletableFuture<String> consumerCompletableFuture = CompletableFuture.supplyAsync(()-> "Hello, ");
        CompletableFuture<Void> consumerFuture = consumerCompletableFuture.thenAccept(s -> {System.out.println(s + "world2.");});

        // thenRun
        // neither need value from completable future nor return a processed value
        CompletableFuture<String> runCompletableFuture = CompletableFuture.supplyAsync(()-> "Hello, ");
        CompletableFuture<Void> runFuture = runCompletableFuture.thenRun(()->{System.out.println("Then run end.");});

        // thenCompose
        CompletableFuture<String> composeCompletableFuture = CompletableFuture.supplyAsync(()->"Hello, ")
                .thenCompose(s -> CompletableFuture.supplyAsync(()->s + "world composed."));
        System.out.println(composeCompletableFuture.get());

        // thenCombine
        CompletableFuture<String> combineCompletableFuture = CompletableFuture.supplyAsync(()->"Hello, ")
                .thenCombine(CompletableFuture.supplyAsync(() -> "world combined."), (s1, s2)->s1 + s2);
        System.out.println(combineCompletableFuture.get());

        // thenAcceptBoth
        CompletableFuture<Void> acceptBothCompletableFuture = CompletableFuture.supplyAsync(()->"Hello, ")
                .thenAcceptBoth(CompletableFuture.supplyAsync(()->"world accept both."),
                        (s1, s2)-> System.out.println(s1+s2));

        // allOf
        CompletableFuture<String> allOfFuture1 = CompletableFuture.supplyAsync(()->"Hello, ");
        CompletableFuture<String> allOfFuture2 = CompletableFuture.supplyAsync(()->", beautiful");
        CompletableFuture<String> allOfFuture3 = CompletableFuture.supplyAsync(()->" world.");

        CompletableFuture< Void> allOfFuture = CompletableFuture.allOf(allOfFuture1, allOfFuture2, allOfFuture3);
        allOfFuture.get();
        System.out.println("allOfFuture1 done:" + allOfFuture1.isDone());
        System.out.println("allOfFuture2 done:" + allOfFuture2.isDone());
        System.out.println("allOfFuture3 done:" + allOfFuture3.isDone());
    }
}
