package com.example.demo.response;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;

import java.util.*;

public class Response {
    public static List<Map<String, String>> generateResponseUser(List<Users> users){
        List<Map<String,String>> list = new ArrayList<>();
        for(Users responseProduct:users){
            Map<String,String> map = new LinkedHashMap<String, String>();
            map.put("id",String.valueOf(responseProduct.getId()));
            map.put("name",responseProduct.getName());
            map.put("username",responseProduct.getUsername());
            StringBuilder roles = new StringBuilder();
            for(Role role:responseProduct.getRoles()){
                roles.append(","+String.valueOf(responseProduct.getRoles()));
            }
            map.put("role",roles.toString());
            StringBuilder sb = new StringBuilder();
            Boolean flag = true;
            for(Invoice invoice:responseProduct.getInvoice()){
                if(flag == true){
                    sb.append(String.valueOf(invoice.getId()));
                    flag = false;
                } else sb.append(","+String.valueOf(invoice.getId()));
            }
            map.put("invoice",sb.toString());
            list.add(map);
        }
        return list;
    }
    public static List<Map<String, String>> generateResponseProduct(List<Product> products){
        List<Map<String,String>> list = new ArrayList<>();
        for(Product responseProduct:products){
            Map<String,String> map = new LinkedHashMap<String, String>();
            map.put("id",String.valueOf(responseProduct.getId()));
            map.put("name",responseProduct.getName());
            map.put("category",responseProduct.getCategory());
            map.put("unitPrice",String.valueOf(responseProduct.getUnitPrice()));
            map.put("amount",String.valueOf(responseProduct.getAmount()));
            StringBuilder sb = new StringBuilder();
            Boolean flag = true;
            for(Invoice invoice:responseProduct.getInvoice()){
                if(flag == true){
                    sb.append(String.valueOf(invoice.getId()));
                    flag=false;
                } else sb.append(","+String.valueOf(invoice.getId()));
            }
            map.put("invoice",sb.toString());
            list.add(map);
        }
        return list;
    }

    public static List<Map<String, String>> generateResponseInvoice(List<Invoice> invoices){
        List<Map<String,String>> list = new ArrayList<>();
        for(Invoice responseInvoice:invoices){
            Map<String,String> map = new LinkedHashMap<String, String>();
            map.put("id",String.valueOf(responseInvoice.getId()));
            StringBuilder sb = new StringBuilder();
            Boolean flag = true;
            for(Product product:responseInvoice.getProduct()){
                if (flag == true){
                    sb.append(String.valueOf(product.getName()));
                    flag = false;
                } else sb.append(","+String.valueOf(product.getName()));
            }
            map.put("product",sb.toString());
            map.put("users",responseInvoice.getUsers().getName());
            map.put("totalPrice",String.valueOf(responseInvoice.getTotalPrice()));
            list.add(map);
        }
        return list;
    }
}
