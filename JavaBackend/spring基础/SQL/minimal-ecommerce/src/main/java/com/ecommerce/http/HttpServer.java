package com.ecommerce.http;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.BeanFactory;
import com.ecommerce.ioc.ClassPathBeanDefinitionScanner;
import com.ecommerce.ioc.DefaultBeanFactory;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ProductServiceInterface;
import com.google.gson.Gson;
import static spark.Spark.*;

public class HttpServer {
    private static final Gson gson = new Gson(); // 用于JSON序列化/反序列化
    private static ProductServiceInterface productService;

    // 初始化：启动IOC+AOP，获取Service实例
    private static void init() throws Exception {
        // 创建容器（自动集成AOP）
        BeanFactory beanFactory = new DefaultBeanFactory();

        // 包扫描：包含ProductLogAspect切面类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.ecommerce");

        productService = beanFactory.getBean(ProductServiceInterface.class);
    }

    public static void main(String[] args) throws Exception {
        // 1. 初始化IOC+AOP
        init();

        // 2. 配置HTTP服务端口（默认8080，可修改）
        port(8080);
        System.out.println("HTTP服务已启动，端口：8080，接口路径：/api/product/*");

        // 3. 配置跨域（解决前端调用时的跨域问题）
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // 4. 绑定接口1：GET /api/product/{id} 按ID查询商品
        get("/api/product/:id", (request, response) -> {
            response.type("application/json"); // 响应格式为JSON
            try {
                // 解析路径中的商品ID（如/api/product/1003中的1003）
                Long productId = Long.parseLong(request.params(":id"));
                Product product = productService.getProductById(productId);
                return gson.toJson(ApiResponse.success(product)); // 成功返回JSON
            } catch (Exception e) {
                // 业务错误返回400状态码
                return gson.toJson(ApiResponse.fail(e.getMessage(), 400));
            }
        });

        // 5. 绑定接口2：GET /api/product/all 查询所有商品
        get("/api/product/all", (request, response) -> {
            response.type("application/json");
            try {
                return gson.toJson(ApiResponse.success(productService.showAll()));
            } catch (Exception e) {
                return gson.toJson(ApiResponse.fail(e.getMessage(), 400));
            }
        });

        // 6. 绑定接口3：POST /api/product/buy 购买商品（请求体为JSON）
        post("/api/product/buy", (request, response) -> {
            response.type("application/json");
            try {
                // 解析POST请求体中的JSON（格式：{"productId":1003,"quantity":2}）
                BuyRequest buyRequest = gson.fromJson(request.body(), BuyRequest.class);
                String result = productService.buyProduct(buyRequest.getProductId(), buyRequest.getQuantity());
                return gson.toJson(ApiResponse.success(result));
            } catch (Exception e) {
                return gson.toJson(ApiResponse.fail(e.getMessage(), 400));
            }
        });

        // 7. 全局异常捕获（处理系统级错误，返回500状态码）
        exception(Exception.class, (e, request, response) -> {
            response.type("application/json");
            response.status(500);
            response.body(gson.toJson(ApiResponse.fail("系统错误：" + e.getMessage(), 500)));
        });
    }

    // 内部类：接收购买商品的POST请求体（与JSON字段对应）
    private static class BuyRequest {
        private Long productId; // 商品ID
        private Integer quantity; // 购买数量

        // Getter和Setter（必须，否则JSON反序列化失败）
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}