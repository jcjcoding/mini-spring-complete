package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.Component;
import com.ecommerce.ioc.PostConstruct;
import com.ecommerce.ioc.PreDestroy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("mysqlRepo")
public class MysqlProductRepository implements ProductRepository {
    //Hikar
    private HikariDataSource dataSource;

    @PostConstruct
    public void init() throws IOException {
        try{
            Properties props = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties");

            if(in == null){
                throw new RuntimeException("db.properties not found");
            }
            props.load(in);
            /*
            // 2. 打印所有配置项（关键！看是否有jdbc.url）
            System.out.println("===== 读取到的db.properties配置 =====");
            props.forEach((key, value) -> System.out.println(key + " = " + value));
            System.out.println("===================================");

            // 3. 单独检查jdbc.url
            String jdbcUrl = props.getProperty("jdbc.url");
            if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
                throw new RuntimeException("❌ 配置中没有jdbc.url！请检查db.properties的键名是否正确");
            }
             */
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("jdbc.url"));//数据库地址
            config.setUsername(props.getProperty("jdbc.username"));//用户名
            config.setPassword(props.getProperty("jdbc.password"));//密码
            config.setDriverClassName(props.getProperty("jdbc.driver")); // 驱动类
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("jdbc.pool.size"))); // 最大连接数

            //连接池
            dataSource = new HikariDataSource(config);
            System.out.println("数据库连接成功");
        }catch (Exception e){
            throw new RuntimeException("数据层初始化失败"+e.getMessage(),e);
        }
    }

    @PreDestroy
    public void destroy(){
        if(dataSource != null){
            dataSource.close();
            System.out.println("数据层关闭");
        }
    }


    @Override
    public void save(Product product) {
        String sql = "insert into product (id, name, price, stock) values(?,?,?,?)";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            int rows = ps.executeUpdate();
            System.out.println("商品写入sql成功！影响行数："+rows+",商品信息："+product);

        }catch (Exception e){
            throw new RuntimeException("商品写入sql失败"+e.getMessage(),e);
        }
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT id, name, price, stock, create_time FROM product WHERE id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();//查询

            if(rs.next()){
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                return product;
            }
            return null;

        }catch (SQLException e){
            throw new RuntimeException("按ID查询商品失败:" + e.getMessage(),e);
        }
    }

    @Override
    public List<Product> findAll(){
        String sql = "SELECT id, name, price, stock, create_time FROM product";
        List<Product> products = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }

            return products;

        }catch (SQLException e){
            throw new RuntimeException("查询所有商品失败:" + e.getMessage(),e);
        }
    }

    @Override
    public int deductStock(Long id, Integer amount){
        String sql = "UPDATE product SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, amount);  // 扣减数量
            ps.setLong(2, id); // 商品ID
            ps.setInt(3, amount);  // 条件：库存>=扣减数量（防超卖）

            return ps.executeUpdate(); // 返回1=成功，0=失败

        } catch (SQLException e) {
            throw new RuntimeException("扣减库存失败：" + e.getMessage(), e);
        }
    }


}
